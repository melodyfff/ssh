package com.xinchen.ssh.demo.controller;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.profile.CasProfile;
import org.pac4j.cas.profile.CasRestProfile;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.exception.HttpAction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Controller
public class HelloController {

    // 模拟用httpclient登录
    private final static String CAS_LOGIN_URL = "https://localhost:9443/cas/login";
    private final static String APP_URL = "http://localhost:8310/ssh";

    @RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
    public ModelAndView welcomePage(HttpServletRequest request, HttpServletResponse response) {
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

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Custom Login Form");
        model.addObject("message", "This is welcome page!");
        model.setViewName("hello");
        return model;

    }

    @RequestMapping(value = "/admin**", method = RequestMethod.GET)
    public ModelAndView adminPage() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Custom Login Form");
        model.addObject("message", "This is protected page!");
        model.setViewName("admin");

        return model;

    }

    @RequestMapping(value = "/gotoadmin", method = RequestMethod.POST)
    public ModelAndView gotoAdminPage() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Custom Login Form");
        model.addObject("message", "This is protected page!");
        model.setViewName("admin");

        return model;

    }

    //Spring Security see this :
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;

    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public ModelAndView testPreAuthorize() {

        return new ModelAndView("testPreAuthorize");

    }
}
