package com.wangzhf.hello.controller;

import com.wangzhf.hello.domain.Greeting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@ConfigurationProperties(prefix = "person")
public class HelloController {

    private final AtomicLong counter = new AtomicLong();

    private static final String template = "Hello %s!";


    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Greeting gretting(@RequestParam("name") String name){
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    public String name;
    public String content;

    @RequestMapping("/defaultName")
    public String defaultName(){
        System.out.println("name: " + name);
        System.out.println("content: " + content);
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
