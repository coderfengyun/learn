package org.tcse.algorithms;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BigIntegerCalculation {

	public String multiply(String oprand1, String oprand2) {
		if (oprand1 == null || oprand2 == null || oprand1.length() == 0
				|| oprand2.length() == 0) {
			throw new IllegalArgumentException();
		}
		int length1 = oprand1.length(), length2 = oprand2.length();
		List<Integer> digitAsBaseAscending = init(length1 + length2);
		for (int i = length1 - 1; i >= 0; i--) {
			int val1 = oprand1.charAt(i) - '0';
			for (int j = length2 - 1; j >= 0; j--) {
				int val2 = oprand2.charAt(j) - '0';
				int baseIndex = length1 + length2 - (i + j) - 2;
				int origin = digitAsBaseAscending.get(baseIndex);
				digitAsBaseAscending.set(baseIndex, origin + val1 * val2);
			}
		}
		int carry = 0;
		for (int i = 0; i < digitAsBaseAscending.size(); i++) {
			int current = digitAsBaseAscending.get(i) + carry, remainder = current % 10;
			carry = current / 10;
			digitAsBaseAscending.set(i, remainder);
		}
		return assemblyResult(digitAsBaseAscending);
	}

	private String assemblyResult(List<Integer> digitAsBaseAscending) {
		StringBuilder stringBuilder = new StringBuilder();
		int i = digitAsBaseAscending.size() - 1;
		for (; i >= 0; i--) {
			if (digitAsBaseAscending.get(i) != 0)
				break;
		}
		for (; i >= 0; i--) {
			stringBuilder.append(digitAsBaseAscending.get(i));

		}
		return stringBuilder.toString();
	}

	private ArrayList<Integer> init(int totalLength) {
		ArrayList<Integer> arrayList = new ArrayList<Integer>(totalLength);
		for (int i = 0; i < totalLength; i++) {
			arrayList.add(0);
		}
		return arrayList;
	}

	@Test
	public void testMultiply() {
		assertEquals(12345 * 98765 + "", this.multiply("12345", "98765"));
	}

	@Test
	public void testDecrease() {
		assertEquals(987654 - 123456 + "", this.decrease("987654", "123456"));
	}

	public String decrease(String string1, String string2) {
		if (!isABSLarger(string1, string2)) {
			String temp = string1;
			string1 = string2;
			string2 = temp;
		}
		boolean isResultNegtive = string1.charAt(0) == '-';
		string1 = removeSymbol(string1);
		string2 = removeSymbol(string2);
		return (isResultNegtive ? "-" : "") + decreaseCore(string1, string2);
	}

	// both string1 and string2 are positive, and string1 > string2
	private String decreaseCore(String string1, String string2) {
		StringBuilder stringBuilder = new StringBuilder(string1), stringBuilder2 = new StringBuilder(
				string2), resultBuilder = new StringBuilder();
		stringBuilder.reverse();
		stringBuilder2.reverse();
		int carry = 0, currentResult = 0;
		for (int i = 0; i < stringBuilder2.length(); i++, currentResult = 0) {
			currentResult += stringBuilder.charAt(i) - '0' - carry;
			if (i < stringBuilder2.length()) {
				currentResult -= stringBuilder2.charAt(i) - '0';
			}
			carry = currentResult < 0 ? 1 : 0; 
			currentResult = (currentResult + 10) % 10;
			resultBuilder.append(currentResult);
		}
		return resultBuilder.reverse().toString();
	}

	private boolean isABSLarger(String string1, String string2) {
		string1 = removeSymbol(string1);
		string2 = removeSymbol(string2);
		if (string1.length() > string2.length()) {
			return true;
		} else if (string1.length() < string2.length()) {
			return false;
		}
		for (int i = 0; i < string1.length(); i++) {
			int val1 = string1.charAt(i), val2 = string2.charAt(i);
			if (val1 == val2) {
				continue;
			}
			return val1 > val2 ? true : false;
		}
		return false;
	}

	private String removeSymbol(String string1) {
		if (string1 == null || string1.length() > 0) {
			return string1;
		}

		char firstChar = string1.charAt(0);
		return (firstChar == '-' || firstChar == '+') ? string1.substring(1)
				: string1;
	}
}
