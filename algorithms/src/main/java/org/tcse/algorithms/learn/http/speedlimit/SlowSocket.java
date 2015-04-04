package org.tcse.algorithms.learn.http.speedlimit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SlowSocket extends Socket {
	private final int bytesPerSecond;

	public SlowSocket(int cps) {
		super();
		this.bytesPerSecond = cps;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new SlowInputStream(super.getInputStream(), this.bytesPerSecond);
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return new SlowOutputStream(super.getOutputStream(),
				this.bytesPerSecond);
	}

}
