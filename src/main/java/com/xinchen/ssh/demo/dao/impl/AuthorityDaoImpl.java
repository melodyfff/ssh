package com.xinchen.ssh.demo.dao.impl;

import com.xinchen.ssh.demo.dao.IAuthorityDao;
import com.xinchen.ssh.demo.entity.Authority;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("authorityDao")
public class AuthorityDaoImpl implements IAuthorityDao {


    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public Authority get(Long id) {
        return (Authority) getCurrentSession().get(Authority.class,id);
    }

    @Override
    public void save(Authority entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(Authority entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public void saveOrUpdate(Authority entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void delete(Authority entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        getCurrentSession().delete(id);
    }

    @Override
    public List<Authority> findAll() {
        return getCurrentSession().createQuery("from Authority").list();
    }
}
