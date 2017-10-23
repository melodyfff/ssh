package com.xinchen.ssh.demo.service;

public interface I18nService {
    /**
     * 同步本机国际化环境
     */
    void reload();

    /**
     * 同步其他服务器国际化环境
     */
    void reloadOthers();
}
