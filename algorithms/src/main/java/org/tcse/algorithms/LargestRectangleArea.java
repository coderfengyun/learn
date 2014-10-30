package org.tcse.algorithms;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

public class LargestRectangleArea {
	public int largestRectangleArea(int[] height) {
		if (height == null || height.length == 0) {
			return 0;
		}
		if (height.length == 1) {
			return height[0];
		}
		int result = height[0];
		Stack<Integer> stack = new Stack<Integer>();
		for (int i = 0; i < height.length; i++) {
			if (stack.isEmpty() || height[i] >= height[stack.peek()]) {
				stack.push(i);
			} else {
				while (!stack.isEmpty() && height[stack.peek()] >= height[i]) {
					result = Math.max(result, dealWithEqual(stack, height, i - 1));
				}
				result = Math.max(height[i]
						* (stack.isEmpty() ? i + 1 : i - stack.peek()), result);
				stack.push(i);
			}
		}
		if (!stack.isEmpty()) {
		    int originTop = stack.peek();
			while (!stack.isEmpty()) {
				result = Math.max(result, dealWithEqual(stack, height, originTop));
			}
		}
		
		return result;
	}

	private int dealWithEqual(Stack<Integer> stack, int[] height, int originTop) {
		int top = stack.peek(), result = height[top];
		while (!stack.isEmpty() && height[stack.peek()] == height[top]) {
			stack.pop();
		}
		int tmpResult = height[top]
				* (stack.isEmpty() ? originTop + 1 : originTop - stack.peek());
		result = Math.max(tmpResult, result);
		return result;
	}

	@Test
	public void test() {
		assertEquals(4, this.largestRectangleArea(new int[] { 2, 4 }));
	}

	@Test
	public void test1() {
		assertEquals(2, this.largestRectangleArea(new int[] { 2, 0, 2 }));
	}
	
	@Test
	public void test2(){
		assertEquals(2, this.largestRectangleArea(new int[]{1, 1} ));
	}
	
	@Test
	public void test3(){
		assertEquals(3, this.largestRectangleArea(new int[]{2,1,2}));
	}
}
