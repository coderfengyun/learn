package org.tcse.algorithms;

import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class HeapImplement {
	private MinHeap heap;

	@Test
	public void testForInit() {
		justifyWholeHeap(heap);
	}

	private void justifyWholeHeap(MinHeap heap) {
		for (int i = 0; i < heap.length; i++) {
			int leftIndex = heap.getLeftIndex(i), rightIndex = heap
					.getRightIndex(i);
			if (leftIndex > -1) {
				assertTrue(heap.array[i] <= heap.array[leftIndex]);
			}
			if (rightIndex > -1) {
				assertTrue(heap.array[i] <= heap.array[rightIndex]);
			}
		}
	}

	@Test
	public void testForOffer() {
		heap.offer(0);
		assertEquals(0, heap.peek());
		justifyWholeHeap(heap);
	}

	@Before
	public void before() {
		int[] input = new int[] { 9, 8, 7, 6, 5, 4, 3, 2, 1 };
		this.heap = new MinHeap(input);
	}

	@Test
	public void testForPop() {
		assertEquals(1, heap.pop());
		justifyWholeHeap(heap);
		assertEquals(8, heap.length);
	}

	public static class MinHeap {
		private int[] array;
		private int length;

		public MinHeap() {

		}

		public MinHeap(int[] array) {
			this.length = array.length;
			this.array = Arrays.copyOf(array, array.length);
			heapify();
		}

		private void heapify() {
			for (int i = this.length >>> 1; i >= 0; i--) {
				siftdown(i);
			}
		}

		private void siftUp(int index) {
			int rootIndex = getRoot(index);
			if (!isLegalIndex(rootIndex)) {
				return;
			}
			if (this.array[rootIndex] > this.array[index]) {
				swap(index, rootIndex);
				siftUp(rootIndex);
			}
		}

		private void siftdown(int index) {
			int half = this.length >>> 1;
			if (index > half) {
				return;
			}
			int leftIndex = getLeftIndex(index), rightIndex = getRightIndex(index), minChild = leftIndex;
			if (leftIndex == -1) {
				return;
			}
			if (rightIndex != -1) {
				minChild = this.array[leftIndex] <= this.array[rightIndex] ? leftIndex
						: rightIndex;
			}
			if (this.array[index] > this.array[minChild]) {
				swap(minChild, index);
				siftdown(minChild);
			}
		}

		public boolean offer(int newValue) {
			if (this.length == this.array.length) {
				enlargerTheHeap();
			}
			int destIndex = this.length;
			this.length++;
			this.array[destIndex] = newValue;
			siftUp(destIndex);
			return true;
		}

		private void enlargerTheHeap() {
			int[] temp = this.array;
			this.array = new int[this.length * 2];
			System.arraycopy(temp, 0, this.array, 0, length);
		}

		public int peek() {
			return this.array[0];
		}

		public int pop() {
			int result = this.array[0];
			this.array[0] = this.array[this.length - 1];
			this.length--;
			siftdown(0);
			return result;
		}

		private void swap(int index, int leftIndex) {
			int temp = this.array[index];
			this.array[index] = this.array[leftIndex];
			this.array[leftIndex] = temp;
		}

		private int getRoot(int index) {
			return (index - 1) / 2;
		}

		/**
		 * 
		 * @param rootIndex
		 * @return if result > this.array.length, return -1, else return result;
		 */
		private int getLeftIndex(int rootIndex) {
			int result = (rootIndex + 1) * 2 - 1;
			return isLegalIndex(result) ? result : -1;
		}

		private int getRightIndex(int rootIndex) {
			int result = (rootIndex + 1) * 2;
			return isLegalIndex(result) ? result : -1;
		}

		private boolean isLegalIndex(int result) {
			return result < this.length;
		}

	}
}