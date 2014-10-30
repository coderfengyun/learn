package org.tcse.algorithms;

import static org.junit.Assert.*;

import org.junit.Test;

public class RemovdDuplicateFromLinkedList2 {
	private static class ListNode {
		int val;
		ListNode next;

		public ListNode(int val) {
			this.val = val;
			this.next = null;
		}
	}

	public ListNode deleteDuplicates(ListNode head) {
		ListNode vHead = new ListNode(-1);
		vHead.next = head;
		if(head == null){
			return null;
		}
		ListNode removeBeginPrevious = null, removeEnd = null;
		ListNode iterator = head, iteratorPrevious = vHead, iteratorNext = head.next;
		while (iterator != null && iterator.next != null) {
			if (iterator.val == iterator.next.val) {
				if (removeBeginPrevious == null) {
					removeBeginPrevious = iteratorPrevious;
				}
				removeEnd = iterator.next;
			} else {
				if (removeBeginPrevious != null && removeEnd != null) {
					removeBeginPrevious.next = removeEnd.next;
					removeBeginPrevious = null;
					removeEnd = null;
				}else{
					iteratorPrevious = iterator;
				}
			}
			iterator = iteratorNext;
			iteratorNext = iteratorNext.next;
		}
		removeBeginPrevious.next = removeEnd.next;
		return vHead.next;
	}

	@Test
	public void test() {
		assertEquals(null, deleteDuplicates(null));
	}

	@Test
	public void test1(){
		ListNode head = new ListNode(1), current = head;
		current.next = new ListNode(1);
		assertEquals(null, deleteDuplicates(head));
	}
	
	@Test
	public void testWithDuplicates() {
		ListNode head = new ListNode(1), current = head;
		current.next = new ListNode(2);
		current = current.next;
		current.next = new ListNode(2);
		current = current.next;
		current.next = new ListNode(3);
		current = current.next;
		current.next = new ListNode(3);
		current = current.next;
		current.next = new ListNode(4); 
		current =current.next;
		current.next = new ListNode(5);
		ListNode headAfterRemove = this.deleteDuplicates(head);
		current = headAfterRemove;
		while(current != null && current.next != null){
			assertNotEquals(current.val, current.next.val);
			current = current.next;
			System.out.println(current.val);
		}
	}
}
