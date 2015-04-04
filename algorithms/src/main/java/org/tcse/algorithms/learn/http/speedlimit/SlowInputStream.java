package org.tcse.algorithms.learn.http.speedlimit;

import java.io.FilterInputStream;
import java.io.InputStream;

public class SlowInputStream extends FilterInputStream {
	private final int bytesPerSecond;

	protected SlowInputStream(InputStream in, int bytesPerSecond) {
		super(in);
		this.bytesPerSecond = bytesPerSecond;
	}

}
