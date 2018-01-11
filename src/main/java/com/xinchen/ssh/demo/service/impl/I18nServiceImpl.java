package com.xinchen.ssh.demo.service.impl;

import com.xinchen.ssh.core.I18n.MessageSource;
import com.xinchen.ssh.core.httpInvoke.HttpInvokerProxyFactory;
import com.xinchen.ssh.demo.service.I18nService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@Service("I18nService")
public class I18nServiceImpl implements I18nService {

    final static String URL = "http://127.0.0.1:8080/ssh/remote/reload";

    @Override
    public void reload() {
        //------------------------------------------------------------
        // 取得ServletContext
        //------------------------------------------------------------
        WebApplicationContext applicationContext  = ContextLoader.getCurrentWebApplicationContext();

        //------------------------------------------------------------
        // 设置国际化多语言
        //------------------------------------------------------------
        MessageSource messageSource = applicationContext.getBean(MessageSource.class);
        messageSource.reload();
        System.out.println("reload success");
    }

    @Override
    public void reloadOthers() {
        I18nService service = new HttpInvokerProxyFactory<I18nService>().
                getProxy(URL,I18nService.class);
        service.reload();
    }


}
