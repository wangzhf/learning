package com.wangzhf.rpc.netty.server.service;

import com.wangzhf.rpc.netty.server.RpcService;

@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {
	@Override
	public String hello(String name) {
		return "Hello, " + name;
	}
}
