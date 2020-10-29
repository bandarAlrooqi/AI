package com.codeWithBandar;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //this is most difficult solvable 8-puzzle ** it needs 31 moves ** other 8-puzzle needs 30 or less moves **
        AI algorithms = new AI("867254301","123456780");
        Scanner input = new Scanner(System.in);

//        System.out.println("Enter initial state : ");
//        String initial ="";

//        for (int i = 0; i < 9; i++)
//            initial +=input.nextInt();
//
//        System.out.println("Enter goal state : ");
//        String goal = "";
//
//        for (int i = 0; i < 9; i++)
//            goal += input.nextInt();

        System.out.println("Choose Algorithm :");
        System.out.println("1. A*" +
                        "\n2.BFS"  +
                        "\n3.DFS"  );

        int choice = input.nextInt();

        if(choice ==1) algorithms.AStar();
        else if (choice == 2) algorithms.BFS();
        else algorithms.DFS();
    }
}
