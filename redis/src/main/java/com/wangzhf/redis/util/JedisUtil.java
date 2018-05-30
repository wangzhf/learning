package com.wangzhf.redis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import sun.rmi.runtime.Log;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class JedisUtil {

	private static JedisPool jedisPool = null;

	private JedisUtil(){}

	static {
		Properties prop = PropertiesUtil.loadProperties("redis.properties");
		String host = prop.getProperty("redis.host");
		int port = Integer.parseInt(prop.getProperty("redis.port"));
		String pass = prop.getProperty("redis.pass");
		int timeout = Integer.parseInt(prop.getProperty("redis.timeout"));
		int maxIdle = Integer.parseInt(prop.getProperty("redis.maxIdle"));
		int maxTotal = Integer.parseInt(prop.getProperty("redis.maxTotal"));
		int maxWaitMillis = Integer.parseInt(prop.getProperty("redis.maxWaitMillis"));
		boolean testOnBorrow = Boolean.parseBoolean(prop.getProperty("redis.testOnBorrow"));

		JedisPoolConfig config = new JedisPoolConfig();
		// 控制一个pool可分配多少个jedis实例，通过pool.getResource()获取
		// 如果赋值为-1，表示不限制，如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted（耗尽）。
		config.setMaxTotal(maxTotal);
		// 控制一个pool最多有多少个状态为idle（空闲）的jedis实例
		config.setMaxIdle(maxIdle);
		// 表示当borrow（引入）一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException
		config.setMaxWaitMillis(maxWaitMillis);
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis是可用的
		config.setTestOnBorrow(testOnBorrow);

		jedisPool = new JedisPool(config, host, port, timeout);
	}

	/**
	 * 从jedis连接池中获取jedis对象
	 * @return
	 */
	public Jedis getJedis(){
		return jedisPool.getResource();
	}

	private static final JedisUtil jedisUtil = new JedisUtil();

	public static JedisUtil getInstance() {
		return jedisUtil;
	}

	/**
	 * 回收jedis
	 * @param jedis
	 */
	private void returnJedis(Jedis jedis) {
		if(null != jedis) {
			jedis.close();
		}
	}

	/**
	 * 销毁连接
	 * @param jedis
	 */
	private static void returnBrokenResource(Jedis jedis) {
		if(jedis != null ){
			jedis.close();
		}
	}

	/**
	 * 添加sorted set
	 * @param key
	 * @param value
	 * @param score
	 */
	public void zadd(String key, String value, double score) {
		Jedis jedis = getJedis();
		jedis.zadd(key, score, value);
		returnJedis(jedis);
	}

	/**
	 * 返回指定位置的集合元素，0为第一个元素，-1为最后一个元素
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrange(String key, int start, int end) {
		Jedis jedis = getJedis();
		Set<String> set = jedis.zrange(key, start, end);
		returnJedis(jedis);
		return set;
	}

	/**
	 * 获取给定区间的元素，按照权重由高到低排序
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrevrange(String key, int start, int end) {
		Jedis jedis = getJedis();
		Set<String> set = jedis.zrevrange(key, start, end);
		returnJedis(jedis);
		return set;
	}

	/**
	 * 添加对应关系，如果对应关系已存在，则覆盖
	 * @param key
	 * @param map 对应关系
	 * @return 状态，成功则返回ok
	 */
	public String hmset(String key, Map<String, String> map) {
		Jedis jedis = getJedis();
		String s = jedis.hmset(key, map);
		returnJedis(jedis);
		return s;
	}

	/**
	 * 向List头部追加记录
	 * @param key
	 * @param value
	 * @return
	 */
	public long lpush(String key, String value) {
		Jedis jedis = getJedis();
		long count = 0;
		try {
			count = jedis.rpush(key, value);
		} finally {
			returnJedis(jedis);
		}
		return count;
	}

	/**
	 * 向List尾部追加记录
	 * @param key
	 * @param value
	 * @return
	 */
	public long rpush(String key, String value) {
		Jedis jedis = getJedis();
		long count = 0;
		try {
			count = jedis.rpush(key, value);
		} finally {
			returnJedis(jedis);
		}
		return count;
	}

	/**
	 * 删除
	 * @param key
	 * @return
	 */
	public long del(String key) {
		Jedis jedis = getJedis();
		long s = jedis.del(key);
		returnJedis(jedis);
		return s;
	}

	/**
	 * 从集合中删除成员
	 */
	public long zrem(String key, String... value) {
		Jedis jedis = getJedis();
		long s = jedis.zrem(key, value);
		returnJedis(jedis);
		return s;
	}

	public void saveValueByKey(int dbIndex, byte[] key, byte[] value, int expireTime) {
		Jedis jedis = null;
		boolean isBroken = false;
		try {
			jedis = getJedis();
			jedis.select(dbIndex);
			jedis.set(key, value);
			if(expireTime > 0) {
				jedis.expire(key, expireTime);
			}
		} finally {
			returnJedis(jedis);
		}
	}

	public byte[] getValueByKey(int dbIndex, byte[] key){
		Jedis jedis = null;
		byte[] result = null;
		boolean isBroken = false;
		try {

		} catch (Exception e) {
			isBroken = true;
			throw  e;
		} finally {
			returnBrokenResource(jedis);
		}
		return result;
	}

	/**
	 * 获取总数量
	 */
	public long zcard(String key) {
		Jedis jedis = getJedis();
		long count = jedis.zcard(key);
		returnJedis(jedis);
		return count;
	}

	/**
	 * 是否存在key
	 */
	public boolean exists(String key) {
		Jedis jedis = getJedis();
		boolean exists = jedis.exists(key);
		return exists;
	}

	/**
	 * 重命名key
	 */
	public String rename(String oldKey, String newKey) {
		Jedis jedis = getJedis();
		String result = jedis.rename(oldKey, newKey);
		returnJedis(jedis);
		return result;
	}

	/**
	 * 设置失效时间
	 */
	public void expire(String key, int seconds) {
		Jedis jedis = getJedis();
		jedis.expire(key, seconds);
		returnJedis(jedis);
	}

	/**
	 * 删除失效时间
	 */
	public void persist(String key) {
		Jedis jedis = getJedis();
		jedis.persist(key);
		returnJedis(jedis);
	}

	/**
	 * 添加一个键值对，如果键值存在，不添加，如果不存在，添加完成之后设置键的有效期
	 */
	public void setnxWithTimeout(String key, String value, int timeout) {
		Jedis jedis = getJedis();
		if(0 != jedis.setnx(key, value)){
			jedis.expire(key, timeout);
		}
		returnJedis(jedis);
	}

	/**
	 * 返回指定key序列值
	 */
	public long incr(String key) {
		Jedis jedis = getJedis();
		long l = jedis.incr(key);
		returnJedis(jedis);
		return l;
	}

	/**
	 * 获取当前时间
	 */
	public long currentTimeSecond() {
		Long l = 0l;
		Jedis jedis = getJedis();
		Object obj = jedis.eval("return jedis.call('TIME')", 0);
		if(obj != null) {
			List<String> list = (List<String>) obj;
			l = Long.valueOf(list.get(0));
		}
		returnJedis(jedis);
		return l;
	}

	/**
	 * set集合操作
	 */
	public long sadd(String key, int timeout, String... values){
		Jedis jedis = getJedis();
		long ret = jedis.sadd(key, values);
		if(timeout > 0) {
			jedis.expire(key, timeout);
		}
		returnJedis(jedis);
		return ret;
	}

	/**
	 * 获取指定key的set集合
	 * @param key
	 * @return
	 */
	public Set<String> smember(String key){
		Jedis jedis = getJedis();
		Set<String> set =  jedis.smembers(key);
		returnJedis(jedis);
		return set;
	}

	/**
	 * 移除集合中的一个或多个成员
	 */
	public long srem(String key, String... values){
		Jedis jedis = getJedis();
		long l = jedis.srem(key, values);
		returnJedis(jedis);
		return l;
	}


}
