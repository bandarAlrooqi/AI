package com.codeWithBandar;

public class Main {

    public static void main(String[] args) {
        EightPuzzle puzzle =new EightPuzzle(new int[][]{
                {1,4,3},
                {7,0,6},
                {5,8,2}
        });
       var puzzleUsingString = new EightPuzzleUsingString("724506831","123456780");
       A_Star a_star = new A_Star("283164705","123804765");
    }
}
