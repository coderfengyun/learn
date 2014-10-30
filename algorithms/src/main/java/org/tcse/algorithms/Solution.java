package org.tcse.algorithms;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class Solution {
	public List<List<String>> partition(String s) {
		if (s == null || s.length() == 0) {
			return null;
		}
		int size = s.length();
		boolean[][] isPalindrome = initIsPalindrome(s);
		List<List<List<String>>> result = new ArrayList<List<List<String>>>(
				size + 1);
		for (int i = 0; i < size + 1; i++) {
			result.add(new ArrayList<List<String>>());
		}
		for (int i = size - 1; i >= 0; i--) {
			for (int j = i; j < size; j++) {
				if (isPalindrome[i][j - i]) {
					result.get(i).addAll(
							join(s.substring(i, j + 1), result.get(j + 1)));
				}
			}
		}
		return result.get(0);
	}

	private List<List<String>> join(String palindromStart,
			List<List<String>> rightPart) {
		List<List<String>> result = new ArrayList<List<String>>();
		if (rightPart.isEmpty()) {
			result.add(Arrays.asList(palindromStart));
		}
		for (List<String> rightItem : rightPart) {
			List<String> resultItem = new ArrayList<String>();
			resultItem.add(palindromStart);
			resultItem.addAll(rightItem);
			result.add(resultItem);
		}
		return result;
	}

	private boolean[][] initIsPalindrome(String s) {
		boolean[][] isPalindrome = new boolean[s.length()][s.length()];
		for (int step = 0; step < s.length(); step++) {
			for (int i = 0; i < s.length(); i++) {
				if (i + step >= s.length()) {
					continue;
				}
				if (step == 0) {
					isPalindrome[i][step] = true;
				} else if (step >= 2) {
					isPalindrome[i][step] = isPalindrome[i + 1][step - 2]
							&& s.charAt(i) == s.charAt(i + step);
				} else {
					isPalindrome[i][step] = s.charAt(i) == s.charAt(i + step);
				}
			}
		}
		return isPalindrome;
	}

	@Test
	public void test() {
		List<List<String>> result = this.partition("aabb");
		for (List<String> item : result) {
			for (String content : item) {
				System.out.print(" " + content);
			}
			System.out.println("____________");
		}
		assertEquals(4, result.size());
	}

}
