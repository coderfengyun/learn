package org.tcse.algorithms;

import static org.junit.Assert.*;

import org.junit.Test;
import org.tcse.algorithms.base.TreeNode;

public class LowestCommonParent {
	private static final int CONTAIN_NON = 0;
	private static final int CONTAIN_1 = 1;
	private static final int CONTAIN_2 = 2;
	private static final int CONTAIN_BOTH = 3;

	private TreeNode result = null;

	public TreeNode lowestCommonParent(TreeNode root, TreeNode node1,
			TreeNode node2) {
		if (root == null || node1 == null || node2 == null) {
			return root;
		}
		postOrderTraverse(root, node1, node2);
		return this.result;
	}

	private int postOrderTraverse(TreeNode root, TreeNode node1, TreeNode node2) {
		if (root == null) {
			return CONTAIN_NON;
		}
		//To check if current root match node1 or node2; 
		int containVal = root == node1 ? CONTAIN_1 : (root == node2 ? CONTAIN_2
				: CONTAIN_NON);
		int leftContains = postOrderTraverse(root.left, node1, node2), rightContains = postOrderTraverse(
				root.right, node1, node2);
		if ((leftContains == CONTAIN_BOTH) || (rightContains == CONTAIN_BOTH)) {
			return CONTAIN_BOTH;
		} else if ((leftContains | rightContains) == CONTAIN_BOTH) {
			this.result = root;
			return CONTAIN_BOTH;
		}
		return containVal | leftContains | rightContains;
	}

	@Test
	public void test1() {
		TreeNode root = buildATree();
		assertEquals(root.left,
				this.lowestCommonParent(root, root.left.left, root.left.right));
	}

	private TreeNode buildATree() {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.right = new TreeNode(3);
		root.left.left = new TreeNode(4);
		root.left.right = new TreeNode(5);
		root.right.left = new TreeNode(6);
		root.right.right = new TreeNode(7);
		root.left.left.left = new TreeNode(8);
		return root;
	}

	@Test
	public void test2() {
		TreeNode root = buildATree();
		assertEquals(root.right, this.lowestCommonParent(root, root.right.left,
				root.right.right));
	}
	
	@Test
	public void test3(){
		TreeNode root = buildATree();
		assertEquals(root, this.lowestCommonParent(root, root.left.right, root.right.left));
	}
	
	@Test
	public void test4(){
		TreeNode root = buildATree();
		assertEquals(root, this.lowestCommonParent(root, root.left.left.left, root.right.left));
	}
}
