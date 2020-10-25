package com.codeWithBandar;

/*
This code was written by Bandar 
 */
import java.util.*;

public class A_Star {
    private static class Node implements Comparable{
        private final String state;
        public Node(String puzzle) {
            this.state = puzzle;
        }
        @Override
        public String toString() {
            StringBuilder m = new StringBuilder();
            for (int i = 0; i < state.length(); i++) {

                if(state.charAt(i)=='0')m.append('*');
                else m.append(state.charAt(i));
                m.append("\t");
                if(i == 2 || i ==5)
                    m.append("\n");

            }
            return m.toString();
        }

        @Override
        public int compareTo(Object o) {
            return   (A_Star.calculateHeuristicValue(this.state)+g)-(A_Star.calculateHeuristicValue(((Node)o).state)+g);
        }
    }
    static String goal;
    static int g;
    Map<String,Node> state ;
    Map<Node, List<Node>> map;
    Set<String> visited;
    ArrayList<Node>path;
    int zg= 0;
    public A_Star(String initial, String goal){

        A_Star.goal = goal;
        var node = new Node(initial);

        state = new HashMap<>();
        map= new HashMap<>();
        visited = new HashSet<>();
        path=new ArrayList<>();

        state.putIfAbsent(initial,node);
        map.putIfAbsent(node,new ArrayList<>());
        search();

    }
    private boolean isGoal(String current){
    if(goal.equals(current)){
        System.out.println("GOAL!!");
        System.out.println("Path size = "+path.size());
        System.out.println("Node visited = "+visited.size());
        System.out.println("Number of Node explored before the goal level = "+zg);
        System.out.println("G = "+g);
        return true;
    }
    return false;
    }
    static int calculateHeuristicValue(String current){
        int num =0;
        for (int i = 0; i < current.length(); i++) {
            if(current.charAt(i)=='0')continue;
            if(current.charAt(i) != goal.charAt(i))num++;
        }

        return num;
    }
    static int minhatinDistance(String current){
        int sum=0;
        char[][]goal=new char[3][];
        char[][]currentState = new char[3][];
        goal[0]=A_Star.goal.substring(0,3).toCharArray();
        goal[1]=A_Star.goal.substring(3,6).toCharArray();
        goal[2]=A_Star.goal.substring(6,9).toCharArray();

        currentState[0]=current.substring(0,3).toCharArray();
        currentState[1]=current.substring(3,6).toCharArray();
        currentState[2]=current.substring(6,9).toCharArray();

        for (int i = 0; i < goal.length; i++) {
            for (int j = 0; j <goal[i].length ; j++) {
                if(currentState[i][j]==goal[i][j] || currentState[i][j]=='0')continue;

                var value = indexOf(goal,currentState[i][j]);
                int row =Integer.parseInt(value.charAt(0)+"");
                int col = Integer.parseInt(value.charAt(1)+"");

                if(row == i) sum+=Math.abs((j+1)-(col+1));
                else if(col == j) sum+=Math.abs((i+1)-(row+1));
                else sum+=((Math.abs((j+1)-(col+1))) + (Math.abs((i+1)-(row+1))));
            }
        }
        return sum;
    }
    private static String indexOf(char[][] array, char value){
        for (int i = 0; i <array.length ; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if(array[i][j]==value)return i+""+j;
            }
        }
        return null;
    }
    private String generate(String position,String currentState){
        StringBuilder m = new StringBuilder(currentState);
        for (int  i = 0; i<9 ; i++) {
            if(currentState.charAt(i) != '0')continue;
            if(position.equals("up") && i <= 2)return null;
            if(position.equals("left") &&  (i== 0 || i==3||i==6))return null;
            if(position.equals("right") && (i==2||i==5||i==8))return null;
            if(position.equals("down") && i>=6)return null;

            switch (position) {
                case "up" -> {
                    char temp = currentState.charAt(currentState.indexOf('0') - 3);
                    m.deleteCharAt(i);
                    m.insert(i, temp);
                    m.deleteCharAt(currentState.indexOf('0') - 3);
                    m.insert(currentState.indexOf(temp), '0');
                    return m.toString();
                }
                case "down" -> {
                    char temp = currentState.charAt(currentState.indexOf('0') + 3);
                    m.deleteCharAt(i);
                    m.insert(i, temp);
                    m.deleteCharAt(currentState.indexOf('0') + 3);
                    m.insert(currentState.indexOf(temp), '0');
                    return m.toString();
                }
                case "right" -> {
                    char temp = currentState.charAt(currentState.indexOf('0') + 1);
                    m.deleteCharAt(i);
                    m.insert(i, temp);
                    m.deleteCharAt(currentState.indexOf('0') + 1);
                    m.insert(currentState.indexOf(temp), '0');
                    return m.toString();
                }
                case "left" -> {
                    char temp = currentState.charAt(currentState.indexOf('0') - 1);
                    m.deleteCharAt(i);
                    m.insert(i, temp);
                    m.deleteCharAt(currentState.indexOf('0') - 1);
                    m.insert(currentState.indexOf(temp), '0');
                    return m.toString();
                }
            }
        }

        return null;

    }
    private void addChildren(List<Node>list, Node node){
        g++;
        directions(list, generate("up",node.state), generate("down",node.state));
        directions(list, generate("left",node.state), generate("right",node.state));
    }
    private void directions(List<Node> list, String up, String down) {
        if(up !=null){var root = new Node(up);list.add(root);map.putIfAbsent(root,new ArrayList<>());}
        if(down!=null){var root = new Node(down);list.add(root);map.putIfAbsent(root,new ArrayList<>());}
    }
    private void search(){
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        priorityQueue.add((Node)map.keySet().toArray()[0]);
        while (!priorityQueue.isEmpty()){
            var node = priorityQueue.poll();
            System.out.println("\n     |\n     |\n     |\n    \\ /\n");
            System.out.println(node+"\tH= "+minhatinDistance(node.state));
            visited.add(node.state);
            path.add(node);
            if(isGoal(node.state))return;

            addChildren(map.get(node),node);
            for(var i : map.get(node)){
                if(visited.contains(i.state))
                    continue;
                priorityQueue.add(i);
                zg++;
            }
        }
    }



}
