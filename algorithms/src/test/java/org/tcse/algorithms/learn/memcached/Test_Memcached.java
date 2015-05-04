package org.tcse.algorithms.learn.memcached;

import static org.junit.Assert.*;

import org.junit.Test;

import com.whalin.MemCached.MemCachedClient;

public class Test_Memcached {
	/**
	 * This test need you to start memcached
	 */
	@Test
	public void test() {
		MemCachedClient client = MemCachedHelper.getCache();
		User user = new User("chen", "123");
		assertTrue(client.add("1", user));
		assertEquals(user, client.get("1"));
	}
}
