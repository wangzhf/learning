package com.wangzhf.mq.rabbitmq.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewTask {

	static String TASk_QUEUE_NAME = "task_queue";

	public void sendMsg(String msg) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();

		// 消息持久化
		boolean durable = true;
		channel.queueDeclare(TASk_QUEUE_NAME, durable, false, false, null);
		channel.basicPublish("", TASk_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
		System.out.println("[x] Send '" + msg + "' ");

		channel.close();
		conn.close();
	}

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		for (int i = 0; i < 10; i++){
			NewTask s = new NewTask();
			s.sendMsg("hello " + i);
			Thread.currentThread().sleep(3000);
		}
	}

}
