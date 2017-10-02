package com.xinchen.ssh.test.demo;


import com.xinchen.ssh.core.exception.ApplicationException;
import com.xinchen.ssh.demo.dao.IUserDao;
import com.xinchen.ssh.demo.entity.Role;
import com.xinchen.ssh.demo.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.xinchen.ssh.demo.entity.AcctUser;
import com.xinchen.ssh.demo.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**   
* @Description: 
* @author xinchen   
* @date 2016年10月23日 下午8:19:00 
* @version V1.0   
*/
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:ssh-context.xml"})
public class TestUserService {  
  
    private static final Logger LOGGER = Logger  
            .getLogger(TestUserService.class);  
  
    @Autowired  
    private UserService userService;

    @Autowired
    private IUserDao userDao;

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;
  
//    @Test
//    public void save() {
//        AcctUser acctUser = new AcctUser();
//        acctUser.setId(UUID.randomUUID().toString());
//        acctUser.setNickName("andy");
//        acctUser.setRegisterTime(new Date());
//        acctUser.setTelephone("13022221111");
//        String id = userService.save(acctUser);
//        LOGGER.info(JSON.toJSONString(id));
//    }
    @Test
    @Transactional
    public void query(){
        AcctUser acctUser = userService.get("14ff5253-5912-4a3f-b51b-f50d9da0271d");
        LOGGER.info(JSON.toJSONString(acctUser));
    }
    @Test
    public void testException() throws Exception {
        try {
            int a  = 1/0;
        }catch (RuntimeException e){
            LOGGER.error(e);
            throw new ApplicationException(e);
        }
    }
    @Test

    public void test3(){

        List<Role> list = new ArrayList<>();

        Role role = new Role();
        role.setId(1);
        role.setRoleName("admin");
        list.add(role);

        User user = new User();

        user.setRegistrTime(new Date());
        user.setUserName("test");
        user.setPassword("test");
        user.setRoleList(list);

        userDao.saveUser(user);

    }

    @Test
    @Transactional
    public void test4(){


        System.out.println(userDao.getUser(2L));

    }

}  
