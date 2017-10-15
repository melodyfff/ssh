package com.xinchen.ssh.test.demo;

import com.xinchen.ssh.demo.dao.I18nResourceDao;
import com.xinchen.ssh.demo.entity.I18nResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.GregorianCalendar;
import java.util.Locale;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:ssh-*.xml"})
public class TestI18n {

    @Autowired
    private I18nResourceDao i18nResourceDao;

    @Autowired
    private MessageSource messageSource;



    @Test
    public void test6(){
        Object[] params = {"John", new GregorianCalendar().getTime()};
        messageSource.getMessage("hello",params, Locale.ENGLISH);
        System.out.println(messageSource.getMessage("hello.test",params,Locale.CHINA));
        System.out.println(messageSource.getMessage("my.test",params,Locale.CHINA));
//        messageSource.reload();
    }

    @Test
    public void test(){
        I18nResource resource = new I18nResource();
        resource.setName("my.test");
        resource.setLanguage("en");
        resource.setContent("hello world!");

        I18nResource resource2 = new I18nResource();
        resource2.setName("my.test");
        resource2.setLanguage("zh");
        resource2.setContent("你好你好");

        i18nResourceDao.save(resource);
        i18nResourceDao.save(resource2);

    }
}
