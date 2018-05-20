package com.wangzhf.springmvc.hello.mapper;

import com.wangzhf.springmvc.hello.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserInfoMapper {

	/**
	 * 获取所有用户信息
	 * @return
	 */
	List<UserInfo> getUserList();
}
