package com.xinchen.ssh.core;

import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.List;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/5/26 20:50
 */
public abstract class AbstractBaseDao<T> extends HibernateDaoSupport implements BaseDao<T> {

    private SessionFactory sessionFactory;

    public AbstractBaseDao() {
    }

    public AbstractBaseDao(SessionFactory sessionFactory) {
        this.setSessionFactory(sessionFactory);
    }

    @Override
    public <T> T get(Class<T> entityClass, Serializable id) throws DataAccessException {
        return getHibernateTemplate().get(entityClass, id);
    }

    @Override
    public Object get(String entityName, Serializable id) throws DataAccessException {
        return getHibernateTemplate().get(entityName, id);
    }

    @Override
    public <T> List<T> loadAll(final Class<T> entityClass) throws DataAccessException {
        return getHibernateTemplate().loadAll(entityClass);
    }

    @Override
    public boolean contains(final Object entity) throws DataAccessException {
        return getHibernateTemplate().contains(entity);
    }

    @Override
    public void update(Object entity) throws DataAccessException {
        getHibernateTemplate().update(entity);
    }

    @Override
    public void saveOrUpdate(final Object entity) throws DataAccessException {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    @Override
    public void persist(final Object entity) throws DataAccessException {
        getHibernateTemplate().persist(entity);
    }

    @Override
    public void delete(Object entity) throws DataAccessException {
        getHibernateTemplate().delete(entity);
    }

    @Override
    public List<?> find(final String queryString, final Object... values) throws DataAccessException {
        return getHibernateTemplate().find(queryString, values);
    }
}
