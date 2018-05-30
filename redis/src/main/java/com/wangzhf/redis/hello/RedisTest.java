package com.wangzhf.redis.hello;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class RedisTest {

	private static String host = "localhost";
	private static int port = 6379;

	static Jedis jedis = null;

	static {
		jedis = new Jedis(host, port);
	}

	public static void main(String[] args) {
		//testPing();
		//testString();
		testList();
	}

	public static void testList(){
		String[] arrs = new String[]{"zhangsan", "lisi", "wangwu"};
		jedis.lpush("name-list", arrs);

		List<String> list = jedis.lrange("name-list", 0, 2);
		System.out.println(list.toString());
		jedis.close();
	}

	public static void testString(){
		jedis.set("name", "zhangsan");
		System.out.println(jedis.get("name"));
		jedis.close();
	}

	public static void testPing(){
		String ping = jedis.ping();
		System.out.println(ping);
		jedis.close();
	}
}
