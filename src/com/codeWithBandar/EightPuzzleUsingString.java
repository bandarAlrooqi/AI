package com.codeWithBandar;

import java.util.*;

public class EightPuzzleUsingString {
    private static class Node{
        private final String state;
        public Node(String puzzle) {
            this.state = puzzle;
        }

        @Override
        public String toString() {
            StringBuilder m = new StringBuilder();
            for (int i = 0; i < state.length(); i++) {
                m.append(state.charAt(i));
                m.append("\t");
                if(i == 2 || i ==5)
                    m.append("\n");

            }
            m.append("\n\n--------------------------------------------------------\n\n");
            return m.toString();
        }
    }
    int depth = 0;
    int nodeExplored =0;
    String goal;
    Map<Node,List<Node>> map = new HashMap<>(); // state , list-> generation of the state 4 max , min 2
    List<Node> path = new ArrayList<>();
    Set<String> visited = new HashSet<>(); // prevent loops
    public EightPuzzleUsingString(String initialState,String goalState) {
        System.out.print("Enter 1 to search using DFS or 2 to BFS :");
        var node = new Node(initialState);
        map.putIfAbsent(node,new ArrayList<>());
        path.add(node);
        goal =goalState;
        if(new Scanner(System.in).nextLine().equals("1"))
        DF();
        else
        BF();
    }
    private boolean isGoal(Node node){
        if(node.state.equals(goal)){
            System.out.println(node);
            System.out.println("Path size = "+path.size());
            System.out.println("Node visited = "+visited.size());
            System.out.println("Number of node explored = "+nodeExplored);
            System.out.println("Depth = "+depth);
            return true;
        }
        return false;
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
    private void addChildren(List<Node>list,Node node){
        directions(list, generate("up",node.state), generate("down",node.state));
        directions(list, generate("left",node.state), generate("right",node.state));
    }

    private void directions(List<Node> list, String up, String down) {
        if(up !=null){var root = new Node(up);list.add(root);map.putIfAbsent(root,new ArrayList<>());}
        if(down!=null){var root = new Node(down);list.add(root);map.putIfAbsent(root,new ArrayList<>());}
    }

    private void DF(){
        Stack<Node> stack = new Stack<>();
        stack.push((Node)map.keySet().toArray()[0]);
            while (!stack.empty()){
                var current = stack.pop();
                if(visited.contains(current.state))continue;
                if(isGoal(current))return;
                path.add(current);
                visited.add(current.state);
                addChildren(map.get(current),current);
                for(var i:map.get(current)) {
                    if (!visited.contains(i.state)) {
                        stack.push(i);
                        nodeExplored++;
                    }
                }
        }
        System.out.println("The given initial state is impossible to solve");
    }
    private void BF(){
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(path.get(0));
        while (!queue.isEmpty()){
            var current = queue.remove();
            if(visited.contains(current.state))continue;
            if(isGoal(current))return;
            path.add(current);
            visited.add(current.state);
            addChildren(map.get(current),current);
            for(var i: map.get(current))
                if(!visited.contains(i.state)) {
                    queue.add(i);
                    nodeExplored++;
                }
        }
        System.out.println("The initial state is impossible to solve");
    }

}
