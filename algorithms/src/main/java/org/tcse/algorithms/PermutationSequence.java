package org.tcse.algorithms;

import org.junit.Test;

public class PermutationSequence {

	public String getPermutation(int n, int k) {
		String result = "";
		if (n <= 0 || k <= 0) {
			return result;
		}
		int[] first = new int[n];
		for (int i = 0; i < first.length; i++) {
			first[i] = i + 1;
		}
		for (int i = 1; i < k; i++) {
			nextPermutation(first);
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < first.length; i++) {
			builder.append(first[i]);
		}
		return builder.toString();
	}
	
	public void nextPermutation(int[] num) {
		if (num == null || num.length == 0) {
			return;
		}
		for (int i = num.length - 1; i > 1; i--) {
			if (num[i] > num[i - 1]) {
				swap(i, i - 1, num);
				return;
			}
		}
		if (num[num.length - 1] > num[0]) {
			int lastValue = num[num.length - 1];
			for (int i = num.length - 1; i > 0; i--) {
				num[i] = num[i - 1];
			}
			num[0] = lastValue;
			return;
		}
		reverse(num);
	}

	private void reverse(int[] num) {
		int begin = 0, end = num.length - 1;
		while (begin <= end) {
			swap(begin, end, num);
			begin++;
			end--;
		}
	}

	private void swap(int i, int j, int[] num) {
		int tmp = num[i];
		num[i] = num[j];
		num[j] = tmp;
	}
	
	@Test
	public void test(){
		System.out.println(this.getPermutation(3, 2));
	}
}
