package com.xinchen.ssh.demo.controller;

import com.xinchen.ssh.core.I18n.MessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

@Controller
public class I18nController {


    @RequestMapping("reload")
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
        return "reload success";
    }
}
