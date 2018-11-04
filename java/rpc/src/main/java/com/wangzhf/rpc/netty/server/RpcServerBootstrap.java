package com.wangzhf.rpc.netty.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RpcServerBootstrap {

	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("classpath:/netty/server/spring-server.xml");
	}
}
