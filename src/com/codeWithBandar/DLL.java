package com.codeWithBandar;

public class DLL {
    private static class Node{
        int value ;
        Node next;
        Node previous;
        Node(int value){
            this.value=value;
        }
        public String toString(){return value+"";}
    }
    private Node head,last;
    private int size;
    public void add(int value){
        if(head == null) {
            head = last = new Node(value);
            size++;
            return;
        }
        last.next = new Node(value);
        Node temp = last;
        last = last.next;
        last.previous =temp;
        size++;
    }
    public void delete(int value){
        var temp = head;
        while (temp!=null && temp.value!=value)
            temp =temp.next;

        if(temp == null) {System.out.println(value+ " does not exist ");return;}
        if(temp.previous == null) {
            head = head.next;
            head.previous = null;
            size--;
            return;
        }
        if(temp.next == null){
            last = last.previous;
            last.next =null;
            size--;
            return;
        }
        temp.previous.next =temp.next;
        size--;
    }
    public int[] toArray(){
        if(size ==0 )return null;
        var array = new int[size];
        var temp = head;
        int i =0;
        while (temp!=null){
            array[i++] =temp.value;
            temp = temp.next;
        }
        return array;
    }

    public void print(){
        var temp = head;
        while (temp!=null) {
            System.out.print(temp.value + "  ");
            temp = temp.next;
        }
    }

}
