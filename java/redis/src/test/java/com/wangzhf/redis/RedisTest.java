package com.wangzhf.redis;

import com.wangzhf.redis.util.JedisUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class RedisTest {

	JedisUtil jedisUtil = null;

	@Before
	public void init(){
		jedisUtil = JedisUtil.getInstance();
	}

	@Test
	public void testSet(){
		String[] values = new String[]{"1", "2", "3"};
		String key = "shopcart_user_001";
		long s = jedisUtil.sadd(key, 20, values);
		System.out.println(s);

		Set<String> set = jedisUtil.smember(key);
		System.out.println(set);

		long l = jedisUtil.srem(key, new String[]{"2"});
		System.out.println(l);
		Set<String> set2 = jedisUtil.smember(key);
		System.out.println(set2);
	}

	@Test
	public void testSetWithTx(){
		Jedis jedis = jedisUtil.getJedis();
		Transaction tx = jedis.multi();
		String key = "shopcart_user_001";
		try {
			String[] values = new String[]{"1", "2", "3"};
			Response s = tx.sadd(key, values);
			System.out.println(s.toString());

			int i=0;
			if (i == 0){
				throw new Exception("error");
			}

			Response l = tx.srem(key, new String[]{"2"});
			System.out.println(l.toString());

			tx.exec();
		} catch (Exception e) {
			e.printStackTrace();
			tx.discard();
		} finally {
			try {
				tx.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Set<String> set2 = jedisUtil.smember(key);
		System.out.println(set2);
	}

	@Test
	public void testWatch(){
		Jedis jedis = jedisUtil.getJedis();
		String key = "shopcart_user_001";
		String watch = jedis.watch(key);
		System.out.println("watch: " + watch);
		Transaction tx = jedis.multi();
		try {

			Thread.sleep(20000);

			Response l = tx.srem(key, new String[]{"5"});
			System.out.println(l.toString());

			List<Object> list = tx.exec();
			System.out.println(list);
		} catch (Exception e) {
			e.printStackTrace();
			tx.discard();
		} finally {

		}
		String un = jedis.unwatch();
		System.out.println(un);
	}



	@Test
	public void testZadd(){
		Jedis jedis = jedisUtil.getJedis();
		String key = "double11-prize";
		long l = jedis.zadd(key, 1, "ipad");
		System.out.println(l);
		l = jedis.zadd(key, 2, "upan");
		System.out.println(l);

		jedis.close();
	}

	/**
	 * 不开事务，watch无效
	 * @throws InterruptedException
	 */
	@Test
	public void testGetPrize() throws InterruptedException {
		Jedis jedis = jedisUtil.getJedis();
		String key = "double11-prize";
		String member = "ipad";
		jedis.watch(key);
		double s = jedis.zscore(key, member);
		Thread.sleep(20000);
		if(s <= 0){
			System.out.println("没有奖品");
		}else{
			System.out.println("old s: " + s);
			// update score
			double d = jedis.zincrby(key, -1, member);
			System.out.println("new : " + d);
		}
		jedis.unwatch();
	}

	@Test
	public void testGetPrize2() throws InterruptedException {
		Jedis jedis = jedisUtil.getJedis();
		String key = "double11-prize";
		String member = "ipad";
		jedis.watch(key);
		double s = jedis.zscore(key, member);
		Transaction tx = jedis.multi();
		Thread.sleep(20000);
		System.out.println(s);
		if(s <= 0){
			System.out.println("没有奖品");
		}else{
			System.out.println("old s: " + s);
			// update score
			Response d = tx.zincrby(key, -1, member);
			System.out.println("new : " + d.toString());
			List<Object> list =  tx.exec();
			System.out.println(list);
		}
		jedis.unwatch();
	}


	@After
	public void destory(){

	}

}
