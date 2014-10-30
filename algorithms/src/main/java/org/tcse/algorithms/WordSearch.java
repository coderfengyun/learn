package org.tcse.algorithms;

import static org.junit.Assert.*;

import org.junit.Test;

public class WordSearch {
	private int rowCount;
    private int columnCount;
    private char[][] board;
    private int[] rowPlus = {1, -1, 0, 0};
    private int[] columnPlus = {0, 0, -1, 1};
    private boolean[][] visited;
    public boolean exist(char[][] board, String word) {
        if(board == null || board.length == 0 || word == null || word.length() == 0){
            return false;
        }
        this.rowCount = board.length;
        this.columnCount = board[0].length;
        this.board = board;
        init(word);
        for(int rowIndex = 0; rowIndex < rowCount; rowIndex++){
            for(int columnIndex = 0; columnIndex < columnCount; columnIndex++){
                if(board[rowIndex][columnIndex] == word.charAt(0)){
                    if(checkByDFS(rowIndex, columnIndex, word, 1)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private void init(String word){
        this.visited = new boolean[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				this.visited[i][j] = false;
			}
		}
    }
    
    private boolean checkByDFS(int rowIndex, int columnIndex, String word, int checkIndex){
        if(checkIndex == word.length()){
            return true;
        }
        boolean result = false;
        this.visited[rowIndex][columnIndex] = true;
        for(int i = 0; i < 4; i++){
            int targetRowIndex = rowIndex + rowPlus[i], targetColumnIndex = columnIndex + columnPlus[i];
            if(targetRowIndex < 0 || targetRowIndex >= rowCount || targetColumnIndex < 0 || targetColumnIndex >= columnCount){
                continue;
            }
            if(this.visited[targetRowIndex][targetColumnIndex]){
                continue;
            }
            if(board[targetRowIndex][targetColumnIndex] == word.charAt(checkIndex)){
                result = result || checkByDFS(targetRowIndex, targetColumnIndex, word, checkIndex + 1);
            }
        }
        this.visited[rowIndex][columnIndex] = false;
        return result;
    }
    
    @Test
    public void test() {
    	char[][] board = new char[3][3];
    	board[0][0] = 'C';
    	for (int i = 1; i < 6; i++) {
			int row = i / 3, column = i % 3;
			board[row][column] = 'A';
		}
    	board[2][0] = 'B';
    	board[2][1] = 'C';
    	board[2][2] = 'D';
		assertTrue(this.exist(board, "AAB"));
	}
}
