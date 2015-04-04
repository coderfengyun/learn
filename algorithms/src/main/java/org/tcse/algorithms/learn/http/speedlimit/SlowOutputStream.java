package org.tcse.algorithms.learn.http.speedlimit;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SlowOutputStream extends FilterOutputStream {
	private final int bytesPerSecond;
	private final SpeedLimitPauser pauser;

	public SlowOutputStream(OutputStream outputStream, int bytesPerSecond) {
		super(outputStream);
		this.pauser = new SpeedLimitPauser(bytesPerSecond);
		this.bytesPerSecond = bytesPerSecond;
	}

	@Override
	public void write(int b) throws IOException {
		super.write(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		super.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		super.write(b, off, len);
	}

}
