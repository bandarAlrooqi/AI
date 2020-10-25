package com.codeWithBandar;

import java.util.*;

public class IDS {
    private static class Node{
        String label ;
        public Node(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }
    Map<String,Node> nodes = new HashMap<>();
    Map<Node, List<Node>> connection = new HashMap<>();
    public void addNode(String label){
        var node = new Node(label);
        nodes.putIfAbsent(label,node);
        connection.putIfAbsent(node,new ArrayList<>());
    }
    public void addEdge(String from, String to){
        var nodeFrom = nodes.get(from);
        var nodeTo = nodes.get(to);
        if(nodeFrom == null || nodeTo == null)
            return;
        connection.get(nodeFrom).add(nodeTo);
    }
//    public void search(String root,String goal, int maxDepth){
//        Set<Node> visited = new HashSet<>();
//        Stack<Node> stack = new Stack<>();
//
//        stack.push(nodes.get(root));
//
//        while (!stack.empty()){
//            var current = stack.pop();
//            if(current.label.equals(goal)){
//                System.out.println("Node found");
//            }
//            visited.add(current);
//
//            for(var subNode :connection.get(current))
//                if(!visited.contains(subNode))
//                    stack.push(subNode);
//        }
//    }
    public String search(String from , String goal , int max){
        if(from.equals(goal)){
            System.out.println("Found depth "+max);
            return from;
            }
        if(max>0){
            for(var i :connection.get(nodes.get(from))){
                System.out.println(i);
                return search(i.label,goal,max-1);
            }
        }
        return null;
    }
}
