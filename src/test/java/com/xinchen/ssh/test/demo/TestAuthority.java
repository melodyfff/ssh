package com.xinchen.ssh.test.demo;

import com.xinchen.ssh.demo.dao.IAuthorityDao;
import com.xinchen.ssh.demo.entity.Authority;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ssh-context.xml"})
public class TestAuthority {
    private static final Logger LOGGER = Logger
            .getLogger(TestAuthority.class);

    @Autowired
    private IAuthorityDao authorityDao;

    @Test
    public void test(){
        Authority authority = authorityDao.get(1L);
        System.out.println(authority);
        authority.setAuthorityName("管理员尊贵权限");
        authorityDao.update(authority);

    }
}
