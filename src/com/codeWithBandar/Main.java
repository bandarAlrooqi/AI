package com.codeWithBandar;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
//        EightPuzzle puzzle =new EightPuzzle(new int[][]{
//                {1,4,3},
//                {7,0,6},
//                {5,8,2}
//        });
//       var puzzleUsingString = new EightPuzzleUsingString("724506831","123456780");
//        var ids = new IDS();
//        for(char i ='A';i<='Q';i++)
//            ids.addNode(i+"");
//        ids.addEdge("A","B");
//        ids.addEdge("A","C");
//        ids.addEdge("B","D");
//        ids.addEdge("B","E");
//        ids.addEdge("C","F");
//        ids.addEdge("C","G");
//        ids.addEdge("D","H");
//        ids.addEdge("D","I");
//        ids.addEdge("E","J");
//        ids.addEdge("E","k");
//        ids.addEdge("J","P");
//        ids.addEdge("J","Q");
//        ids.addEdge("F","L");
//        ids.addEdge("F","M");
//        ids.addEdge("G","N");
//        ids.addEdge("G","O");
//        ids.search("A","O",2);
//DLL dll = new DLL();
//dll.add(1);
//dll.add(2);
//dll.add(3);
//dll.delete(1);
//dll.print();



       A_Star a_star = new A_Star("283164705","123804765");
        //var puzzleUsingString = new EightPuzzleUsingString("283164705","123804765");
//        String goal ="";
//        goal=fill(goal,'w');
//        goal=fill(goal,'r');
//        goal=fill(goal,'g');
//        goal=fill(goal,'y');
//        goal=fill(goal,'b');
//        goal= fill(goal,'o');
//        System.out.println(goal);
//        var i = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        System.out.println(i.format(LocalDateTime.now()));
//        Period period = Period.between(LocalDate.of(1995,4,26),LocalDate.now());
//        System.out.println("Years\t\tMonths\t\tDays");
//        System.out.println(period.getYears() +"\t\t\t"+period.getMonths()+"\t\t\t"+period.getDays());
    }
    public static String fill(String goal,char c){
        for (int i = 0; i < 9; i++) {
            goal+=c;
        }
        return goal;
    }
}
