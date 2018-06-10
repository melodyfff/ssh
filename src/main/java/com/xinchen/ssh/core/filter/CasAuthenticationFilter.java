package com.xinchen.ssh.core.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.jasig.cas.client.util.CommonUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/6/10 19:51
 */
public class CasAuthenticationFilter extends AbstractAuthenticationProcessingFilter{
    public static final String CAS_STATEFUL_IDENTIFIER = "_cas_stateful_";
    public static final String CAS_STATELESS_IDENTIFIER = "_cas_stateless_";
    private RequestMatcher proxyReceptorMatcher;
    private ProxyGrantingTicketStorage proxyGrantingTicketStorage;
    private String artifactParameter = "ticket";
    private boolean authenticateAllArtifacts;
    private AuthenticationFailureHandler proxyFailureHandler = new SimpleUrlAuthenticationFailureHandler();

    public CasAuthenticationFilter() {
//        super("/login/cas");
        super("/login/cas");
        this.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler());
    }

    @Override
    protected final void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        boolean continueFilterChain = this.proxyTicketRequest(this.serviceTicketRequest(request, response), request);
        if (!continueFilterChain) {
            super.successfulAuthentication(request, response, chain, authResult);
        } else {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Authentication success. Updating SecurityContextHolder to contain: " + authResult);
            }

            SecurityContextHolder.getContext().setAuthentication(authResult);
            if (this.eventPublisher != null) {
                this.eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
            }

            chain.doFilter(request, response);
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        if (this.proxyReceptorRequest(request)) {
            this.logger.debug("Responding to proxy receptor request");
            CommonUtils.readAndRespondToProxyReceptorRequest(request, response, this.proxyGrantingTicketStorage);
            return null;
        } else {
            boolean serviceTicketRequest = this.serviceTicketRequest(request, response);
            String username = serviceTicketRequest ? "_cas_stateful_" : "_cas_stateless_";
            String password = this.obtainArtifact(request);
            if (password == null) {
                this.logger.debug("Failed to obtain an artifact (cas ticket)");
                password = "";
            }

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected String obtainArtifact(HttpServletRequest request) {
        return request.getParameter(this.artifactParameter);
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        boolean serviceTicketRequest = this.serviceTicketRequest(request, response);
        boolean result = serviceTicketRequest || this.proxyReceptorRequest(request) || this.proxyTicketRequest(serviceTicketRequest, request);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("requiresAuthentication = " + result);
        }

        return result;
    }

    public final void setProxyAuthenticationFailureHandler(AuthenticationFailureHandler proxyFailureHandler) {
        Assert.notNull(proxyFailureHandler, "proxyFailureHandler cannot be null");
        this.proxyFailureHandler = proxyFailureHandler;
    }

    @Override
    public final void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
        super.setAuthenticationFailureHandler(new CasAuthenticationFailureHandler(failureHandler));
    }

    public final void setProxyReceptorUrl(String proxyReceptorUrl) {
        this.proxyReceptorMatcher = new AntPathRequestMatcher("/**" + proxyReceptorUrl);
    }

    public final void setProxyGrantingTicketStorage(ProxyGrantingTicketStorage proxyGrantingTicketStorage) {
        this.proxyGrantingTicketStorage = proxyGrantingTicketStorage;
    }

    public final void setServiceProperties(ServiceProperties serviceProperties) {
        this.artifactParameter = serviceProperties.getArtifactParameter();
        this.authenticateAllArtifacts = serviceProperties.isAuthenticateAllArtifacts();
    }

    private boolean serviceTicketRequest(HttpServletRequest request, HttpServletResponse response) {
        boolean result = super.requiresAuthentication(request, response);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("serviceTicketRequest = " + result);
        }

        return result;
    }

    private boolean proxyTicketRequest(boolean serviceTicketRequest, HttpServletRequest request) {
        if (serviceTicketRequest) {
            return false;
        } else {
            boolean result = this.authenticateAllArtifacts && this.obtainArtifact(request) != null && !this.authenticated();
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("proxyTicketRequest = " + result);
            }

            return result;
        }
    }

    private boolean authenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }

    private boolean proxyReceptorRequest(HttpServletRequest request) {
        boolean result = this.proxyReceptorConfigured() && this.proxyReceptorMatcher.matches(request);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("proxyReceptorRequest = " + result);
        }

        return result;
    }

    private boolean proxyReceptorConfigured() {
        boolean result = this.proxyGrantingTicketStorage != null && this.proxyReceptorMatcher != null;
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("proxyReceptorConfigured = " + result);
        }

        return result;
    }

    private class CasAuthenticationFailureHandler implements AuthenticationFailureHandler {
        private final AuthenticationFailureHandler serviceTicketFailureHandler;

        public CasAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
            Assert.notNull(failureHandler, "failureHandler");
            this.serviceTicketFailureHandler = failureHandler;
        }

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            if (serviceTicketRequest(request, response)) {
                this.serviceTicketFailureHandler.onAuthenticationFailure(request, response, exception);
            } else {
                proxyFailureHandler.onAuthenticationFailure(request, response, exception);
            }

        }
    }
}
