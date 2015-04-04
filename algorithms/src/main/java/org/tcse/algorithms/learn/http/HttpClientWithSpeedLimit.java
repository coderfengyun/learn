package org.tcse.algorithms.learn.http;

import org.apache.http.conn.scheme.Scheme;
import org.tcse.algorithms.learn.http.speedlimit.SlowHC4SocketFactory;

@SuppressWarnings("deprecation")
public class HttpClientWithSpeedLimit {
	private static final int DEFAULT_PORT = 80;
	private static final String HTTP_PROTOCAL = "http";
	Scheme scheme = new Scheme(HTTP_PROTOCAL, DEFAULT_PORT,
			new SlowHC4SocketFactory(12));
}
