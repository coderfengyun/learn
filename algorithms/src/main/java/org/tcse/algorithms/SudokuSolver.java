package org.tcse.algorithms;

import org.junit.Test;

public class SudokuSolver {
	private int rowCount;
    private int rowLength;
    public void solveSudoku(char[][] board) {
        if(board == null || board.length != 9 || board[0].length != 9){
            return;
        }
        this.rowCount = board.length;
        this.rowLength = board[0].length;
        solveSudoku0(board, 0);
    }
    
    private boolean solveSudoku0(char[][] board, int index){
        if(index == this.rowCount * this.rowLength){
            return true;
        }
        int rowIndex = getRowIndex(index), columnIndex = getColumnIndex(index);
        if(board[rowIndex][columnIndex] != '.'){
            return solveSudoku0(board, index + 1);
        }else{
            for(int i = 1; i <= 9; i++){
                if(canBePlaced(i, index, board)){
                    board[rowIndex][columnIndex] = (char) (i + '0');
                    if(!solveSudoku0(board, index + 1)){
                        board[rowIndex][columnIndex] = '.';
                    }else{
                        return true;
                    }
                }
            }
            return false;
        }
    }
    
    private int getRowIndex(int index){
        return index / this.rowLength;
    } 
    
    private int getColumnIndex(int index){
        return index % this.rowLength;
    }
    
    private boolean canBePlaced(int value, int index, char[][] board){
        //for row
        int rowIndex = getRowIndex(index), columnIndex = getColumnIndex(index);
        for(int i = 0; i < this.rowLength; i++){
            if(i == columnIndex){
                continue;
            }
            if(board[rowIndex][i] == value){
                return false;
            }
        }
        //for column
        for(int i = 0; i < this.rowCount; i++){
            if(i == rowIndex){
                continue;
            }
            if(board[i][columnIndex] == value){
                return false;
            }
        }
        //for little square
        int squareFirstRowIndex = (rowIndex / 3) * 3, squareFirstColumnIndex = (columnIndex / 3) * 3;
        for(int i = squareFirstRowIndex; i < squareFirstRowIndex + 3; i++){
            for(int j = squareFirstColumnIndex; j < squareFirstColumnIndex + 3; j++){
                if(i == rowIndex || j == columnIndex){
                    continue;
                }
                if(board[i][j] == value){
                    return false;
                }
            }
        }
        return true;
    }
    @Test
    public void test(){
    	System.out.println((char)(1 + '0'));
    }
}
