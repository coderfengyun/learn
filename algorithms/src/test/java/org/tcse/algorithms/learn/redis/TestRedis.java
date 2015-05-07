package org.tcse.algorithms.learn.redis;

import static org.junit.Assert.*;

import org.junit.Test;
import org.tcse.algorithms.learn.redis_with_springdata.RedisHelper;
import org.tcse.algorithms.learn.redis_with_springdata.RedisHelper.RedisMap;

import redis.clients.jedis.Jedis;

public class TestRedis {
	@Test
	public void test() {
		RedisMap redisMap = new RedisMap();
		redisMap.put("hahkey", "hahvalue");
		assertEquals("hahvalue", redisMap.get("hahkey"));
	}

	@Test
	public void testJedis() {
		String key = "testJedisKey", value = "testJedisValue";
		Jedis jedis = RedisHelper.getJedisSingleton();
		jedis.set(key, value);
		assertEquals(value, jedis.get(key));
	}
}
