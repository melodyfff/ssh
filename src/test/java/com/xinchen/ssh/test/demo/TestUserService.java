package com.xinchen.ssh.test.demo;


import com.xinchen.ssh.core.exception.ApplicationException;
import com.xinchen.ssh.demo.dao.IUserDao;
import com.xinchen.ssh.demo.entity.Authority;
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


import java.util.*;

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

    public void test3(){

        Authority authority = new Authority();
        authority.setId(1L);
        authority.setAuthorityName("管理员权限");

        Set<Authority> authorityList = new HashSet<>();
        authorityList.add(authority);

        Set<Role> list = new HashSet<>();

        Role role = new Role();
        role.setId(2);
        role.setRoleName("users");
        role.setAuthorityList(authorityList);
        list.add(role);

        User user = new User();

        user.setRegistrTime(new Date());
        user.setUserName("admin");
        user.setPassword("test3");
        user.setRoleList(list);

        userDao.save(user);

    }

    @Test
    public void test4(){

        Role role = new Role();
        role.setId(2);
        role.setRoleName("users");



        User user = userDao.get(25L);
        System.out.println(user);
//        user.getRoleList().add(role);
//        user.setUserName("admin");
//        user.getRoleList().get(0).setId(1);
//        user.getRoleList().get(0).setRoleName("admin");
//        userDao.updateUser(user);
//        userDao.saveOrUpdate(user);
//        System.out.println(userDao.findAll());

    }

}  
