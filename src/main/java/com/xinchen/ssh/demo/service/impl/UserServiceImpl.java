package com.xinchen.ssh.demo.service.impl;

import com.xinchen.ssh.demo.dao.IUserDao;
import com.xinchen.ssh.demo.entity.User;
import com.xinchen.ssh.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> load(String username) {
        return userDao.getByName(username);
    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public void update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.update(user);
    }


}
