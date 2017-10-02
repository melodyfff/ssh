package com.xinchen.ssh.demo.dao.impl;

import com.xinchen.ssh.demo.dao.IUserDao;
import com.xinchen.ssh.demo.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("iuserDao")
public class IUserDaoImpl implements IUserDao {
    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public User getUser(Long id) {
        return (User) getCurrentSession().get(User.class,id);
    }

    @Override
    public void saveUser(User user) {
        getCurrentSession().save(user);
    }
}
