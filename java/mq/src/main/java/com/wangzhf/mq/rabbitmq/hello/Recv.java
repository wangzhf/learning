package com.wangzhf.mq.rabbitmq.hello;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {

	static String QUEUE_NAME = "hello";

	public void getMsg(final int index) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for message, To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String msg = new String(body, "UTF-8");
				System.out.println(index + ": [x] Receive '" + msg + "' ");
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}

	public static void main(String[] args) throws IOException, TimeoutException {
		for (int i = 0; i < 10; i++){
			Recv r = new Recv();
			r.getMsg(i);
		}
	}

}
