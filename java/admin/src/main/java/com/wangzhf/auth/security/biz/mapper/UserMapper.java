package com.wangzhf.auth.security.biz.mapper;

import com.wangzhf.admin.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> findAll();
}
