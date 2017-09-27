package com.xinchen.ssh.demo.controller;

import com.xinchen.ssh.demo.entity.AcctUser;
import com.xinchen.ssh.demo.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private transient UserService userService;

    @InjectMocks
    private UserController userController;


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void showUserInfos() throws Exception {
        List<AcctUser> list = userController.showUserInfos();
    }

}