package org.tcse.algorithms.learn.http.speedlimit;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SlowInputStream extends FilterInputStream {
	private final SpeedLimitPauser pauser;

	protected SlowInputStream(InputStream in, int bytesPerSecond) {
		super(in);
		this.pauser = new SpeedLimitPauser(bytesPerSecond);
	}

	@Override
	public int read() throws IOException {
		this.pauser.pause(1);
		return super.read();
	}

	@Override
	public int read(byte[] b) throws IOException {
		this.pauser.pause(b == null ? 0 : b.length);
		return super.read(b);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		this.pauser.pause(len);
		return super.read(b, off, len);
	}

}
