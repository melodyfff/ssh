package com.xinchen.ssh.core.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/6/10 19:49
 */
public abstract class AbstractAuthenticationProcessingFilter extends GenericFilterBean implements ApplicationEventPublisherAware, MessageSourceAware {
    protected ApplicationEventPublisher eventPublisher;
    protected AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    private AuthenticationManager authenticationManager;
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private RememberMeServices rememberMeServices = new NullRememberMeServices();
    private RequestMatcher requiresAuthenticationRequestMatcher;
    private boolean continueChainBeforeSuccessfulAuthentication = false;
    private SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();
    private boolean allowSessionCreation = true;
    private AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

    protected AbstractAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
        this.setFilterProcessesUrl(defaultFilterProcessesUrl);
    }

    protected AbstractAuthenticationProcessingFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        Assert.notNull(requiresAuthenticationRequestMatcher, "requiresAuthenticationRequestMatcher cannot be null");
        this.requiresAuthenticationRequestMatcher = requiresAuthenticationRequestMatcher;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(this.authenticationManager, "authenticationManager must be specified");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if ("/login".equals(request.getServletPath())&&!StringUtils.isEmpty(username)&&!StringUtils.isEmpty(password)){
            String agent = request.getHeader("User-Agent");
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("http://localhost:9443/cas/login");
            HttpPost httpPost = new HttpPost("http://localhost:9443/cas/login");
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setHeader("User-Agent",agent);
//        httpPost.setHeader("Cookies",request.getCookies());
            try {
                CloseableHttpResponse closeableHttpResponse = client.execute(httpGet);
                int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
                System.out.println("statusCode:" + statusCode);
                if (200 == statusCode) {
                    for (Header head : closeableHttpResponse.getAllHeaders()) {
                        System.out.println(head);
                    }
                    HttpEntity loginEntity = closeableHttpResponse.getEntity();
                    String loginEntityContent = EntityUtils.toString(loginEntity);
                    final Document parse = Jsoup.parse(loginEntityContent);
                    String execution = parse.getElementsByAttributeValue("name", "execution").attr("value");
                    System.out.println(execution);

                    System.out.println();
                    System.out.println();
                    System.out.println();

                    String postParam = "username=admin&" +
                            "password=123&" +
                            "execution=" +
                            execution +
                            "&_eventId=submit&geolocation=";
                    httpPost.setEntity(new StringEntity(postParam, "UTF-8"));
                    final CloseableHttpResponse execute = client.execute(httpPost);
                    System.out.println(execute.getStatusLine().getStatusCode());

                    String cookie = "";
                    for (Header header : execute.getAllHeaders()) {
                        System.out.println(header);
                        if (header.toString().contains("Set-Cookie")){
                            cookie = header.toString().replace("Set-Cookie: ", "");
                        }
                    }
                    HttpEntity entity = execute.getEntity();
                    String re = EntityUtils.toString(entity);
                    System.out.println(re);
                    System.out.println(cookie);
//xin_chen=eyJhbGciOiJIUzUxMiJ9.WlhsS05tRllRV2xQYVVwRlVsVlphVXhEU21oaVIyTnBUMmxLYTJGWVNXbE1RMHBzWW0xTmFVOXBTa0pOVkVrMFVUQktSRXhWYUZSTmFsVXlTVzR3TGk1TFRtdHpZVGswTUhGd1dVaDNlVVpOTURWcE5tdEJMbTF0VUVaeGQwRmlNMVJrYXkxb2REbHZVV3hJVW01dmNtVjBkQzF6VjNSRFEyWnBlRFphZFZaMExXVjJSMDVrTUhaM2FrVnZjRjlRVldkRFp6TlNTRTVSVFhGaVJUQkxabHBYWW5OMVkxSXRVSEpQU1dJMlZrWjVWR3hPWlV0YVZscG9iMFZYVkd4cVkwVlljMUJZUXpGdFFqbGpYM2RYVUhWaWNVOUxNVFZIYVdkWWVEQllRME41Ym5CeE1rZHBWbFJVVTJ4SlNUZDRXRTVmVkRkV2QwSTRRMDFLZW5Cbk1XSlZNbkJHV0VJMlgyVXlNVWhQTVcxRE9VOTJVVVphTFdSU01XTmtSVGRNZWtsUFNsbEZaRVkwZWxZdFlVcERlVEYwY2xsblh6RkxPVXAxUzFkUGFXeE9NbHBWVVY5UVgwRndUSFp3VVdKSlVuSnFWbFU1Y1ZjdVNtVXRZM1V6VVV4TE9GVTRWbU15ZUZodU1WTkRVUT09.eMBFQXRbOrQbToGb8SrN3Iq1eoabRKdgBitnTDtBME1FYbAeyzLqjXBgjvWKJDmJUHTAzLcMLuEcM5Rquy-2eA; Version=1; Comment="CAS Cookie"; Domain=localhost; Path=/cas; HttpOnly
                    Cookie cookie1 = new Cookie("xin_chen", cookie.split("=")[1].split(";")[0]);
                    cookie1.setPath("/");
//                Cookie cookie2 = new Cookie("Set-Cookie", cookie);
//                cookie2.setDomain("http://localhost");
//                cookie2.setPath("/");
                    cookie1.setVersion(1);
                    response.addCookie(cookie1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

//            request.getRequestDispatcher("/gotoadmin").forward(request,response);
            response.sendRedirect("/ssh/admin");
            return;
        }


        if (!this.requiresAuthentication(request, response)) {
            chain.doFilter(request, response);
        } else {

            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Request is to process authentication");
            }

            Authentication authResult;
            try {
                authResult = this.attemptAuthentication(request, response);
                if (authResult == null) {
                    return;
                }

                this.sessionStrategy.onAuthentication(authResult, request, response);
            } catch (InternalAuthenticationServiceException var8) {
                this.logger.error("An internal error occurred while trying to authenticate the user.", var8);
                this.unsuccessfulAuthentication(request, response, var8);
                return;
            } catch (AuthenticationException var9) {
                this.unsuccessfulAuthentication(request, response, var9);
                return;
            }

            if (this.continueChainBeforeSuccessfulAuthentication) {
                chain.doFilter(request, response);
            }

            this.successfulAuthentication(request, response, chain, authResult);
        }
    }

    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return this.requiresAuthenticationRequestMatcher.matches(request);
    }

    public abstract Authentication attemptAuthentication(HttpServletRequest var1, HttpServletResponse var2) throws AuthenticationException, IOException, ServletException;

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Authentication success. Updating SecurityContextHolder to contain: " + authResult);
        }

        SecurityContextHolder.getContext().setAuthentication(authResult);
        this.rememberMeServices.loginSuccess(request, response, authResult);
        if (this.eventPublisher != null) {
            this.eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
        }

        this.successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Authentication request failed: " + failed.toString(), failed);
            this.logger.debug("Updated SecurityContextHolder to contain null Authentication");
            this.logger.debug("Delegating to authentication failure handler " + this.failureHandler);
        }

        this.rememberMeServices.loginFail(request, response);
        this.failureHandler.onAuthenticationFailure(request, response, failed);
    }

    protected AuthenticationManager getAuthenticationManager() {
        return this.authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(filterProcessesUrl));
    }

    public final void setRequiresAuthenticationRequestMatcher(RequestMatcher requestMatcher) {
        Assert.notNull(requestMatcher, "requestMatcher cannot be null");
        this.requiresAuthenticationRequestMatcher = requestMatcher;
    }

    public RememberMeServices getRememberMeServices() {
        return this.rememberMeServices;
    }

    public void setRememberMeServices(RememberMeServices rememberMeServices) {
        Assert.notNull(rememberMeServices, "rememberMeServices cannot be null");
        this.rememberMeServices = rememberMeServices;
    }

    public void setContinueChainBeforeSuccessfulAuthentication(boolean continueChainBeforeSuccessfulAuthentication) {
        this.continueChainBeforeSuccessfulAuthentication = continueChainBeforeSuccessfulAuthentication;
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void setAuthenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        Assert.notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    protected boolean getAllowSessionCreation() {
        return this.allowSessionCreation;
    }

    public void setAllowSessionCreation(boolean allowSessionCreation) {
        this.allowSessionCreation = allowSessionCreation;
    }

    public void setSessionAuthenticationStrategy(SessionAuthenticationStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
        Assert.notNull(successHandler, "successHandler cannot be null");
        this.successHandler = successHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
        Assert.notNull(failureHandler, "failureHandler cannot be null");
        this.failureHandler = failureHandler;
    }

    protected AuthenticationSuccessHandler getSuccessHandler() {
        return this.successHandler;
    }

    protected AuthenticationFailureHandler getFailureHandler() {
        return this.failureHandler;
    }
}
