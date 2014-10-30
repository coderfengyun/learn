package org.tcse.algorithms;

import org.junit.Test;

public class QuickSort {

	public void quickSort(int[] A) {
		if (A == null || A.length == 0) {
			return;
		}
		quickSort0(A, 0, A.length - 1);
	}
	
	private void quickSort0(int[] A, int begin, int end){
		if (begin >= end) {
			return;
		}
		int pivot = A[begin], pivotPos = partition(A, begin, end, pivot);
		quickSort0(A, begin, pivot - 1);
		quickSort0(A, pivotPos + 1, end);
	}

	private int partition(int[] A, int begin, int end, int pivot) {
		int pivotPos = begin, iterator = begin + 1;
		A[begin] = Integer.MIN_VALUE;
		for (; iterator <= end; iterator++) {
			if (A[iterator] >= pivot) {
				continue;
			}
			if (A[pivotPos] >= pivot) {
				swap(A, pivotPos, iterator);
				pivotPos++;
			}
		}
		return pivotPos;
	}

	private void swap(int[] a, int pivotPos, int iterator) {
		int temp = a[pivotPos];
		a[pivotPos] = a[iterator];
		a[iterator] = temp;
	}

	@Test
	public void test() {
		int[] a = new int[] { 1, 10, 90, 87, 65, 290, 864, 265, 9 };
		verifySorted(a);
	}

	private void verifySorted(int[] a) {
		for (int i = 0; i < a.length - 1; i++) {
			assertTrue(a[i] <= a[i + 1]);
		}
	}

	private void assertTrue(boolean b) {
		// TODO Auto-generated method stub

	}
}
