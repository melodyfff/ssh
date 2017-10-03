package com.xinchen.ssh.demo.dao.impl;

import com.xinchen.ssh.demo.dao.IRoleDao;
import com.xinchen.ssh.demo.entity.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("roleDao")
public class RoleDaoImpl implements IRoleDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }


    @Override
    public Role get(Long id) {
        return (Role) getCurrentSession().get(Role.class,id);
    }

    @Override
    public void save(Role entity) {
        getCurrentSession().save(entity);
    }



    @Override
    public void update(Role entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public void saveOrUpdate(Role entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void delete(Role entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        getCurrentSession().delete(id);
    }

    @Override
    public List<Role> findAll() {
        return getCurrentSession().createQuery("from Role").list();
    }
}
