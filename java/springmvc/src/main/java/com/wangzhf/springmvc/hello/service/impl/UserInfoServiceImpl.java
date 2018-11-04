package com.wangzhf.springmvc.hello.service.impl;

import com.wangzhf.springmvc.hello.domain.UserInfo;
import com.wangzhf.springmvc.hello.mapper.UserInfoMapper;
import com.wangzhf.springmvc.hello.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public List<UserInfo> getUserList() {
		return userInfoMapper.getUserList();
	}
}
