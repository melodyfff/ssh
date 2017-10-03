package com.xinchen.ssh.demo.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T extends Serializable,K extends Serializable> {

    T get(K id);

    void save(T entity);

    void update(T entity);

    void saveOrUpdate(T entity);

    void delete(T entity);

    void deleteById(K id);

    List<T> findAll();
}
