package org.tcse.algorithms;

import org.junit.Test;

public class RemoveNthNodeFromEndOfList {
	public class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
			next = null;
		}
	}

	public ListNode removeNthFromEnd(ListNode head, int n) {
		if (head == null) {
			return head;
		}
		int length = getLength(head);
		n = n % length;
		ListNode forerunner = head, postRunner = head;
		ListNode vHead = new ListNode(-1), postRunnerPrevious = vHead;
		vHead.next = head;
		for (int i = 0; i <= n; i++) {
			forerunner = forerunner.next;
		}

		while (forerunner != null) {
			forerunner = forerunner.next;
			postRunnerPrevious = postRunnerPrevious.next;
			postRunner = postRunner.next;
		}
		postRunnerPrevious.next = postRunner.next;
		postRunner.next = null;
		return vHead.next;
	}

	private int getLength(ListNode head) {
		int result = 0;
		while (head != null) {
			result++;
			head = head.next;
		}
		return result;
	}
	
	@Test
	public void test(){
		ListNode current = this.removeNthFromEnd(new ListNode(1), 1);
		while (current != null) {
			System.out.println(current);
		}
	}
}
