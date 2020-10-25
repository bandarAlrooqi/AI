package com.codeWithBandar;

import java.util.*;

public class EightPuzzle {
    private int cost = 0;
    private final int[][] initialState;
    private static class Node{
        final int [][]  state;

        public Node(int[][] state) {
            this.state = state;
        }

        @Override
        public String toString() {
            StringBuilder m = new StringBuilder();
            for(var i:state){
                for(var j :i)
                    m.append(j).append("\t");
                m.append("\n");
            }
            m.append("\n-------------------------------------\n");
            return m.toString();
        }
    }
    Map<Node,Integer> states =new HashMap<>();
    public EightPuzzle(int[][]state){
       initialState =state;
        //BFS();

        cost =0;
        DFS();

    }
    private boolean perfect(Node node){
       return Arrays.equals(node.state[0],new int[]{1,2,3}) && Arrays.equals(node.state[1],new int[]{4,5,6}) && Arrays.equals(node.state[2],new int[]{7,8,0});
    }
    private void DFS(){
        Queue<Node> queue =new ArrayDeque<>();
        Set<String> set = new HashSet<>();
        queue.add(new Node(initialState));
        while (true){
            while (!queue.isEmpty()) {
                var current = queue.remove();
                if (getIn(current,"up"))
                    return;
                if (getIn(current,"down"))
                    return;
                if (getIn(current,"right"))
                    return;
                if (getIn(current,"left"))
                    return;
            }
            for(var i : states.keySet()) {
                if(set.contains(zg(i))){
                    continue;
                }
                if (perfect(i)) {
                    System.out.println(i);
                    System.out.println("Node created "+cost);
                    states.clear();
                    return;
                }
                if(!queue.contains(i)) {
                    queue.add(i);
                }
                set.add(zg(i));
            }
            states.clear();
            }

    }
    private void BFS(){
        Stack<Node> stack =new Stack<>();
        Set<String> set =new HashSet<>();
        stack.push(new Node(initialState));
        while (true){
            while (!stack.empty()){
                var current = stack.pop();
                if (getIn(current,"up"))
                   return;
                if (getIn(current,"down"))
                    return;
                if (getIn(current,"right"))
                    return;
                if (getIn(current,"left"))
                    return;
            }
            for(var i : states.keySet()) {
                if(set.contains(zg(i))){
                    continue;
                }
                if (perfect(i)) {
                    System.out.println(i);
                    System.out.println("Node created "+cost);
                    states.clear();
                    return;
                }
                stack.push(i);
                set.add(zg(i));
                System.out.println(cost);
            }
        }

    }


    private int[][] generateNode(String position,int[][]array){
        int row ;
        int col ;
        for (row = 0; row <3 ; row++) {
            for (col = 0; col < 3; col++) {
                if(array[row][col] != 0)continue;
                if(position.equals("up") && row == 0)return null;
                if(position.equals("left") && col == 0)return null;
                if(position.equals("right") && col == 2)return null;
                if(position.equals("down") && row == 2)return null;
                if(position.equals("up")){
                    int temp = array[row-1][col];
                    array[row][col]=temp;
                    array[row-1][col] = 0;
                    return array;
                }
                if(position.equals("down")){
                    int temp = array[row+1][col];
                    array[row][col]=temp;
                    array[row+1][col] = 0;
                    return array;
                }
                if(position.equals("right")){
                    int temp = array[row][col+1];
                    array[row][col]=temp;
                    array[row][col+1] = 0;
                   return array;
                }
                if(position.equals("left")){
                    int temp = array[row][col-1];
                    array[row][col]=temp;
                    array[row][col-1] = 0;
                    return array;
                }
            }
        }
        return null;
    }
    private int[][] copyArray(int[][] array){
        int[][]result = new int[array.length][];
        for (int i = 0; i < array.length; i++) {
            result[i]=array[i].clone();
        }
        return result;
    }
    private boolean getIn(Node node,String position){
        var array = generateNode(position,copyArray(node.state));
        if(array == null)return false;
        node =new Node(array);

        if(perfect(node)){
            System.out.println(node);
            System.out.println("Node Created = "+cost);
            return true;
        }
        states.putIfAbsent(node,cost++);
        return false;
    }
    private String zg(Node node){
        StringBuilder m = new StringBuilder();
        for (int i = 0; i < node.state.length; i++) {
            for (int j = 0; j <node.state.length ; j++) {
                m.append(node.state[i][j]).append("\t");
            }
            m.append("\n");
        }
        return m.toString();
    }
}
