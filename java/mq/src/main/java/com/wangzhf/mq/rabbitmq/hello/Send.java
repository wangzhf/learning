package com.wangzhf.mq.rabbitmq.hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

	static String QUEUE_NAME = "hello";

	public void sendMsg(String msg) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
		System.out.println("[x] Send '" + msg + "' ");

		channel.close();
		conn.close();
	}

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		for (int i = 0; i < 10; i++){
			Send s = new Send();
			s.sendMsg("hello " + i);
			Thread.currentThread().sleep(3000);
		}
	}

}
