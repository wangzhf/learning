package com.wangzhf.springboot.hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

    @RequestMapping("/hello")
    @ResponseBody
	// 针对某个方法跨域
	//@CrossOrigin("http://localhost")
    public String hello(){
        System.out.println("hello world!");
        return "hello world!";
    }

    @RequestMapping("/name")
    @ResponseBody
    public String sayName(String name){
        System.out.println("hello " + name);
        return "hello " + name;
    }


}
