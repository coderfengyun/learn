package org.tcse.algorithms;

import org.junit.Test;
import org.tcse.algorithms.base.ListNode;

public class ReverseKGroup {
	public ListNode reverseKGroup(ListNode head, int k) {
        if(head == null || k < 2){
            return head;
        }
        ListNode current = head, vHead = new ListNode(-1), pre = vHead;
        while(current != null){
            HeadTail headTail = reverseK(current, k);
            pre.next = headTail.head;
            
            current = headTail.nextHead;
            pre = headTail.tail;
        }
        return vHead.next;
    }
    
    private HeadTail reverseK(ListNode head, int k){
        ListNode vHead = new ListNode(-1), current = head, next = head.next;
        for(int i = 0; i < k; i++){
            if(current == null){
                return new HeadTail(head, null, null);
            }
            current = current.next;
        }
        current = head;
        for(int i = 0; i < k && current != null; i++){
            current.next = vHead.next;
            vHead.next = current;
            current = next;
            if(next != null){
                next = next.next;
            }
        }
        return new HeadTail(vHead.next, head, current);
    }
    
    private static class HeadTail{
        private final ListNode head;
        private final ListNode tail;
        private final ListNode nextHead;
        public HeadTail(final ListNode head, final ListNode tail, final ListNode nextHead){
            this.head = head;
            this.tail = tail;
            this.nextHead = nextHead;
        }
    }
    
    @Test
    public void test(){
    	ListNode head = new ListNode(1);
    	head.next = new ListNode(2);
    	head.next.next = new ListNode(3);
		this.reverseKGroup(head , 2);
    }
}
