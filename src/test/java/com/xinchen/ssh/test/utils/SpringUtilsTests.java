package com.xinchen.ssh.test.utils;

import com.xinchen.ssh.core.utils.SpringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试 通过BeanFactoryAware 获取 BeanFactory
 * @author Xin Chen
 * @date 2018/5/7 9:27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ssh-context.xml","classpath:ssh-security.xml"})
public class SpringUtilsTests {
    @Test
    public void test(){
        final BeanFactory beanFactory = SpringUtils.getBeanFactory();
    }
}
