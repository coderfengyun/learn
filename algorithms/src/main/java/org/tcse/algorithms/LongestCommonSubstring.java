package org.tcse.algorithms;

import static org.junit.Assert.*;

import org.junit.Test;

public class LongestCommonSubstring {

	public int findLongestSubstring(String query, String text) {
		if (query == null || text == null || query.length() == 0
				|| text.length() == 0) {
			return 0;
		}
		int queryLength = query.length(), textLength = text.length();
		int[] dpResult = new int[queryLength + 1];
		int result = 0;
		for (int i = 0; i < textLength; i++) {
			for (int j = queryLength - 1; j >= 0; j--) {
				if (query.charAt(j) == text.charAt(i)) {
					dpResult[j + 1] = dpResult[j] + 1;
				} else {
					dpResult[j + 1] = 0;
				}
				result = Math.max(result, dpResult[j + 1]);
			}
		}
		return result;
	}

	@Test
	public void test() {
		assertEquals(3, this.findLongestSubstring("acbac", "acaccbabb"));
	}
}
