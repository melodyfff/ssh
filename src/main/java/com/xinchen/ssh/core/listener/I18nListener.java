package com.xinchen.ssh.core.listener;

import com.xinchen.ssh.core.I18n.MessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;

public class I18nListener implements ServletContextListener {

    /**
     * context初始化时激发
     */
    @Override
    public void contextInitialized(ServletContextEvent e) {
        //------------------------------------------------------------
        // 取得ServletContext
        //------------------------------------------------------------
        ServletContext context = e.getServletContext();
        WebApplicationContext applicationContext  = WebApplicationContextUtils.getWebApplicationContext(context);

        //------------------------------------------------------------
        // 设置国际化多语言
        //------------------------------------------------------------
        MessageSource messageSource = applicationContext.getBean(MessageSource.class);
        messageSource.reload();
    }

    /**
     * context删除时激发
     */
    @Override
    public void contextDestroyed(ServletContextEvent e) {
    }

    /**
     * 创建一个 session时激发
     * @param e
     */
    public void sessionCreated(HttpSessionEvent e) {
    }

    /**
     * 当一个 session失效时激发
     * @param e
     */
    public void sessionDestroyed(HttpSessionEvent e) {
    }

    /**
     * 设置 context的属性，它将激发attributeReplaced或attributeAdded方法
     * @param e
     */
    public void setContext(HttpSessionEvent e) {
    }

    /**
     * 增加一个新的属性时激发
     * @param e
     */
    public void attributeAdded(ServletContextAttributeEvent e) {
    }

    /**
     * 删除一个新的属性时激发
     * @param e
     */
    public void attributeRemoved(ServletContextAttributeEvent e) {
    }

    /**
     * 属性被替代时激发
     * @param e
     */
    public void attributeReplaced(ServletContextAttributeEvent e) {
    }

}
