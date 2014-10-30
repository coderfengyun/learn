package org.tcse.algorithms;

public class LengthOfLastWord {
	public int lengthOfLastWord(String s) {
        if(s == null || s.isEmpty()){
            return 0;
        }
        int lastSpaceAddr = -1;
        for(int i = s.length() - 1; i >= 0 ; i--){
            if(s.charAt(i) == ' '){
                if(lastSpaceAddr == -1){
                    lastSpaceAddr = i;    
                }else{
                    return lastSpaceAddr - i - 1;
                }
                if(s.length() - i - 1 == 0){
                    continue;
                }
                return s.length() - i - 1;
            }
        }
        if (lastSpaceAddr != -1) {
			return 0;
		}
        return s.length();
    }
}
