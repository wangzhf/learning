package com.wangzhf.mq.rabbitmq.workqueue;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Worker {

	static String TASk_QUEUE_NAME = "task_queue";

	public void getMsg(final long index) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();

		channel.queueDeclare(TASk_QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for message, To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String msg = new String(body, "UTF-8");
				System.out.println("Thread " + index + ": [x] Receive '" + msg + "' ");
			}
		};
		channel.basicConsume(TASk_QUEUE_NAME, true, consumer);
	}

	public static void main(String[] args) throws IOException, TimeoutException {
		for (int i = 0; i < 3; i++){
			Thread t = new Thread(){
				@Override
				public void run() {
					Worker r = new Worker();
					try {
						r.getMsg(Thread.currentThread().getId());
					} catch (IOException e) {
						e.printStackTrace();
					} catch (TimeoutException e) {
						e.printStackTrace();
					}
				}
			};
			t.start();
		}
	}

}
