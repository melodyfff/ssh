package com.xinchen.ssh.test.demo;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration(locations = {"classpath*:spring-*.xml"})  
public class TestUserService {  
  
    private static final Logger LOGGER = Logger  
            .getLogger(TestUserService.class);  
  
    @Autowired  
    private UserService userService;  
  
    @Test  
    public void save() {  
        AcctUser acctUser = new AcctUser();  
        acctUser.setId(UUID.randomUUID().toString());  
        acctUser.setNickName("andy");  
        acctUser.setRegisterTime(new Date());  
        acctUser.setTelephone("13022221111");  
        String id = userService.save(acctUser);  
        LOGGER.info(JSON.toJSONString(id));  
    }  
  
}  
