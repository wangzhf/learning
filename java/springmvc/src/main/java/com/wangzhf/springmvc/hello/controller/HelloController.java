package com.wangzhf.springmvc.hello.controller;

import com.wangzhf.springmvc.hello.domain.UserInfo;
import com.wangzhf.springmvc.hello.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hello")
public class HelloController {

	@Autowired
	private UserInfoService userInfoService;

	@RequestMapping("/say")
	@ResponseBody
	public String sayHello(String name){
		System.out.println("hello, " + name);
		return "hello, " + name;
	}

	@RequestMapping("/users")
	@ResponseBody
	public List<UserInfo> getUserList(){
		return userInfoService.getUserList();
	}

	@RequestMapping("/userList")
	@ResponseBody
	public Map<String, Object> getUserInfoList(){
		List<UserInfo> list = userInfoService.getUserList();
		Map<String, Object> ret = new HashMap<>();
		ret.put("code", 1);
		ret.put("data", list);
		return ret;
	}

}
