package com.xinchen.ssh.demo.dao.impl;

import com.xinchen.ssh.demo.dao.I18nResourceDao;
import com.xinchen.ssh.demo.entity.I18nResource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("i18nResourceDao")
public class I18nResourceDaoImpl implements I18nResourceDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public I18nResource get(Long id) {
        return (I18nResource) getCurrentSession().get(I18nResource.class,id);
    }

    @Override
    public void save(I18nResource entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(I18nResource entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public void saveOrUpdate(I18nResource entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void delete(I18nResource entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        getCurrentSession().delete(id);
    }

    @Override
    public List<I18nResource> findAll() {
        return getCurrentSession().createQuery("from I18nResource").list();
    }
}
