package org.tcse.algorithms.learn.http.speedlimit;

public class SpeedLimitPauser {
	private final int CPS; // Characters per second to emulate

	private static final int MS_PER_SEC = 1000;
	private static final int NS_PER_SEC = 1000000000;
	private static final int NS_PER_MS = NS_PER_SEC / MS_PER_SEC;

	/**
	 * Create a pauser with the appropriate speed settings.
	 *
	 * @param cps
	 *            CPS to emulate
	 */
	public SpeedLimitPauser(int cps) {
		if (cps <= 0) {
			throw new IllegalArgumentException("Speed (cps) <= 0");
		}
		CPS = cps;
	}

	/**
	 * Pause for an appropriate time according to the number of bytes being
	 * transferred.
	 *
	 * @param bytes
	 *            number of bytes being transferred
	 */
	public void pause(int bytes) {
		long sleepMS = (bytes * MS_PER_SEC) / CPS;
		int sleepNS = ((bytes * MS_PER_SEC) / CPS) % NS_PER_MS;
		try {
			if (sleepMS > 0 || sleepNS > 0) {
				Thread.sleep(sleepMS, sleepNS);
			}
		} catch (InterruptedException ignored) {
		}
	}
}
