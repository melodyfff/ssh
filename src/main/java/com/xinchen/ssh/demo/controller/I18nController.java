package com.xinchen.ssh.demo.controller;

import com.xinchen.ssh.core.I18n.MessageSource;
import com.xinchen.ssh.demo.service.I18nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

@Controller
public class I18nController {

    @Autowired
    @Qualifier("I18nService")
    private I18nService i18nService;

    @RequestMapping("reload")
    @ResponseBody
    public String reload(){
        //------------------------------------------------------------
        // 取得ServletContext
        //------------------------------------------------------------
        WebApplicationContext applicationContext  = ContextLoader.getCurrentWebApplicationContext();

        //------------------------------------------------------------
        // 设置国际化多语言
        //------------------------------------------------------------
        MessageSource messageSource = applicationContext.getBean(MessageSource.class);
        messageSource.reload();
        i18nService.reload();
        i18nService.reloadOthers();
        return "reload success";
    }
}
