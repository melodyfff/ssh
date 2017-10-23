package com.xinchen.ssh.core.httpInvoke;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.springframework.remoting.httpinvoker.HttpComponentsHttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

public class HttpInvokerProxyFactory <Client extends Object>{
    public Client getProxy(String serviceUrl, Class serviceInterface) {

        HttpComponentsHttpInvokerRequestExecutor httpComponentsHttpInvokerRequestExecutor = new HttpComponentsHttpInvokerRequestExecutor();
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager();
        cm.setMaxTotal(100);
        HttpClient httpclient = new DefaultHttpClient(cm);
        //设置超时时间
        httpComponentsHttpInvokerRequestExecutor.setConnectTimeout(200);
        httpComponentsHttpInvokerRequestExecutor.setHttpClient(httpclient);

        HttpInvokerProxyFactoryBean factoryBean = new HttpInvokerProxyFactoryBean();
        factoryBean.setServiceUrl(serviceUrl);
        factoryBean.setServiceInterface(serviceInterface);
        factoryBean.setHttpInvokerRequestExecutor(httpComponentsHttpInvokerRequestExecutor);
        factoryBean.afterPropertiesSet();


        return (Client) factoryBean.getObject();
    }
    /*public static void main(String[] args) {
        String serviceUrl = "http://meiqiu.me:8080/remote/app/service";
        AppService client = new HttpInvokerProxyFactory<AppService>().getProxy(md5ClientService, serviceUrl, AppService.class);
    }*/


}
