package org.tcse.algorithms;

import static org.junit.Assert.*;

import org.junit.Test;

public class SpecialDigit {

	public int countOfWithout258(int begin, int end) {
		if (begin > end) {
			return 0;
		}
		if (begin < 0 && end < 0) {
			return countOfWithout258(-end, -begin);
		} else if (begin < 0) {
			return countWithout258(-begin) + countWithout258(end);
		}
		return countWithout258(end) - countWithout258(begin);
	}

	private int countWithout258(int n) {
		String content = String.valueOf(n);
		int result = 0;
		int length = content.length();
		// for low length - 1 digits
		if (length == 1) {
			return doForOneBit(n);
		}
		result = (int) Math.pow(7, length - 1);
		char highest = content.charAt(0);
		int highestVal = length == 1 ? highest - '0' + 1 : highest - '0';
		result *= doForOneBit(highestVal) - 1;
		if (length == 1) {
			return result;
		} else {
			return result
					+ countWithout258(Integer.valueOf(content.substring(1)));
		}
	}

	private int doForOneBit(int n) {
		if (n <= 2) {
			return n;
		} else if (n <= 5) {
			return n - 1;
		} else if (n <= 8) {
			return n - 2;
		} else {
			return n - 3;
		}
	}

	@Test
	public void test0() {
		assertEquals(2, this.countOfWithout258(0, 2));
	}

	@Test
	public void test() {
		assertEquals(5, this.countOfWithout258(0, 7));
	}

	@Test
	public void test1() {
		assertEquals(7, this.countOfWithout258(0, 10));
	}

	
}
