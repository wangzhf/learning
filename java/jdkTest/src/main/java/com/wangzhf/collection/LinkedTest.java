package com.wangzhf.collection;

/**
 * 倒叙输出链表值
 */
public class LinkedTest {

    public static void main(String[] args) throws Exception {
        Node parentNode = null;
        Node startNode = null;
        for(int i=0; i<5; i++){
            if(parentNode == null ){
                parentNode = new Node();
                parentNode.setValue(i);
                startNode = parentNode;
            }else{
                Node node = new Node();
                node.setValue(i);
                parentNode.addNode(node);
                parentNode = node;
            }
        }

        printReverse(startNode);

    }

    public static void printReverse(Node node){
        if(node.tail != null){
            printReverse(node.tail);
        }
        System.out.println(node.getValue());
    }

}

class Node {
    Node head;
    Node tail;
    Integer value;

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public Node getTail() {
        return tail;
    }

    public void setTail(Node tail) {
        this.tail = tail;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void addNode(Node node) throws Exception {
        if(this.tail != null){
            throw new Exception("The Node has next node.");
        }
        node.head = this;
        this.tail = node;
    }


}
