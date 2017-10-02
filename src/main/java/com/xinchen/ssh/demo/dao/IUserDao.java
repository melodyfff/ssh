package com.xinchen.ssh.demo.dao;

import com.xinchen.ssh.demo.entity.User;

public interface IUserDao {

    User getUser(Long id);

    void saveUser(User user);
}
