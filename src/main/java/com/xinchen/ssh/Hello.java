package com.xinchen.ssh;

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
import sun.misc.IOUtils;

import java.io.IOException;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/6/9 23:26
 */
public class Hello {
    public static void main(String[] args) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:9443/cas/login");
        HttpPost httpPost = new HttpPost("http://localhost:9443/cas/login");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.79 Safari/537.36");
        try {
            CloseableHttpResponse response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("statusCode:" + statusCode);
            if (200 == statusCode) {
                for (Header head : response.getAllHeaders()) {
                    System.out.println(head);
                }
                HttpEntity loginEntity = response.getEntity();
                String loginEntityContent = EntityUtils.toString(loginEntity);
                final Document parse = Jsoup.parse(loginEntityContent);
                String execution = parse.getElementsByAttributeValue("name", "execution").attr("value");
                System.out.println(execution);

                System.out.println();
                System.out.println();
                System.out.println();

                String postParam = "username=admin&" +
                        "password=12213&" +
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

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
