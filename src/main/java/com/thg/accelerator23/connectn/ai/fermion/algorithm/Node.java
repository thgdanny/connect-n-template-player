package com.thg.accelerator23.connectn.ai.fermion.algorithm;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.fermion.board.LocalBoardAnalyser;

import java.util.*;

public class Node {

    private State state;
    private Node parent;
    private int move;
    private final List<Node> children = new ArrayList<>();

    public Node(Counter counter){
        this.state = new State(counter);
    }

    public Node(Node tempNode) {
        this.state = tempNode.getState();
        this.parent = tempNode.getParent();
        this.move = tempNode.getMove();
    }

    public Node(Node parent, int move, Counter counter) {
        this.state = new State(counter);
        Board b = parent.state.getBoard();
        this.state.setBoard(b);
        this.parent = parent;
        this.move = move;
    }

    public State getState() {
        return state;
    }

    public int getMove() {
        return move;
    }

    public List<Node> getChildren() {
        return children;
    }

    public boolean hasChildren() {
        return this.children.size() != 0;
    }

    public Node getParent() { return this.parent; };

    public Node childMostVisits() {
        if (this.hasChildren()) {
            Node mostNode = this.getChildren().get(0);
            for (Node node : this.getChildren()) {
                if (node.getState().getNodeVisits() > mostNode.getState().getNodeVisits()) {
                    mostNode = node;
                }
            }
            return mostNode;
        }
        return this;
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public void generateChildrenNodes() {
        for (int i = 0; i < 10; i++) {
            LocalBoardAnalyser lba = new LocalBoardAnalyser(this.state.getBoard());
            boolean[] fullColumns = lba.fullColumns();

            try {
                if(!fullColumns[i]) {
                    Board board = new Board(this.getState().getBoard(), i, this.getState().getCounter());
                    Node node = new Node(this, i, this.getState().getCounterOpposite());
                    node.getState().setBoard(board);
                    addChild(node);
                }
            } catch (InvalidMoveException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public double getUCTValue(Node rootNode) {
        if(this.state.getNodeVisits()==0) {
            return Integer.MAX_VALUE;
        } else {
            return (((double) this.state.getNodeWins()/(double) this.state.getNodeVisits())+1.41*Math.sqrt(Math.log(rootNode.state.getNodeVisits())/(double) this.state.getNodeVisits()));
        }
    }

    public int playRandomMove(Board board) {
        LocalBoardAnalyser lba = new LocalBoardAnalyser(board);
        boolean[] fullColumns = lba.fullColumns();
        Random r = new Random();
        int random = -1;
        boolean hasFound = false;
        while(!hasFound) {
            random = (r.nextInt(10));
            if(!fullColumns[random])
                hasFound = true;
        }
        return random;
    }

}
