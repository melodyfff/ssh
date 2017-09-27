package com.xinchen.ssh.demo.service.impl;

import com.xinchen.ssh.core.exception.ApplicationException;
import com.xinchen.ssh.demo.dao.UserDao;
import com.xinchen.ssh.demo.entity.AcctUser;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoImplTest {


    private static final Logger LOGGER = Logger
            .getLogger(UserDaoImplTest.class);

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    private  AcctUser acctUser;

    @Test(expected = ApplicationException.class)
    public void testException() throws Exception {
        try {
            int a  = 1/0;
        }catch (RuntimeException e){
            LOGGER.error(e);
            throw new ApplicationException(e);
        }
    }

    @Before
    public void setUp() throws Exception {
        acctUser  = mock(AcctUser.class);
    }

    @Test
    public void load() throws Exception {
        userService.load("14ff5253-5912-4a3f-b51b-f50d9da0271d");
    }

    @Test
    public void get() throws Exception {
        userService.get("14ff5253-5912-4a3f-b51b-f50d9da0271d");
    }

    @Test
    public void findAll() throws Exception {
        userService.findAll();
    }

    @Test
    public void persist() throws Exception {
        userService.persist(acctUser);
    }

    @Test
    public void save() throws Exception {
        //        AcctUser acctUser = new AcctUser();
//        acctUser.setId(UUID.randomUUID().toString());
//        acctUser.setNickName("andy");
//        acctUser.setRegisterTime(new Date());
//        acctUser.setTelephone("13022221111");
//        String id = userService.save(acctUser);
//        LOGGER.info(JSON.toJSONString(id));
    }

    @Test
    public void saveOrUpdate() throws Exception {
        userService.saveOrUpdate(acctUser);
    }

    @Test
    public void delete() throws Exception {
        userService.delete("1");
    }

    @Test
    public void flush() throws Exception {
        userService.flush();
    }

}