package org.tcse.algorithms;

public class ConstructPalindromeByAddingChars {
	public int leastCharsToAdd(String input) {
		if (input == null || input.length() == 0) {
			return 0;
		}
		int length = input.length();
		// i represents start, j represents length
		boolean[][] isPalindrome = new boolean[length][length + 1];
		// i represents start, j represents end
		int[][] dp = new int[length][length];
		for (int i = 0; i < length; i++) {
			isPalindrome[i][0] = true;
			isPalindrome[i][1] = true;
		}
		for (int i = 0; i < length; i++) {
			dp[i][i] = 0;
		}

		for (int i = 2; i <= length; i++) {
			for (int j = 0; j < length; j++) {
				int result = Integer.MAX_VALUE;
				char chStart = input.charAt(j), chEnd = input.charAt(j + i - 1);
				isPalindrome[j][i] = chStart == chEnd
						&& isPalindrome[j + 1][i - 1];
				result = Math.min(result, dp[j][j + i - 1] + 1);
				result = Math.min(result, dp[j + 1][j + i] + 1);
				result = Math.min(result, dp[j + 1][j + i - 1]
						+ (chStart == chEnd ? 0 : 1));
			}
		}
		return dp[0][length - 1];
	}
}
