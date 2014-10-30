package org.tcse.algorithms;

import static org.junit.Assert.*;

import org.junit.Test;

public class LeftRotateString {

	public void leftRotateString(StringBuilder s, int n) {
		if (s == null || s.length() == 0 || n < 0) {
			return;
		}
		int length = s.length();
		n = n % length;
		reverse(s, 0, length - 1);
		reverse(s, 0, length - n - 1);
		reverse(s, length - n, length - 1);
	}

	private void reverse(StringBuilder s, int begin, int end) {
		char temp = ' ';
		for (; begin < end; begin++, end--) {
			temp = s.charAt(begin);
			s.setCharAt(begin, s.charAt(end));
			s.setCharAt(end, temp);
		}
	}

	@Test
	public void test() {
		String content = "abcdefg", target = "cdefgab";
		StringBuilder stringBuilder = new StringBuilder(content);
		this.leftRotateString(stringBuilder, 2);
		assertEquals(target, stringBuilder.toString());
	}
	
	@Test
	public void testWithLongerN(){
		String content = "abcdefg", target = "cdefgab";
		StringBuilder stringBuilder = new StringBuilder(content);
		this.leftRotateString(stringBuilder, 9);
		assertEquals(target, stringBuilder.toString());
	}
}
