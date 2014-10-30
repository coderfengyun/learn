package org.tcse.algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class Subset {
	public List<List<Integer>> subsets(int[] S) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<List<List<Integer>>> resultArray = new ArrayList<List<List<Integer>>>();
        if(S == null || S.length == 0){
            return result;
        }
        result.add(new LinkedList<Integer>());
        List<List<Integer>> singleItemResult = new ArrayList<List<Integer>>();
        for(int i = 0; i < S.length; i++){
            List<Integer> item = new LinkedList<Integer>();
            item.add(i);
            singleItemResult.add(item);
        }
        resultArray.add(singleItemResult);
        for(int combinationLength = 1; combinationLength < S.length; combinationLength++){
            List<List<Integer>> thisLevel = new ArrayList<List<Integer>>();
            for(List<Integer> upLevelResult : resultArray.get(combinationLength - 1)){
                for(int i = upLevelResult.get(upLevelResult.size() - 1) + 1; i < S.length; i++){
                    List<Integer> item = new LinkedList<Integer>(upLevelResult);
                    item.add(i);
                    thisLevel.add(item);
                }
            }
            resultArray.add(thisLevel);
        }
        for(List<List<Integer>> doubelLevelItem : resultArray){
            for(List<Integer> indexItem : doubelLevelItem){
                List<Integer> valueItem = new LinkedList<Integer>();
                for(Integer index : indexItem){
                    valueItem.add(S[index]);
                }
                result.add(valueItem);
            }
        }
        return result;
    }
	
	@Test
	public void test(){
		this.subsets(new int[]{1,2});
	}
}
