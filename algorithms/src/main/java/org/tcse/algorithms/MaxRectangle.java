package org.tcse.algorithms;

import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;

public class MaxRectangle {
	private int[] heightWithSentinel;
	private Stack<Integer> indexStack;

	public int largestRectangleArea(int[] height) {
		if (height == null || height.length == 0) {
			return 0;
		}
		this.heightWithSentinel = buildWithSentinel(height);
		this.indexStack = new Stack<Integer>();
		int length = heightWithSentinel.length, result = 0;
		for (int i = 0; i < length - 1; i++) {
			this.indexStack.push(i);
			if (heightWithSentinel[i] > heightWithSentinel[i + 1]) {
				result = Math.max(calculateAreaBefore(i + 1), result);
			}
		}
		return result;
	}

	private int calculateAreaBefore(int currentMinIndex) {
		int result = 0;
		while (!this.indexStack.empty()) {
			int topIndex = this.indexStack.peek();
			if (this.heightWithSentinel[topIndex] < this.heightWithSentinel[currentMinIndex]) {
				break;
			} else {
				this.indexStack.pop();
				result = Math
						.max(result,
								this.heightWithSentinel[topIndex]
										* ((currentMinIndex - topIndex) + (this.indexStack
												.empty() ? topIndex : topIndex
												- this.indexStack.peek() - 1)));
			}
		}
		return result;
	}

	private int[] buildWithSentinel(int[] height) {
		int[] result = new int[height.length + 1];
		for (int i = 0; i < height.length; i++) {
			result[i] = height[i];
		}
		result[height.length] = 0;
		return result;
	}

	@Test
	public void test() {
		assertEquals(10, this.largestRectangleArea(new int[] { 2, 1, 5, 6, 2, 3 }));
	}
}
