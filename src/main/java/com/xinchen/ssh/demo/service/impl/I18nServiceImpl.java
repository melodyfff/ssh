package com.xinchen.ssh.demo.service.impl;

import com.xinchen.ssh.core.I18n.MessageSource;
import com.xinchen.ssh.demo.service.I18nService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@Service("I18nService")
public class I18nServiceImpl implements I18nService {
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
}
