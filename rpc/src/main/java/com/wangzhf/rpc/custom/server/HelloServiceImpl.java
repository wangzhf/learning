package com.wangzhf.rpc.custom.server;

public class HelloServiceImpl implements HelloService {
	@Override
	public String sayHi(String name) {
		return "Hi, " + name;
	}
}
