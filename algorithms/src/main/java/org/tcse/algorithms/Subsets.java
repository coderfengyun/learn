package org.tcse.algorithms;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class Subsets {
	public List<List<Integer>> subsets(List<Integer> S) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		if (S == null || S.isEmpty()) {
			return result;
		}
		return subsets0(0, S);
	}

	private List<List<Integer>> subsets0(int beginIndex, List<Integer> S) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		if (beginIndex == S.size()) {
			return result;
		}
		for (int i = beginIndex + 1; i < S.size(); i++) {
			swap(S, beginIndex, i);
			result.addAll(subsets0(i, S));
			swap(S, i, beginIndex);
		}
		return result;
	}

	private void swap(List<Integer> S, int swapIndex, int targetIndex) {
		int tmp = S.get(swapIndex);
		S.set(swapIndex, S.get(targetIndex));
		S.set(targetIndex, tmp);
	}

	@Test
	public void test() {
		assertTrue(this.subsets(new ArrayList<Integer>()).isEmpty());
	}
}
