package org.tcse.algorithms.learn.http;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Socket;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.params.HttpParams;
import org.junit.Test;
import org.tcse.algorithms.learn.http.speedlimit.SlowHC4SocketFactory;
import org.tcse.algorithms.learn.http.speedlimit.SlowSocket;

@SuppressWarnings("deprecation")
public class HttpClient4WithSpeedLimit {
	private static final int BYTES_PER_SECOND = 1200;
	private static final int DEFAULT_PORT = 80;
	private static final String HTTP_PROTOCAL = "http";
	private static final Scheme SPEED_LIMITED_HttpScheme = new Scheme(
			HTTP_PROTOCAL, DEFAULT_PORT, new SlowHC4SocketFactory(
					BYTES_PER_SECOND));
	private HttpClient httpclient;

	public HttpClient4WithSpeedLimit() {
		setupClient();
	}

	private void setupClient() {
		DnsResolver dnsResolver = new SystemDefaultDnsResolver();
		ClientConnectionManager clientConnectionManager = new PoolingClientConnectionManager(
				SchemeRegistryFactory.createDefault(), dnsResolver);
		HttpParams httpParams = null;
		this.httpclient = new DefaultHttpClient(clientConnectionManager,
				httpParams);
		SchemeRegistry schemeRegistry = this.httpclient.getConnectionManager()
				.getSchemeRegistry();
		// This means that when the httpclient need a "http" scheme, it will
		// find our speed limited one.

		schemeRegistry.register(SPEED_LIMITED_HttpScheme);
	}

	@Test
	public void testIsUseTheSpeedLimitedSocketFactory() {
		assertEquals(SlowHC4SocketFactory.class, this.httpclient
				.getConnectionManager().getSchemeRegistry().get(HTTP_PROTOCAL)
				.getSchemeSocketFactory().getClass());
	}

	@Test
	public void testIsUseTheSlowSocket() throws IOException {
		Socket createSocket = SPEED_LIMITED_HttpScheme.getSchemeSocketFactory()
				.createSocket(null);
		assertEquals(SlowSocket.class, createSocket.getClass());
	}

	@Test
	public void testExecuteRequestWithSlowSocket()
			throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet("http://www.baidu.com/");
		HttpResponse response = this.httpclient.execute(request,
				HttpClientContext.create());
		assertEquals(200, response.getStatusLine().getStatusCode());
	}

	@Test
	public void testGoogleTranslate() throws ClientProtocolException,
			IOException {
		HttpUriRequest request = new HttpGet(
				"http://translate.google.cn/#en/zh-CN/base");
		request.addHeader("User-Agent", "Mozilla/4.0");
		HttpResponse response = this.httpclient.execute(request);
		System.out.println(response.getStatusLine());
	}
}
