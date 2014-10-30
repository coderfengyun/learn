package org.tcse.algorithms;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidNumber {
	public boolean isNumber(String s) {
		if (s == null) {
			return false;
		}
		s = s.trim();
		if (s.length() == 0) {
			return false;
		}
		boolean alreadyHaveDecimalPoint = false, alreadyHaveExponent = false, hasNumberAfterDecimalPoint = false, hasNumberBeforeDecimalPoint = false, hasNumberAfterExponent = false;
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (i == 0) {
				if (isSignBit(ch) || isNumberic(ch)) {
				} else if (isDecimalPoint(ch)) {
					alreadyHaveDecimalPoint = true;
				} else {
					return false;
				}
			} else {
				if (isNumberic(ch)) {
					if (isDecimalPoint(s.charAt(i - 1))) {
						hasNumberAfterDecimalPoint = true;
					} else if (isExponent(s.charAt(i - 1))) {
						hasNumberAfterExponent = true;
					}
				} else if (isDecimalPoint(ch) && !alreadyHaveDecimalPoint
						&& !alreadyHaveExponent) {
					alreadyHaveDecimalPoint = true;
					if (isNumberic(s.charAt(i - 1))) {
						hasNumberBeforeDecimalPoint = true;
					}
				} else if (isExponent(ch) && !alreadyHaveExponent) {
					if (isNumberic(s.charAt(i - 1))) {

					} else if (isDecimalPoint(s.charAt(i - 1))
							&& (hasNumberBeforeDecimalPoint || hasNumberAfterDecimalPoint)) {
					} else {
						return false;
					}
					alreadyHaveExponent = true;
				} else if (isSignBit(ch) && isExponent(s.charAt(i - 1)) && (i != s.length() - 1)) {
					if (isNumberic(s.charAt(i + 1))) {
						hasNumberAfterExponent = true;
					}
				} else {
					return false;
				}
			}
		}
		boolean result = true;
		if (alreadyHaveDecimalPoint) {
			result = result
					&& (hasNumberAfterDecimalPoint || hasNumberBeforeDecimalPoint);
		}
		if (alreadyHaveExponent) {
			result = result && hasNumberAfterExponent;
		}
		return result;
	}

	private boolean isSignBit(char ch) {
		return ch == '-' || ch == '+';
	}

	private boolean isNumberic(char ch) {
		return (ch >= '0' && ch <= '9');
	}

	private boolean isDecimalPoint(char ch) {
		return ch == '.';
	}

	private boolean isExponent(char ch) {
		return ch == 'e';
	}

	@Test
	public void test() {
		System.out.println(.12);
		System.out.println(1e10);
		System.out.println(012);
		System.out.println(3.);
		// System.out.println(6e6.5);
	}

	@Test
	public void test1() {
		assertTrue(this.isNumber("0"));
	}

	@Test
	public void test2() {
		assertFalse(this.isNumber("1e"));
	}

	@Test
	public void test3() {
		assertFalse(this.isNumber(" "));
	}

	@Test
	public void test4() {
		assertTrue(this.isNumber("3."));
	}

	@Test
	public void test5() {
		assertTrue(this.isNumber("46.e4"));
	}

	@Test
	public void test6() {
		assertFalse(this.isNumber("6e5.6"));
	}

	@Test
	public void test7() {
		assertTrue(this.isNumber(" 005047e+6"));
	}
}
