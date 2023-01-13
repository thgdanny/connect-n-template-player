package com.thg.accelerator23.connectn.ai.fermion.algorithm;

import com.thehutgroup.accelerator.connectn.player.Counter;

public class Tree {
    private Node root;
    private Node trueRoot;

    public Node getTrueRoot() {
        return trueRoot;
    }

    public Tree(Counter counter){
        Node first = new Node(counter);
        this.root = first;
        this.trueRoot = first;
    }
    public Tree(Node root) {
        this.root = root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return this.root;
    }
}
