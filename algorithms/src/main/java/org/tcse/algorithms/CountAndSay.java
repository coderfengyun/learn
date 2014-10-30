package org.tcse.algorithms;

import static org.junit.Assert.*;

import org.junit.Test;

public class CountAndSay {
	public String countAndSay(int n) {
		if (n <= 0) {
			return "";
		}
		String input = "1", result = "";
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < input.length(); j++) {
				if (j == input.length() - 1) {
					result += "1" + input.charAt(j);
				} else {
					int count = getSussesiveCount(input.charAt(j), j, input);
					result += String.valueOf(count) + input.charAt(j);
					j = j + count - 1;
				}
			}
			input = result;
			result = "";
		}
		return input;
	}

	private int getSussesiveCount(char ch, int beginIndex, String target) {
		int result = 0;
		for (int i = beginIndex; i < target.length(); i++) {
			if (target.charAt(i) == ch) {
				result++;
			} else {
				break;
			}
		}
		return result;
	}

	@Test
	public void test() {
		assertEquals("1211", this.countAndSay(4));
	}
}
