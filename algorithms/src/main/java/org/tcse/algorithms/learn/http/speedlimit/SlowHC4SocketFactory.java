package org.tcse.algorithms.learn.http.speedlimit;

import java.net.Socket;

import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.params.HttpParams;

@SuppressWarnings("deprecation")
public class SlowHC4SocketFactory extends PlainSocketFactory {
	private final int bytesPerSecond;

	public SlowHC4SocketFactory(final int bytesPerSecond) {
		super();
		this.bytesPerSecond = bytesPerSecond;
	}

	@Override
	public Socket createSocket(final HttpParams params) {
		return new SlowSocket(this.bytesPerSecond);
	}

	@Override
	public Socket createSocket() {
		return new SlowSocket(this.bytesPerSecond);
	}

}
