package com.wangzhf.ins.user.controller;

import com.wangzhf.ins.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/user")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final String template = "Hello, %s!";

    private final AtomicInteger counter = new AtomicInteger();

    @RequestMapping("/login")
    public User login(@RequestParam("username") String userName,
                      @RequestParam("password") String password) {
        logger.debug("username: {}, password: {}", userName, password);
        return new User(userName, password);
    }
}
