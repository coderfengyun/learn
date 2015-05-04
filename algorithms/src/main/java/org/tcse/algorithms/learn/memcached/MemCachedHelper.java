package org.tcse.algorithms.learn.memcached;

import java.util.concurrent.TimeUnit;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

public class MemCachedHelper {
	private static final String POOL_NAME = "default";
	private static MemCachedClient cachedClient = new MemCachedClient(POOL_NAME);
	static {
		SockIOPool pool = SockIOPool.getInstance(POOL_NAME);

		// Setting of the servers' configuration
		String[] servers = new String[] { "127.0.0.1:11211" };
		Integer[] weights = new Integer[] { 3 };
		pool.setServers(servers);
		pool.setWeights(weights);
		// Settings for the connection pool
		pool.setInitConn(10);
		pool.setMaxConn(1000);
		pool.setMinConn(10);
		pool.setMaxIdle(TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS));
		// Main Thread sleep time between two runs,This time is in millisecond
		pool.setMaintSleep(60);
		// Setting the TCP Configuration,
		pool.setNagle(true);// Open the Nagle Algorithm when send request
		pool.setSocketTO(60); // Socket read Time out is 60 ms
		pool.setSocketConnectTO(60); // Socket connection time out is 60 ms
		// init the pool
		pool.initialize();
	}

	public static MemCachedClient getCache() {
		return cachedClient;
	}
}
