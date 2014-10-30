package org.tcse.algorithms;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class GrayCode {
	public List<Integer> grayCode(int n){
		List<Integer> result = new LinkedList<Integer>();
		if (n <= 0) {
			return result;
		}
		int firstOfLevel = 0;
		for (int i = 0; i < n; i++) {
			List<Integer> level = buildLevel(firstOfLevel, i);
			result.addAll(level);
			firstOfLevel = level.get(level.size() - 1) + (int) Math.pow(2, i + 1);
		}
		return result;
	}

	private List<Integer> buildLevel(int firstOfLevel, int i) {
		List<Integer> alreadyExists = new LinkedList<Integer>();
		int levelLength = i == 0 ? 2 : (int) Math.pow(2, i);
		alreadyExists.add(firstOfLevel);
		int currentValue = firstOfLevel;
		for (int j = 1; j < levelLength; j++) {
			int currentBit = 0, next = next(currentValue, 0);
			while (alreadyExists.contains(next) && currentBit < i) {
				currentBit++;
				next = next(currentValue, currentBit);
				continue;
			}
			currentValue = next;
			alreadyExists.add(currentValue);
		}
		return alreadyExists;
	}

	private int next(int currentValue, int currentBit) {
		int valueOfCheckedBit = (int)Math.pow(2, currentBit);
		if ((currentValue & valueOfCheckedBit) > 0) {
			return currentValue - valueOfCheckedBit;
		}else {
			return currentValue + valueOfCheckedBit;
		}
	}
	
	@Test
	public void test(){
		List<Integer> result = this.grayCode(5);
		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i));
		}
	}
}
