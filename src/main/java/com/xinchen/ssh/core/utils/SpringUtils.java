package com.xinchen.ssh.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * 通过继承 BeanFactoryAware 获取 BeanFactory
 * @author Xin Chen
 * @date 2018/5/7 9:24
 */
@Component
public class SpringUtils implements BeanFactoryAware {

    private static BeanFactory beanFactory;


    /**
     * get beanFactory
     *
     * @return beanFactory
     */
    public static BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
    }
}
