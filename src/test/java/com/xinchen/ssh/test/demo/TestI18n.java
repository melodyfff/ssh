package com.xinchen.ssh.test.demo;

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
    private MessageSource messageSource;

    @Test
    public void test6(){
        Object[] params = {"John", new GregorianCalendar().getTime()};
        messageSource.getMessage("hello",params, Locale.ENGLISH);
        System.out.println(messageSource.getMessage("hello.test",params,Locale.ENGLISH));
    }
}
