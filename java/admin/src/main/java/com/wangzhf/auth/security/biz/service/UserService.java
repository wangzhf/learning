package com.wangzhf.auth.security.biz.service;

import com.wangzhf.admin.user.domain.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
}
