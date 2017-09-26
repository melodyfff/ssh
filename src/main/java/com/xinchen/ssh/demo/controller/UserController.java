package com.xinchen.ssh.demo.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.xinchen.ssh.demo.entity.AcctUser;
import com.xinchen.ssh.demo.service.UserService;

/**
 * @Description:
 * @author xinchen
 * @date 2016年10月23日 下午9:14:23
 * @version V1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger LOGGER = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@GetMapping("/showInfos")
	public  List<AcctUser> showUserInfos() {
		LOGGER.info("查询用户全部用户");
		List<AcctUser> userInfos = userService.findAll();
		return userInfos;
	}

	@GetMapping("/showInfo/{userId}")
	public AcctUser showUserInfo( @PathVariable String userId) {
		LOGGER.info("查询用户：" + userId);
		AcctUser userInfo = userService.get(userId);
		return userInfo;
	}

}
