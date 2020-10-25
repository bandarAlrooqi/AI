package com.codeWithBandar;

import java.util.*;

public class A_StarToSolve3dCube {
    public static class Node{
        String state;
        Node(String state){
            this.state=state;
        }
        public String toString(){
            StringBuilder m = new StringBuilder();
            for (int i = 1; i <=state.length() ; i++) {
                if(i%(3*3)==0) m.append("\n");
                m.append(state.charAt(i - 1));
            }
            return m.toString();
        }
    }
    Map<Node, ArrayList<Node>> map ;
    Set<String> visited ;
    String goal;
    A_StarToSolve3dCube(String initial,String goal){
        this.goal=goal;
        Node node = new Node(initial);

        map = new HashMap<>();
        visited = new HashSet<>();

    }
    private void generate(List<Node>list,Node node){

    }

}
