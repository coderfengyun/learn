package org.tcse.algorithms;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

public class LongestParentheses {
	public int longestValidParentheses(String s) {
        int result = 0;
        if(s == null || s.length() == 0){
            return result;
        }
        Stack<Integer> stack = new Stack<Integer>();
        int currentValidLength = 0;
        for(int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);
			if(ch == '('){
                stack.push(i);
            }else if(ch == ')'){
                if(stack.empty()){
                    result = Math.max(result, currentValidLength);
                    currentValidLength = 0;
                }else{
                    stack.pop();
                    currentValidLength += 2;
                }
            }
        }
        
        return Math.max(result, currentValidLength);
    }
	
	@Test
	public void test(){
		assertEquals(6, this.longestValidParentheses("))()()()"));
	}
	
	@Test
	public void test2(){
		assertEquals(10, this.longestValidParentheses("(((())))()"));
	}
	
	@Test
	public void test3(){
		assertEquals(2, this.longestValidParentheses("()(()"));
	}
}
