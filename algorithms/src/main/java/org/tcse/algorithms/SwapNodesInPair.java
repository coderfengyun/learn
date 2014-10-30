package org.tcse.algorithms;

import org.junit.Test;

public class SwapNodesInPair {
	private static class ListNode {
		int val;
		ListNode next;

		public ListNode(int val) {
			this.val = val;
			this.next = null;
		}
	}

	public ListNode swapPairs(ListNode head) {
		if (head == null) {
			return null;
		}
		ListNode vHead = new ListNode(-1), tempVHead = new ListNode(-1), resultVHead = new ListNode(
				-1), resutlEnd = resultVHead;
		vHead.next = head;
		ListNode iterator = head, iteratorNext = head.next;
		int currentNodeCount = 0;
		while (iterator != null) {
			if (currentNodeCount == 2) {
				resutlEnd.next = tempVHead.next;
				resutlEnd = resutlEnd.next.next;
				currentNodeCount = 0;
				tempVHead.next = null;
			} 
			vHead.next = iteratorNext;
			iterator.next = tempVHead.next;
			tempVHead.next = iterator;
			currentNodeCount++;
			
			
			iterator = iteratorNext;
			iteratorNext = iteratorNext == null ? null : iteratorNext.next;
		}
		resutlEnd.next = tempVHead.next;
		return resultVHead.next;
	}
	
	@Test
	public void test(){
		ListNode head = new ListNode(1), current = head;
		current.next = new ListNode(2);
		current = current.next;
		current.next = new ListNode(1);
		current = current.next;
		current.next = new ListNode(2);
		current = current.next;
		current.next = new ListNode(1);
		current = this.swapPairs(head);
		while (current != null) {
			System.out.println(current.val);
			current = current.next;
		}
	}
}
