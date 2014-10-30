package org.tcse.algorithms;

import static org.junit.Assert.*;

import org.junit.Test;

public class NumberOfOne {

	public int countOf1(int n) {
		if (n <= 0) {
			return 0;
		}
		String nContent = String.valueOf(n);
		int result = 0, length = nContent.length(), higherDigitValue = 0;
		for (int i = 0; i < length; i++) {
			int currentDigit = nContent.charAt(i) - '0', currentCount = 0, base = (int) Math
					.pow(10, length - 1 - i);
			if (currentDigit == 0) {
				currentCount = higherDigitValue * base;
			} else if (currentDigit == 1) {
				if (i == length - 1) {
					currentCount = higherDigitValue * base + 1;
				} else {
					currentCount = higherDigitValue * base
							+ (Integer.valueOf(nContent.substring(i + 1))) + 1;
				}
			} else {
				if (i == length - 1) {
					currentCount = higherDigitValue * base + 1;
				} else {
					currentCount = (higherDigitValue + 1) * base;
				}
			}
			result += currentCount;
			higherDigitValue = higherDigitValue * 10 + currentDigit;
		}
		return result;
	}

	@Test
	public void test() {
		testCore(2);
	}

	@Test
	public void test1() {
		testCore(13);
	}

	@Test
	public void test2() {
		testCore(90);
	}

	@Test
	public void test3() {
		testCore(20);
	}

	@Test
	public void test4() {
		testCore(93);
	}

	@Test
	public void test5() {
		testCore(124);
	}

	private int getRealCount(int n) {
		int result = 0;
		for (int i = 0; i <= n; i++) {
			String content = i + "";
			for (int j = 0; j < content.length(); j++) {
				if (content.charAt(j) == '1') {
					result++;
				}
			}
		}
		return result;
	}
	
	@Test
	public void test6(){
		int val = 100976;
		testCore(val);
	}
	
	@Test
	public void test7(){
		int val = 1998002;
		testCore(val);
	}

	private void testCore(int val) {
		assertEquals(getRealCount(val), this.countOf1(val));
	}
	
}
