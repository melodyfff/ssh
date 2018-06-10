package com.xinchen.ssh.core.filter;

import org.jasig.cas.client.util.CommonUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/6/10 22:54
 */
public class CasAuthenticationEntryPoint implements AuthenticationEntryPoint, InitializingBean {
    private ServiceProperties serviceProperties;
    private String loginUrl;
    private boolean encodeServiceUrlWithSessionId = true;

    public CasAuthenticationEntryPoint() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasLength(this.loginUrl, "loginUrl must be specified");
        Assert.notNull(this.serviceProperties, "serviceProperties must be specified");
        Assert.notNull(this.serviceProperties.getService(), "serviceProperties.getService() cannot be null.");
    }

    @Override
    public final void commence(HttpServletRequest servletRequest, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        String urlEncodedService = this.createServiceUrl(servletRequest, response);
        String redirectUrl = this.createRedirectUrl(urlEncodedService);
        this.preCommence(servletRequest, response);
        // 写死
//        redirectUrl = "http://localhost:8180/ssh/login";
        response.sendRedirect(redirectUrl);
    }

    protected String createServiceUrl(HttpServletRequest request, HttpServletResponse response) {
        return CommonUtils.constructServiceUrl((HttpServletRequest)null, response, this.serviceProperties.getService(), (String)null, this.serviceProperties.getArtifactParameter(), this.encodeServiceUrlWithSessionId);
    }

    protected String createRedirectUrl(String serviceUrl) {
        return CommonUtils.constructRedirectUrl(this.loginUrl, this.serviceProperties.getServiceParameter(), serviceUrl, this.serviceProperties.isSendRenew(), false);
    }

    protected void preCommence(HttpServletRequest request, HttpServletResponse response) {
    }

    public final String getLoginUrl() {
        return this.loginUrl;
    }

    public final ServiceProperties getServiceProperties() {
        return this.serviceProperties;
    }

    public final void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public final void setServiceProperties(ServiceProperties serviceProperties) {
        this.serviceProperties = serviceProperties;
    }

    public final void setEncodeServiceUrlWithSessionId(boolean encodeServiceUrlWithSessionId) {
        this.encodeServiceUrlWithSessionId = encodeServiceUrlWithSessionId;
    }

    protected boolean getEncodeServiceUrlWithSessionId() {
        return this.encodeServiceUrlWithSessionId;
    }
}
