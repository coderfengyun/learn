package org.tcse.algorithms;

import static org.junit.Assert.*;

import org.junit.Test;

public class RotateList {
	public class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
			next = null;
		}
	}

	public ListNode rotateRight(ListNode head, int n) {
        if(head == null || n == 0){
            return head;
        }
        ListNode vHead = new ListNode(-1);
        vHead.next = head;
        int listLength = calculateLength(head);
        rotateOneTime(n % listLength, vHead);
        return vHead.next;
    }

    private int calculateLength(ListNode head){
        int result = 0;
        while(head != null){
            result ++;
            head = head.next;
        }
        return result;
    }

    private void rotateOneTime(int n, ListNode vHead) {
		ListNode forerunner = vHead.next, postRunner = vHead.next, rotatePrevious = vHead;
		if(n == 0){
		    return;
		}
		while(n > 0){
		    if(forerunner == null){
		        break;
		    }
		    forerunner = forerunner.next;
		    n--;
		}
		while(forerunner != null && forerunner.next != null){
		    rotatePrevious = rotatePrevious.next;
		    postRunner = postRunner.next;
		    forerunner = forerunner.next;
		}
		if(forerunner != null){
		    rotatePrevious = rotatePrevious.next;
		    postRunner = postRunner.next;
		}
		ListNode rotateHead = postRunner, rotateEnd = forerunner;
        rotatePrevious.next = null;
        rotateEnd.next = vHead.next;
        vHead.next = rotateHead;
	}
	
	@Test
	public void test(){
		assertEquals(1, this.rotateRight(new ListNode(1), 3).val);
	}
	
	@Test
	public void test2(){
		ListNode head = new ListNode(1), current = head;
		current.next = new ListNode(2);
		assertEquals(2, this.rotateRight(head, 1).val);
	}
	
	@Test
	public void test1(){
		ListNode head = new ListNode(1), current = head;
		current.next = new ListNode(2);
		current = this.rotateRight(head, 3);
		System.out.println(current.val);
		System.out.println(current.next.val);
	}
	
}
