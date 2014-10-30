package org.tcse.algorithms;

import java.util.Stack;

public class TreeMostDistance {
	private int minValue = Integer.MAX_VALUE;
	private int maxValue = Integer.MIN_VALUE;
	
	/**
	 * 
	 * @param root
	 * @return when root is null, just return 0;
	 */
	public int findMaxDistance(TreeNode root){
		Stack<TreeNode> stack = new Stack<TreeNode>();
		TreeNode current = root;
		if (current != null) {
			stack.push(current);
		}
		while (!stack.empty()) {
			current = stack.peek();
			stack.pop();
			visit(current);
			if (current.right != null) {
				stack.push(current.right);
			}
			if (current.left != null) {
				stack.push(current.left);
			}
		}
		return this.maxValue - this.minValue;
	}

	private void visit(TreeNode current) {
		this.minValue = Math.min(current.val, this.minValue);
		this.maxValue = Math.max(current.val, this.maxValue);
	}
	
	private static class TreeNode{
		private int val;
		private TreeNode left;
		private TreeNode right;
	}
}
