package com.xinchen.ssh.demo.dao;

import com.xinchen.ssh.demo.entity.User;

import java.util.List;

public interface IUserDao extends BaseDao<User,Long>{
    List<User> getByName(String userame);
}
