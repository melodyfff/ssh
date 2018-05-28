package com.xinchen.ssh.core;

import org.springframework.dao.DataAccessException;

import java.io.Serializable;
import java.util.List;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/5/28 23:13
 */
public interface BaseDao<T> {
    <T> T get(Class<T> entityClass, Serializable id) throws DataAccessException;


     Object get(String entityName, Serializable id) throws DataAccessException;

     <T> List<T> loadAll(final Class<T> entityClass) throws DataAccessException;

    boolean contains(final Object entity) throws DataAccessException;

    void update(Object entity) throws DataAccessException;

    void saveOrUpdate(final Object entity) throws DataAccessException;

    void persist(final Object entity) throws DataAccessException;

    void delete(Object entity) throws DataAccessException;

     List<?> find(final String queryString, final Object... values) throws DataAccessException;
}
