package org.tcse.algorithms;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.junit.Test;

public class MinimumWindowSubstring2 {
	public String minWindow(String S, String T) {
		String result = "";
		if (S == null || T == null || S.length() == 0 || T.length() == 0) {
			return result;
		}
		Map<Character, Integer> targetFeature = findFeature(T);
		Map<Character, Integer> sourceWindow = new HashMap<Character, Integer>();
		LinkedList<Integer> indexWindow = new LinkedList<Integer>();
		int windowLength = Integer.MAX_VALUE, appeared = 0;
		for(int i = 0; i < S.length(); i++){
		    char ch = S.charAt(i);
		    if(targetFeature.containsKey(ch)){
		        //remove redundant above
		        
		    	indexWindow.add(i);
		        if (!sourceWindow.containsKey(ch) || sourceWindow.get(ch) < targetFeature.get(ch)) {
					appeared++;
				}
		    	if (!sourceWindow.containsKey(ch)) {
					sourceWindow.put(ch, 0);
				}
		        sourceWindow.put(ch, sourceWindow.get(ch) + 1);
		        while(sourceWindow.get(S.charAt(indexWindow.getFirst())) > targetFeature.get(S.charAt(indexWindow.getFirst()))){
		            sourceWindow.put(S.charAt(indexWindow.getFirst()), sourceWindow.get(S.charAt(indexWindow.getFirst())) - 1);
		            indexWindow.removeFirst();
		        }
		        if(appeared >= T.length()){
			        int currentWindowLength = indexWindow.getLast() - indexWindow.getFirst() + 1;
	                if(windowLength > currentWindowLength){
	                    windowLength = currentWindowLength;
	                    result = S.substring(indexWindow.getFirst(), indexWindow.getLast() + 1);
	                }
			    }
		    }
		}
		return result;
	}
	

	private Map<Character, Integer> findFeature(String target){
	    Map<Character, Integer> result = new HashMap<Character, Integer>();
	    for(char ch : target.toCharArray()){
	        if(result.keySet().contains(ch)){
	            result.put(ch, result.get(ch) + 1);
	        }else{
                result.put(ch, 1);	            
	        }
	    }
	    return result;
	}

	@Test
	public void test() {
		assertEquals("BANC", this.minWindow("ADOBECODEBANC", "ABC"));
	}

	@Test
	public void test2() {
		assertEquals("BAC", this.minWindow("ACNVFHBAC", "ABC"));
	}

	@Test
	public void test3() {
		assertEquals("aa", this.minWindow("aa", "aa"));
	}
	
	@Test
	public void test4() {
		assertEquals("ba", this.minWindow("bba", "ab"));
	}
	
	@Test
	public void test5(){
		assertEquals("baca", this.minWindow("acbbaca", "aba"));
	}
}
