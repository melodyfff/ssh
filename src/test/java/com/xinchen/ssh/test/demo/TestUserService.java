package com.xinchen.ssh.test.demo;


import com.xinchen.ssh.core.exception.ApplicationException;
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
    public void query(){
        AcctUser acctUser = userService.load("6e5afb1d-50e1-45fe-b6fe-b9e399415387");
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
    public void test(){
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        com.xinchen.ssh.demo.entity.Test test1 = new com.xinchen.ssh.demo.entity.Test();
        test1.setName("test");
        test1.setEmail("20");

        session.persist(test1);

        transaction.commit();

        session.close();

    }
  
}  
