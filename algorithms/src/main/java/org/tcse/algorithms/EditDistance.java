package org.tcse.algorithms;

import static org.junit.Assert.*;

import org.junit.Test;

public class EditDistance {
	public int minDistance(String word1, String word2) {
        if(word1 == null || word1.length() == 0){
            return word2.length();
        }
        if(word2 == null || word2.length() == 0){
            return word1.length();
        }
        int[][] dp = init(word1.length(), word2.length());
        int min = Integer.MAX_VALUE;
        for(int i = word1.length() - 1; i >= 0; i--){
            for(int j = word2.length() - 1; j >= 0; j--){
                min = Math.min(dp[i + 1][j] + 1, dp[i][j + 1] + 1);
                if(word1.charAt(i) == word2.charAt(j)){
                    min = Math.min(dp[i + 1][j + 1], min);
                }else{
                    min = Math.min(dp[i + 1][j + 1] + 1, min);
                }
                dp[i][j] = min;
            }
        }
        return dp[0][0];
    }
    
    private int[][] init(int len1, int len2){
       int[][] dp = new int[len1 + 1][len2 + 1];
        for(int i = 0; i < len1 + 1; i++){
            for(int j = 0; j < len2 + 1; j++){
                if(i == len1 && j == len2){
                    dp[i][j] = 0;
                }else{
                    dp[i][j] = Integer.MAX_VALUE - 1;
                }
            }
        }
        return dp;
    }
    
    @Test
    public void test(){
    	assertEquals(0, this.minDistance("a", "a"));
    }
    
    @Test
    public void test1(){
    	assertEquals(1, this.minDistance("ab", "a"));
    }
}
