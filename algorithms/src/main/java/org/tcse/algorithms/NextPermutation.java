package org.tcse.algorithms;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class NextPermutation {

	public void nextPermutation(int[] num) {
		if (num == null || num.length == 0) {
			return;
		}
		for (int i = num.length - 1; i >= 1; i--) {
			if (num[i] > num[i - 1]) {
				int lastLarger = findLastLargerThan(i - 1, num);
				swap(lastLarger, i - 1, num);
				reverse(num, i, num.length - 1);
				return;
			}
		}
		reverse(num, 0, num.length - 1);
	}

	private int findLastLargerThan(int index, int[] num) {
		for (int i = num.length - 1; i >= 0; i--) {
			if (num[i] > num[index]) {
				return i;
			}
		}
		return -1;
	}

	private void reverse(int[] num, int begin, int end) {
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
	public void test() {
		int[] num = new int[] { 1, 6, 5, 4, 3, 2 };
		examine(num);
	}

	@Test
	public void test1() {
		int[] num = new int[] { 1, 3, 2, 5, 4 };
		examine(num);
		assertArrayEquals(new int[] { 1, 3, 4, 2, 5 }, num);
	}

	private void examine(int[] num) {
		this.nextPermutation(num);
		for (int i = 0; i < num.length; i++) {
			System.out.println(num[i]);
		}
	}

	@Test
	public void test2() {
		int[] num = new int[] { 2, 3, 1 };
		examine(num);
		assertArrayEquals(new int[] { 3, 1, 2 }, num);
	}

	@Test
	public void test3() {
		int[] num = new int[] { 3, 2, 1 };
		examine(num);
		assertArrayEquals(new int[] { 1, 2, 3 }, num);
		
		String[] strings = new String[1];
		strings[1] = " ";
		Arrays.copyOf(strings, strings.length);
	}
}
