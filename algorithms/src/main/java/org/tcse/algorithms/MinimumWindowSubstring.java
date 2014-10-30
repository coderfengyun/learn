package org.tcse.algorithms;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MinimumWindowSubstring {
	public String minWindow(String S, String T) {
		String result = "";
		if (S == null || T == null || S.length() == 0 || T.length() == 0) {
			return result;
		}
		Map<Character, Integer> tMap = construct(T), resultMap = new HashMap<Character, Integer>();
		int begin = 0, end = -1, validCount = 0;
		for(int i = 0; i < S.length(); i++){
		    char ch = S.charAt(i);
		    end = i;
		    if(!tMap.containsKey(ch)){
		        continue;
		    }
	        //tMap contains key
	        if(!resultMap.containsKey(ch)){
	            resultMap.put(ch, 1);
	            validCount++;
	        }else if(resultMap.get(ch) < tMap.get(ch)){
	            resultMap.put(ch, resultMap.get(ch) + 1);
	            validCount++;
	        }else{
	            // resultMap containsKey ch, and resultMap.get(ch) == tMap.get(ch)
	        }
		    if(validCount == T.length()){
		        char beginChar = S.charAt(begin);
		        while(!resultMap.containsKey(beginChar) || resultMap.get(beginChar) > tMap.get(beginChar)){
		            begin++;
		            if(begin == S.length()) break;
		            beginChar = S.charAt(begin);
		        }
		        if(end + 1 - begin < result.length() || result.length() == 0){
	                result = S.substring(begin, end + 1);
	            }
		    }
		}
        return result;
	}
	
	private Map<Character, Integer> construct(String s){
	    Map<Character, Integer> result = new HashMap<Character, Integer>();
	    for(char ch : s.toCharArray()){
	        if(result.containsKey(ch)){
	            result.put(ch, result.get(ch) + 1);
	        }else{
	            result.put(ch, 1);
	        }
	    }
	    return result;
	}
	
	@Test
	public void test(){
		String result = this.minWindow("ab", "a");
		assertEquals("a", result);
	}
	
//	@Test
//	public void test1(){
//		String result = this.minWindow("", T)
//	}
}
