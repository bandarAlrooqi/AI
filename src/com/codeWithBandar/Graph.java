package com.codeWithBandar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private static class Node{
        String label;
        public Node(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }
    Map<String,Node> stringNodeMap = new HashMap<>();
    Map<Node, List<Node>> nodeListMap = new HashMap<>();
    public void addNode(String label){
        if(label==null)
            return;
            var node = new Node(label);
            stringNodeMap.putIfAbsent(label,node);
            nodeListMap.putIfAbsent(node,new ArrayList<>());

    }
    public void removeNode(String label){
        var node = stringNodeMap.get(label);
        if(node == null)return;
        stringNodeMap.remove(label);
        nodeListMap.remove(node);
    }
    public void addEdges(String from , String to){
        var nodeFrom = stringNodeMap.get(from);
        var nodeTo = stringNodeMap.get(to);
        if(nodeFrom == null || nodeTo == null)return;
        if(nodeListMap.get(nodeFrom).contains(nodeTo))return;
        nodeListMap.get(nodeFrom).add(nodeTo);
    }
    public void removeEdges(String from, String to){
        var nodeFrom = stringNodeMap.get(from);
        var nodeTo = stringNodeMap.get(to);
        if(nodeFrom == null || nodeTo == null)return;
        nodeListMap.get(nodeFrom).remove(nodeTo);
    }
    public void IDS(String root,String goal,int maxDepth){
        if(maxDepth<=0)return;

         var nodeRoot = stringNodeMap.get(root);
         var nodeGoal = stringNodeMap.get(goal);

         if(nodeRoot == null || nodeGoal == null)
             return;


        //get the branch factor of a tree
        int branchFactor =0;
        for(var i : nodeListMap.values())
            branchFactor =Math.max(i.size(),branchFactor);

        int depth = 0;

        Map<Node,List<Node>> temp = new HashMap<>();
        while (depth<=maxDepth){
            if(nodeRoot == nodeGoal) {
                System.out.println("Found at level "+depth);
                return;
            }
            for(var i : nodeListMap.get(nodeRoot))

            depth++;
        }


    }
    private List<Node> DF(Node root,Map<Node,List<Node>> map){
        return null;
    }


    public void print(){
        for(var i : stringNodeMap.values())
            if(!nodeListMap.get(i).isEmpty())
                System.out.println(i+" is connected to "+nodeListMap.get(i));
    }
}
