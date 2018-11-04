package com.wangzhf.rpc.netty.client;

import com.wangzhf.rpc.netty.server.service.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RpcClientBootstrap {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context =
				new ClassPathXmlApplicationContext("classpath:/netty/client/spring-client.xml");
		RpcProxy proxy = context.getBean(RpcProxy.class);
		HelloService service = proxy.create(HelloService.class);
		String ret = service.hello("wangzhf");
		System.out.println(ret);
		context.close();
	}
}
