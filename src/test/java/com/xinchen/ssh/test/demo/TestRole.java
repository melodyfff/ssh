package com.xinchen.ssh.test.demo;

import com.xinchen.ssh.demo.dao.IRoleDao;
import com.xinchen.ssh.demo.entity.Authority;
import com.xinchen.ssh.demo.entity.Role;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ssh-context.xml"})
public class TestRole {
    private static final Logger LOGGER = Logger
            .getLogger(TestRole.class);

    @Autowired
    private IRoleDao roleDao;

    @Test
    public void test(){

        Authority authority = new Authority();
        authority.setId(2L);
        authority.setAuthorityName("查看用户");



        Role role = roleDao.get(2L);
        System.out.println(role);
        role.getAuthorityList().add(authority);
//
        roleDao.update(role);


    }
}
