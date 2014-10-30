package org.tcse.algorithms;

import static org.junit.Assert.*;

import org.junit.Test;

public class BestBuyAndSellStock {
	public int maxProfit(int[] prices) {
		if (prices == null || prices.length < 2) {
			return 0;
		}
		int firstSectionMinmumIndex = 0, result = Integer.MIN_VALUE;
		int[] first = new int[prices.length], second = new int[prices.length];
		
		for (int i = 0; i < second.length; i++) {
			first[i] = 0;
			second[i] = 0;
		}
		
		for (int i = 0; i < prices.length; i++) {
			if (prices[firstSectionMinmumIndex] > prices[i]) {
				firstSectionMinmumIndex = i;
			}
			first[i] = Math.max(first[i], prices[i] - prices[firstSectionMinmumIndex]);
		}
		
		int maxMumIndex = prices.length - 1;
		for (int j = prices.length - 2; j >= 0; j--) {
			if (prices[maxMumIndex] < prices[j]) {
				maxMumIndex = j;
			}
			second[j] = Math.max(second[j + 1], prices[maxMumIndex] - prices[j]);
		}
		
		for (int i = 0; i < second.length; i++) {
			result = Math.max(result, first[i] + second[i]);
		}
		return result;
	}

//	private int calculateSecondLargest(int secondSectionBegin, int[] prices){
//		int result = Integer.MIN_VALUE;
//		int miniMumIndex = secondSectionBegin;
//		for (int j = secondSectionBegin; j < prices.length; j++) {
//			if (prices[miniMumIndex] > prices[j]) {
//				miniMumIndex = j;
//			}
//			result = Math.max(result, prices[j] - prices[miniMumIndex]);
//		}
//		return result;
//	}
	
	@Test
	public void testForAllIncrease() {
		int result = this.maxProfit(new int[] { 1, 2, 3, 4, 5, 6 });
		assertEquals(5, result);
	}

	@Test
	public void testNull() {
		assertEquals(0, this.maxProfit(null));
		assertEquals(0, this.maxProfit(new int[] { 1 }));
	}

	@Test
	public void testForAllDecrease() {
		int result = this.maxProfit(new int[] { 6, 5, 4, 3, 2, 1 });
		assertEquals(0, result);
	}

	@Test
	public void testForSecondSegments() {
		int result = this.maxProfit(new int[] { 1, 2, 3, 2, 3, 4 });
		assertEquals(4, result);
	}

	@Test
	public void testForForSegments() {
		assertEquals(4, this.maxProfit(new int[] { 1, 3, 1, 3, 1, 3, 1, 3 }));
	}

	@Test
	public void testForRandom() {
		assertEquals(9, this.maxProfit(new int[] { 1, 3, 5, 4, 3, 2, 6, 7 }));
	}
	@Test
	public void test4() {
		assertEquals(13, this.maxProfit(new int[] { 1, 2, 3, 5, 2, 7, 2, 9 }));
	}
	
	@Test
	public void tleTest() {
		int[] input = new int[10000];
		for (int i = 0; i < input.length; i++) {
			input[i] = 10000 - i;
		}
		assertEquals(0, this.maxProfit(input));
	}
}
