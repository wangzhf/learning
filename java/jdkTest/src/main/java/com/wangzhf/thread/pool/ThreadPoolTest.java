package com.wangzhf.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

	public static void main(String[] args) {
		testFixedThreadPool();
	}

	public static void testFixedThreadPool(){
		ExecutorService service = Executors.newFixedThreadPool(10);
		System.out.println(service.getClass().getName());
		service.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getId() +
					Thread.currentThread().getName());
			}
		});

		service.shutdown();
	}

}
