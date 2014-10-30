package org.tcse.algorithms;

import org.junit.Test;

public class Zigzag {
	public String convert(String s, int nRows) {
        if(s == null || nRows <= 1 || s.length() <= nRows){
            return s;
        }
        StringBuilder resultBuilder = new StringBuilder();
        int itemLength = nRows * 2 - 2, count = s.length() / nRows, lastColumnLength = s.length() % nRows;
        if(lastColumnLength != 0){
            count++;
        }
        char[][] columns = new char[count][itemLength];
        for(int i = 0; i < count; i++){
            for(int j = 0; j < itemLength; j++){
                if(i * itemLength + j >= s.length()){
                    break;
                }
                columns[i][j] = s.charAt(i * itemLength + j);
            }
        }
        for(int j = 0; j < nRows; j++){
        	for(int i = 0; i < count; i++){
                if(i * itemLength + j >= s.length()){
                    break;
                }
                if((j == 0 || j == nRows - 1)){
                    resultBuilder.append(columns[i][j]);      
                }else{
                    resultBuilder.append(columns[i][j]);
                    if(i * itemLength + itemLength - j < s.length()){
                        resultBuilder.append(columns[i][itemLength - j]);
                    }
                }
            }
        }
        return resultBuilder.toString();
    }
	
	@Test
	public void test(){
		System.out.println(this.convert("ABC", 2));
	}
}
