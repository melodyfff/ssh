package com.xinchen.ssh.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
/*@RequestMapping("user")*/
public class IndexController {
	
	@RequestMapping("test")
	public String modal(){
		return "test";	
	}
	@RequestMapping("user/test")
	public String modal2(){
		return "user/test";	
	}
	
	
}
