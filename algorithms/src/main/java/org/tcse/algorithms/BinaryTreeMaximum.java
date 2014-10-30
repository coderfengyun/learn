package org.tcse.algorithms;

import static org.junit.Assert.*;

import org.junit.Test;

public class BinaryTreeMaximum {
	private static class TreeNode {
		private int val;
		private TreeNode left;
		private TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	private int maxPathSum = Integer.MIN_VALUE;

	public int maxPathSum(TreeNode root) {
		if (root == null) {
			return 0;
		}
		maxPathToRoot(root);
		return this.maxPathSum;
	}

	private Children maxPathToRoot(TreeNode root) {
		if (root == null) {
			return new Children(0, 0, 0);
		}
		Children result = null;
		if (root.left == null && root.right == null) {
			result = new Children(0, 0, root.val);
		} else {
			Children leftResult = maxPathToRoot(root.left);
			Children rightResult = maxPathToRoot(root.right);
			result = new Children(leftResult, rightResult, root.val);
		}
		this.maxPathSum = Math.max(result.getMaxPath(), this.maxPathSum);
		return result;
	}

	private static class Children {
		private int leftMaxPath;
		private int rightMaxPath;
		private int val;

		private Children(int leftMaxPath, int rightMaxPath, int val) {
			this.leftMaxPath = leftMaxPath;
			this.rightMaxPath = rightMaxPath;
			this.val = val;
		}

		private Children(Children left, Children right, int val) {
			int leftItem = Math.max(left.leftMaxPath, left.rightMaxPath);
			this.leftMaxPath = left.val + (leftItem > 0 ? leftItem : 0);
			int rightItem = Math.max(right.leftMaxPath, right.rightMaxPath);
			this.rightMaxPath = right.val + (rightItem > 0 ? rightItem : 0);
			this.val = val;
		}

		private int getMaxPath() {
			int result = 0;
			if (this.leftMaxPath > 0) {
				result += this.leftMaxPath;
			}
			if (this.rightMaxPath > 0) {
				result += this.rightMaxPath;
			}
			return result + this.val;
		}
	}

	@Test
	public void test() {
		TreeNode root = new TreeNode(-2);
		root.left = new TreeNode(-1);
		assertEquals(-1, this.maxPathSum(root));
	}

	@Test
	public void test2() {
		TreeNode root = new TreeNode(9);
		root.left = new TreeNode(6);
		root.right = new TreeNode(-3);

		root.right.left = new TreeNode(-6);
		root.right.right = new TreeNode(2);
		root.right.right.left = new TreeNode(2);
		root.right.right.left.left = new TreeNode(-6);
		root.right.right.left.right = new TreeNode(-6);
		assertEquals(16, this.maxPathSum(root));
	}
}
