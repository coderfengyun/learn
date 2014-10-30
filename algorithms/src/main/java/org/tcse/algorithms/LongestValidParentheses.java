package org.tcse.algorithms;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

public class LongestValidParentheses {
	public int longestValidParentheses(String s) {
        int result = 0;
        if(s == null || s.length() == 0){
            return result;
        }
        int[] validLengthFromIndex = new int[s.length() + 1];
        LinkedList<Character> stack = new LinkedList<Character>();
        LinkedList<Integer> indexStack = new LinkedList<Integer>();
        validLengthFromIndex[s.length()] = 0;
        validLengthFromIndex[s.length() - 1] = 0;
        if(s.charAt(s.length() - 1) == ')'){
            stack.push(')');
            indexStack.push(s.length() - 1);
        }
        int current = 0;
        for(int i = s.length() - 2; i >= 0; i--){
            if(s.charAt(i) == ')'){
                stack.push(')');
                indexStack.push(i);
                current = 0;
            }else if(s.charAt(i) == '('){
                if(stack.size() > 0 && stack.peek() == ')'){
                    //valid
                    stack.pop();
                    int index = indexStack.pop();
                    current = 2 + validLengthFromIndex[i + 1];
                    current += validLengthFromIndex[index + 1];
                }else{
                    //not valid
                	current = 0;
                    stack.clear();
                }
            }
            validLengthFromIndex[i] = current;
            result = Math.max(result, current);
        }
        return result;
    }
	
	@Test
	public void test(){
		assertEquals(6, this.longestValidParentheses("()(())"));
	}
	
	@Test
	public void test1(){
		assertEquals(2, this.longestValidParentheses("())()"));
	}
	
	@Test
	public void test2(){
		assertEquals(2, this.longestValidParentheses("()(()"));
	}
	
	@Test
	public void test3(){
		assertEquals(8, this.longestValidParentheses("((()))())"));
	}
}
