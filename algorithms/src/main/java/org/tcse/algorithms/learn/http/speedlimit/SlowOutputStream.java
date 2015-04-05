package org.tcse.algorithms.learn.http.speedlimit;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SlowOutputStream extends FilterOutputStream {
	private final SpeedLimitPauser pauser;

	public SlowOutputStream(OutputStream outputStream, int bytesPerSecond) {
		super(outputStream);
		this.pauser = new SpeedLimitPauser(bytesPerSecond);
	}

	@Override
	public void write(int b) throws IOException {
		this.pauser.pause(1);
		super.write(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		this.pauser.pause(b == null ? 0 : b.length);
		super.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		this.pauser.pause(len);
		super.write(b, off, len);
	}

}
