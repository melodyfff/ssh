package com.xinchen.ssh.demo.service;

import com.xinchen.ssh.demo.entity.User;

import java.util.List;

public interface IUserService {

    List<User> load(String username);

}
