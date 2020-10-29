package com.codeWithBandar;

import java.util.*;

public class AI {

    Map<Node, List<Node>> map; // storing the node and its children
    Set<String> visited; // set of visited node to help not visiting a node twice
    Node initial;

    public AI(String root, String goal){

        Node.goal = goal;
        initial = new Node(null,root);

        map= new HashMap<>();
        visited = new HashSet<>();

        map.putIfAbsent(initial,new ArrayList<>());

    }

    public void DFS(){
        DFS(initial);
    }
    public void BFS(){BFS(initial);}
    public void AStar(){AStar(initial);}
    private void AStar(Node initial){
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(); // initializing priority queue of nodes
        priorityQueue.add(initial); // adding our initial state to the queue
        while (!priorityQueue.isEmpty()){
            var current = priorityQueue.remove(); // removing the node from the queue to be visited and checked

            if(visited.contains(current.state))
                continue;

            visited.add(current.state); // adding the node to visited listed in order to prevent visiting it again
            Node.numberOfNodeVisited++;
            if(current.isGoal())
                return; // checking if the current node is the goal

            current.addChildren(map,visited,map.get(current),current); // if it is not the goal then generate children

            for(var i : map.get(current)){ // adding the generated nodes to our queue
                if(visited.contains(i.state)) // if the node has been visited before then just ignore the process and continue
                    continue;
                priorityQueue.add(i);
            }
        }
        // if we get here then the state is impossible to solve .. read more about impossible 8-puzzle https://www.geeksforgeeks.org/check-instance-8-puzzle-solvable/
        System.out.println("The given initial state is impossible to sole !");
    }
    private void DFS(Node initial){
        Stack<Node> stack = new Stack<>();
        stack.push(initial);
        while (!stack.empty()){
            var current = stack.pop();
            if(visited.contains(current.state))continue ;
            Node.numberOfNodeVisited++;
            if(current.isGoal())return;
            visited.add(current.state);
            current.addChildren(map,visited,map.get(current),current);
            for(var i:map.get(current)) {
                if (!visited.contains(i.state)) {
                    stack.push(i);
                }
            }
        }
        System.out.println("The given initial state is impossible to solve");
    }
    private void BFS(Node initial){
        int j =0;
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(initial);
        while (!queue.isEmpty()){
            var current = queue.remove();
            if(visited.contains(current.state))continue;
            j++;
            System.out.println(j);
            Node.numberOfNodeVisited++;
            if(current.isGoal())return;
            visited.add(current.state);
            current.addChildren(map,visited,map.get(current),current);
            for(var i: map.get(current))
                if(!visited.contains(i.state)) {
                    queue.add(i);
                }
        }
        System.out.println("The initial state is impossible to solve");
    }





}
