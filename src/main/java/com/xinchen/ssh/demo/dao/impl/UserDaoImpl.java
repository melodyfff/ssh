package com.xinchen.ssh.demo.dao.impl;

import com.xinchen.ssh.demo.dao.IUserDao;
import com.xinchen.ssh.demo.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("userDao")
public class UserDaoImpl implements IUserDao {
    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public User get(Long id) {
        return (User) getCurrentSession().get(User.class,id);
    }

    @Override
    public void save(User user) {
        getCurrentSession().save(user);
    }

    @Override
    public void update(User user) {
        getCurrentSession().update(user);
    }

    @Override
    public void saveOrUpdate(User entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void delete(User user) {
        getCurrentSession().delete(user);
    }

    @Override
    public void deleteById(Long id) {
        getCurrentSession().delete(id);
    }

    @Override
    public List<User> findAll() {
        return getCurrentSession().createQuery("from User").list();
    }

    @Override
    public List<User> getByName(String userame) {
        String sql = "from User where user_name = :username";
        return getCurrentSession().createQuery(sql).setParameter("username",userame).list();
    }
}
