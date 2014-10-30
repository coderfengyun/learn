package org.tcse.algorithms;

import static org.junit.Assert.*;

import org.junit.Test;

public class UniquePathes {
	public int uniquePaths(int m, int n) {
		if (m <= 0 || n <= 0) {
			return -1;
		}
		int[][] dp = new int[m + 1][n + 1];
		for (int i = 0; i < m + 1; i++) {
			for (int j = 0; j < n + 1; j++) {
				if (i == m || j == n) {
					dp[i][j] = 0;
				} else {
					dp[i][j] = -1;
				}
			}
		}
		dp[m - 1][n - 1] = 1;
		for (int i = m - 1; i >= 0; i--) {
			for (int j = n - 1; j >= 0; j--) {
				dp[i][j] = dp[i + 1][j] + dp[i][j + 1];
			}
		}
		return dp[0][0];
	}
	
	@Test
	public void test(){
		assertEquals(1, this.uniquePaths(1, 2));
	}
}
