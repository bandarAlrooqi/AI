package com.codeWithBandar;

import java.util.*;

public class A_Star {
    private static class Node implements Comparable{
        private final String state;
        public Node(String puzzle) {
            this.state = puzzle;
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
        public int compareTo(Object o) { // using compareTo method to make a priority queue ..
            return   (A_Star.calculateHeuristicValue(this.state)+ actualCost)-(A_Star.calculateHeuristicValue(((Node)o).state)+ actualCost);
        }
    }
    static String goal ; // the goal state is static to help using it in other classes
    static int actualCost; // the actual cost used with heuristic value (estimated cost) to help find the shortest path to the goal
    Map<String,Node> state ; // storing all the nodes
    Map<Node, List<Node>> map; // storing the node and its children
    Set<String> visited; // set of visited node to help not visiting a node twice
    ArrayList<Node>path; // an optional list used to store the whole path from initial state to the goal state .
    int numberOfNodeGenerated = 0; // calculating the number of node the explored ( this DOES NOT mean that the node is visited nor checked )
    public A_Star(String initial, String goal){

        A_Star.goal = goal;
        var node = new Node(initial);

        state = new HashMap<>();
        map= new HashMap<>();
        visited = new HashSet<>();
        path=new ArrayList<>();

        state.putIfAbsent(initial,node);
        map.putIfAbsent(node,new ArrayList<>());

        search();//kicking the program to start

    }
    private boolean isGoal(String current){ // this method is used to check if the current node is a goal node
    if(goal.equals(current)){
        System.out.println("GOAL!!");
        System.out.println("Path size = "+path.size());
        System.out.println("Node visited = "+visited.size());
        System.out.println("Number of Node explored before the goal level = "+ numberOfNodeGenerated);
        return true;
    }
    return false;
    }
    static int calculateHeuristicValue(String current){ // this method is calculating the Heuristic value based on Hamming distance ( Number of misplaced numbers )
        int num =0;
        for (int i = 0; i < current.length(); i++) {

            if(current.charAt(i)=='0')continue;

            if(current.charAt(i) != goal.charAt(i))
                num++;
        }

        return num;
    }
    static int manhattanDistance(String current){ // calculating Heuristic value using Manhattan distance (how far is the number from its goal position )
        int sum=0;
        char[][]goal=new char[3][]; // converting goal state into 2-D array to help detecting the row and column
        char[][]currentState = new char[3][];

        goal[0]=A_Star.goal.substring(0,3).toCharArray(); // getting the rows ..
        goal[1]=A_Star.goal.substring(3,6).toCharArray();
        goal[2]=A_Star.goal.substring(6,9).toCharArray();

        currentState[0]=current.substring(0,3).toCharArray();
        currentState[1]=current.substring(3,6).toCharArray();
        currentState[2]=current.substring(6,9).toCharArray();

        for (int i = 0; i < goal.length; i++) {
            for (int j = 0; j <goal[i].length ; j++) {
                if(currentState[i][j]==goal[i][j] || currentState[i][j]=='0')continue; // if the number in its goal position or if the number is space (0) then continue

                var value = indexOf(goal,currentState[i][j]); // get the row and column to help calculating how far is the number from its position
                int row = Integer.parseInt(value.charAt(0)+"");
                int col = Integer.parseInt(value.charAt(1)+"");

                /*
                once we get here that means we only have 3-cases :
                1- the number is in the same row as its goal position
                2- the number is in the same column as its goal position
                3- the number is neither in the row or column as its goal position
                 */
                if(row == i) sum+=Math.abs((j+1)-(col+1));
                else if(col == j) sum+=Math.abs((i+1)-(row+1));
                else sum+=((Math.abs((j+1)-(col+1))) + (Math.abs((i+1)-(row+1))));
            }
        }
        return sum;
    }
    private static String indexOf(char[][] array, char value){ // a helper method to find the row and column for manhattanDistance method above
        for (int i = 0; i <array.length ; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if(array[i][j]==value)return i+""+j;
            }
        }
        return null;
    }
    private String generate(String position,String currentState){ // generate a node for the current node
        StringBuilder m = new StringBuilder(currentState);
        for (int  i = 0; i<9 ; i++) {
            if(currentState.charAt(i) != '0')continue; // we are looking for the space that can move , as we cant move numbers
            if(position.equals("up") && i <= 2)return null; // avoiding edges or moving towards edges
            if(position.equals("left") &&  (i== 0 || i==3||i==6))return null;
            if(position.equals("right") && (i==2||i==5||i==8))return null;
            if(position.equals("down") && i>=6)return null;

            switch (position) {
                case "up" -> { // switching the numbers ..
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
        actualCost++; // the actual cost it 8- puzzle is considered to be a level of the node .. we increase it here as we add children which means we are adding a new level
        directions(list, generate("up",node.state), generate("down",node.state)); // sending the 4 nodes to the other method to add it to our graph
        directions(list, generate("left",node.state), generate("right",node.state));
    }
    private void directions(List<Node> list, String up, String down) { // helper method for the above one
        if(up !=null){var root = new Node(up);list.add(root);map.putIfAbsent(root,new ArrayList<>());} // if the node is not null ( which means it is not at the edges ) then create it
        if(down!=null){var root = new Node(down);list.add(root);map.putIfAbsent(root,new ArrayList<>());}
    }

    private void search(){
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(); // initializing priority queue of nodes
        priorityQueue.add((Node)map.keySet().toArray()[0]); // adding our initial state to the queue
        while (!priorityQueue.isEmpty()){
            var node = priorityQueue.poll(); // removing the node from the queue to be visited and checked
            System.out.println("\n     |\n     |\n     |\n    \\ /\n"); // decoration for the console print
            System.out.println(node+"\tH= "+ manhattanDistance(node.state)); // printing the heuristic value next to the node
            visited.add(node.state); // adding the node to visited listed in order to not visit it again
            path.add(node); // adding the node to our path
            if(isGoal(node.state))return; // checking if the current node is the goal

            addChildren(map.get(node),node); // if it is not the goal then generate children

            for(var i : map.get(node)){ // adding the generated nodes to our queue
                if(visited.contains(i.state)) // if the node has been visited before then just ignore the process and continue
                    continue;
                priorityQueue.add(i);
                numberOfNodeGenerated++;
            }
        }
    }



}
