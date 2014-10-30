package org.tcse.algorithms;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

public class MedianOfTwoSortedArray {
	private static int ERROR_CODE = Integer.MIN_VALUE;

	public double findMedianSortedArrays(int A[], int B[]) {
		if (A == null || B == null || (A.length == 0 && B.length == 0)) {
			return ERROR_CODE;
		}
		int totalLength = A.length + B.length, k1 = totalLength / 2 + 1, k2 = (totalLength - 1) / 2 + 1;
		if (k1 == k2) {
			return find(A, B, k1);
		}
		return ((double) find(A, B, k1) + (double) find(A, B, k2)) / 2;
	}

	private int find(int A[], int B[], int targetPosition) {
		if (A.length == 0) {
			return B[targetPosition - 1];
		} else if (B.length == 0) {
			return A[targetPosition - 1];
		}
		int endA = A.length - 1, endB = B.length - 1, midA = endA / 2;
		int pivotValue = A[midA];
		int leftLengthA = midA, leftLengthB = getLeftLength(B, pivotValue), leftLength = leftLengthA
				+ leftLengthB, equalLength = 0;
		int rightLengthA = getRightLength(A, pivotValue), rightLengthB = getRightLength(
				B, pivotValue);
		int equalLengthA = A.length - rightLengthA - leftLengthA, equalLengthB = B.length
				- rightLengthB - leftLengthB;
		equalLength = equalLengthA + equalLengthB;
		if (targetPosition <= leftLength) {
			return find(Arrays.copyOfRange(A, 0, leftLengthA),
					Arrays.copyOfRange(B, 0, leftLengthB), targetPosition);
		} else if (targetPosition > leftLength
				&& targetPosition <= leftLength + equalLength) {
			return pivotValue;
		} else {
			return find(
					Arrays.copyOfRange(A, equalLengthA + leftLengthA,
							Math.max(equalLengthA + leftLengthA, endA + 1)),
					Arrays.copyOfRange(B, equalLengthB + leftLengthB,
							Math.max(equalLengthB + leftLengthB, endB + 1)),
					targetPosition - equalLength - leftLength);
		}
	}

	private int getLeftLength(int[] array, int pivotValue) {
		if (pivotValue >= array[array.length - 1]) {
			return array.length;
		} else if (pivotValue < array[0]) {
			return 0;
		}
		int begin = 0, end = array.length - 1, mid = (begin + end) / 2;
		while (begin < end) {
			if (array[mid] < pivotValue) {
				begin = mid + 1;
			} else {
				end = mid - 1;
			}
			mid = (begin + end) / 2;
		}
		if (array[mid] > pivotValue) {
			return mid;
		} else if (array[mid] < pivotValue) {
			return mid + 1;
		} else {
			return mid;
		}
	}

	private int getRightLength(int[] array, int pivotValue) {
		if (pivotValue < array[0]) {
			return array.length;
		} else if (pivotValue >= array[array.length - 1]) {
			return 0;
		}
		int begin = 0, end = array.length - 1, mid = (begin + end) / 2;
		while (begin < end) {
			if (array[mid] <= pivotValue) {
				begin = mid + 1;
			} else {
				end = mid - 1;
			}
			mid = (begin + end) / 2;
		}
		if(array[mid] <= pivotValue){
			return array.length - mid - 1;
		}else{
			return array.length - mid;
		}
	}

	@Test
	public void test() {
		System.out.println(this.findMedianSortedArrays(new int[] { 2 },
				new int[] { 1, 3, 4 }));
	}
	
	@Test
	public void test1(){
		ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>(20);
		assertNotNull(concurrentHashMap);
	}
}
