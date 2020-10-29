package com.codeWithBandar;

import java.util.*;

public class Node implements Comparable<Node>{
    public final String state;
    public static String goal ;
    public int heuristicValue;
    public Node parent;
    public int actualCost; // the actual cost used with heuristic value (estimated cost) to help find the shortest path to the goal. F(n) = g(n) +h(n)
    public static int numberOfNodeGenerated;
    public static int numberOfNodeVisited;
    public Node(Node parent, String current) {
        this.state = current;
        this.parent = parent;
        numberOfNodeGenerated++;
        heuristicValue = manhattanDistance(state);

        // the actual cost in 8- current is considered to be a level of the node .. we increase it here as we add children which means we are adding a new level
        if(parent != null)
            this.actualCost = parent.actualCost +1;
        else
            numberOfNodeGenerated=numberOfNodeVisited=0;

    }
    public boolean isGoal(){ // this method is used to check if the current node is a goal node
        if(goal.equals(this.state)){
            List<Node> path = new ArrayList<>();
            Node current = this;
            while(current!= null){
                path.add(current);
                current = current.parent;
            }
            Collections.reverse(path);
            path.forEach(i-> System.out.println("\n     |\n     |\n     |\n    \\ /\n\n"+i)); // decoration for the console print
            System.out.println("\nPath size = "+(path.size()-1));
            System.out.println("Node visited = "+numberOfNodeVisited);
            System.out.println("Number of Node explored before the goal level = "+ numberOfNodeGenerated);
            return true;
        }
        return false;
    }
    private int manhattanDistance(String current){ // calculating Heuristic value using Manhattan distance (how far is the number from its goal position )
        int sum=0;
        char[][]goal=new char[3][]; // converting goal state into 2-D array to help detecting the row and column
        char[][]currentState = new char[3][];

        goal[0]= Node.goal.substring(0,3).toCharArray(); // getting the rows ..
        goal[1]= Node.goal.substring(3,6).toCharArray();
        goal[2]= Node.goal.substring(6,9).toCharArray();

        currentState[0]=current.substring(0,3).toCharArray();
        currentState[1]=current.substring(3,6).toCharArray();
        currentState[2]=current.substring(6,9).toCharArray();

        for (int i = 0; i < goal.length; i++) {
            for (int j = 0; j <goal[i].length ; j++) {
                if(currentState[i][j]=='0')continue; // if the number in its goal position or if the number is space (0) then continue

                var value = indexOf(goal,currentState[i][j]); // get the row and column to help calculating how far is the number from its position
                int row = Integer.parseInt(""+value.charAt(0));
                int col = Integer.parseInt(""+value.charAt(1));

                /*
                once we get here that means we only have 3-cases :
                1- the number is in the same row as its goal position
                2- the number is in the same column as its goal position
                3- the number is neither in the row or column as its goal position

                ** Note: you still can use only Math.abs(j-col) + Math.abs(i-row) but I decide to show all the cases :) **
                 */
                if(row == i) sum+=Math.abs(j-col);
                else if(col == j) sum+=Math.abs(i-row);
                else sum+=Math.abs(j-col) + Math.abs(i-row);
            }
        }
        return sum;
    }
    private static String indexOf(char[][] array, char value){ // a helper method to find the row and column for manhattanDistance method above
        String rowAndColumn="";
        for (int i = 0; i <array.length ; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if(array[i][j]==value){
                    rowAndColumn = i+""+j;
                    return rowAndColumn;
                }
            }
        }
        return rowAndColumn;
    }
    private String generate(Set<String>visited,String position,String currentState){ // generate a node for the current node
        StringBuilder m = new StringBuilder(currentState);
        int i = currentState.indexOf('0');
        if (isEdge(position, i)) return null;
        return generateNode(position, currentState, m, i,visited);
    }

    private String generateNode(String position, String currentState, StringBuilder m, int i,Set<String>visited) { // helper for the above method " always keep a method do one thing only -_* "
        switch (position) {
            case "up" -> { // switching the numbers ..
                char temp = currentState.charAt(currentState.indexOf('0') - 3);
                m.deleteCharAt(i);
                m.insert(i, temp);
                m.deleteCharAt(currentState.indexOf('0') - 3);
                m.insert(currentState.indexOf(temp), '0');
                return (visited.contains(m.toString())) ? null : m.toString();
            }
            case "down" -> {
                char temp = currentState.charAt(currentState.indexOf('0') + 3);
                m.deleteCharAt(i);
                m.insert(i, temp);
                m.deleteCharAt(currentState.indexOf('0') + 3);
                m.insert(currentState.indexOf(temp), '0');
                return (visited.contains(m.toString())) ? null : m.toString();
            }
            case "right" -> {
                char temp = currentState.charAt(currentState.indexOf('0') + 1);
                m.deleteCharAt(i);
                m.insert(i, temp);
                m.deleteCharAt(currentState.indexOf('0') + 1);
                m.insert(currentState.indexOf(temp), '0');
                return (visited.contains(m.toString())) ? null : m.toString();
            }
            case "left" -> {
                char temp = currentState.charAt(currentState.indexOf('0') - 1);
                m.deleteCharAt(i);
                m.insert(i, temp);
                m.deleteCharAt(currentState.indexOf('0') - 1);
                m.insert(currentState.indexOf(temp), '0');
                return (visited.contains(m.toString())) ? null : m.toString();
            }
        }
        return m.toString();
    }

    private boolean isEdge(String position, int i) { // helper for the above method
        if(position.equals("up") && i <= 2) return true;
        if(position.equals("left") &&  (i== 0 || i==3||i==6)) return true;
        if(position.equals("right") && (i==2||i==5||i==8)) return true;
        return position.equals("down") && i >= 6;
    }

    public void addChildren(Map<Node,List<Node>> map, Set<String> visited, List<Node>list, Node node){
        directions(map,node,list, generate(visited,"up",node.state), generate(visited,"down",node.state)); // sending the 4 nodes to the other method to add it to our graph
        directions(map,node,list, generate(visited,"left",node.state), generate(visited,"right",node.state));
    }
    private void directions(Map<Node,List<Node>> map,Node parent,List<Node> list,String up, String down) { // helper method for the above one
        if(up !=null){var root = new Node(parent,up);list.add(root);map.putIfAbsent(root,new ArrayList<>());} // if the node is not null ( which means it is not at the edges ) then create it
        if(down!=null){var root = new Node(parent,down);list.add(root);map.putIfAbsent(root,new ArrayList<>());}
    }
    @Override
    public String toString() { // displaying the state in understandable way ..
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
    public int compareTo(Node o) { // with out this method we cannot implement priority queue ..
        return (manhattanDistance(state) + actualCost) - (manhattanDistance(o.state) + o.actualCost);
    }
}
