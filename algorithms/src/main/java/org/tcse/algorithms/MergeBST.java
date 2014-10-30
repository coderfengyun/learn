package org.tcse.algorithms;

import static org.junit.Assert.*;

import org.junit.Test;

public class MergeBST {

	private static class BSTTree {
		public BSTTree left;
		public BSTTree right;
		public final int val;

		public BSTTree(int val) {
			this.val = val;
		}

		public int getHeight() {
			int result = 1, leftHeight = 0, rightHeight = 0;
			if (this.left != null) {
				leftHeight = this.left.getHeight();
			}
			if (this.right != null) {
				rightHeight = this.right.getHeight();
			}
			return result + Math.max(leftHeight, rightHeight);
		}
	}

	public void merge(BSTTree tree1, BSTTree tree2) {
		if (tree1 == null || tree2 == null) {
			return;
		}
		if (tree1.getHeight() < tree2.getHeight()) {
			merge0(tree2, tree1);
		} else {
			merge0(tree1, tree2);
		}
	}

	private void merge0(BSTTree tree1, BSTTree tree2) {
		if (tree1.val >= tree2.val) {
			if (tree1.left == null) {
				tree1.left = tree2;
			} else {
				merge0(tree1.left, tree2);
			}
			if (tree2.right != null) {
				merge0(tree1, tree2.right);
				tree2.right = null;
			}
		} else {
			if (tree1.right == null) {
				tree2.right = tree2;
			} else {
				merge0(tree1.right, tree2);
			}
			if (tree2.left != null) {
				merge0(tree1, tree2.left);
				tree2.left = null;
			}
		}
	}

	@Test
	public void test() {
		BSTTree tree1 = new BSTTree(90);
		tree1.left = new BSTTree(70);
		tree1.right = new BSTTree(110);
		BSTTree tree2 = new BSTTree(60);
		tree2.left = new BSTTree(5);
		tree2.right = new BSTTree(800);
		merge(tree1, tree2);
		assertNotNull(tree1);
		assertTrue(isBST(tree1));
	}

	private boolean isBST(BSTTree tree1) {
		if (tree1 == null) {
			return true;
		}
		boolean result = true;
		if (tree1.left != null) {
			result = result && tree1.val >= tree1.left.val;
		}
		if (tree1.right != null) {
			result = result && tree1.val < tree1.right.val;
		}
		return result && isBST(tree1.left) && isBST(tree1.right);
	}

}
