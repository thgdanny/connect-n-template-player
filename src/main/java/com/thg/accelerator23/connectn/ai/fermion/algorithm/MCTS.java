package com.thg.accelerator23.connectn.ai.fermion.algorithm;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thg.accelerator23.connectn.ai.fermion.board.BoardAnalyser;
import com.thg.accelerator23.connectn.ai.fermion.board.GameState;
import com.thg.accelerator23.connectn.ai.fermion.renderer.ConsoleRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MCTS {
    Board board;

    public MCTS(Board board) {
        this.board = board;
    }

    public Node selectHighestUCTNode(Node parentNode) {
        ConsoleRenderer obj = new ConsoleRenderer();
            Node node = parentNode;
            while (node.getChildren().size() != 0) {
                node = Collections.max(node.getChildren(), Comparator.comparing(c -> c.getUCTValue(parentNode)));
            }
        return node;
    }

    public Node expand(Node promisingNode, ArrayList<Integer> movesMade) {
        BoardAnalyser analyser = new BoardAnalyser(promisingNode.getState().getBoard().getConfig());

        if (!analyser.calculateGameState(promisingNode.getState().getBoard()).isEnd() && !promisingNode.hasChildren()) {
            promisingNode.generateChildrenNodes();

        }
        Node newNode = randomChildNode(promisingNode);
        movesMade.add(newNode.getMove());
        return newNode;
    }

    public int play(Counter counter) {

        Tree tree = new Tree(counter);
        Node rootNode = tree.getRoot();

        rootNode.getState().setBoard(this.board);
        rootNode.getState().setRootCounter(counter);

        long currentTime = System.currentTimeMillis();

        while (System.currentTimeMillis()-currentTime < 8500) {
            ArrayList<Integer> movesMade = new ArrayList<>();

            Node promisingNode = selectHighestUCTNode(rootNode);

            BoardAnalyser analyser = new BoardAnalyser(promisingNode.getState().getBoard().getConfig());

            Node nodeToExplore = expand(promisingNode,movesMade);

            ConsoleRenderer obj = new ConsoleRenderer();

                Counter results = simulateMoves(nodeToExplore, rootNode.getState().getCounter());

                backpropagation(nodeToExplore, results, rootNode.getState().getCounter());
            }

        Node winnerNode = tree.getTrueRoot().childMostVisits();

        tree.setRoot(winnerNode);
        return winnerNode.getMove();
    }

    private Counter simulateMoves(Node nodeToExplore, Counter rootCounter) {
        BoardAnalyser boardChecker =  new BoardAnalyser(new GameConfig(10,8,4));
        GameState gameState = boardChecker.calculateGameState(nodeToExplore.getState().getBoard());
        ConsoleRenderer obj = new ConsoleRenderer();

        Node interNode = new Node(nodeToExplore);
        State interState = interNode.getState().clone();

        if (rootCounter.getOther() == gameState.getWinner()) { //opponent has won
            nodeToExplore.getParent().getState().setNodeWins(Integer.MIN_VALUE);
            return gameState.getWinner();
        }

        while(!gameState.isEnd()){
            try {
                int move = interNode.playRandomMove(interState.getBoard());

                interState.setBoard(new Board(interState.getBoard(), move, interState.getCounter()));

                gameState = boardChecker.calculateGameState(interState.getBoard());

                interState.invertCounter();
            } catch(InvalidMoveException e) {
                System.out.println("Sim fail");
                throw new RuntimeException(e);
            }
        }
        return gameState.getWinner();
    }

    public Node randomChildNode(Node rootNode) {
        if(rootNode.getChildren().size() != 0) {
            Random r = new Random();
            return rootNode.getChildren().get(r.nextInt(rootNode.getChildren().size()));
        }
        return rootNode;
    }

    public void backpropagation(Node endNode, Counter simWinnerNode, Counter rootCounter) {
        Node upNode = endNode;
        while (upNode != null) {
            upNode.getState().addVisit();
            if (upNode.getState().getCounterOpposite() == simWinnerNode) {
                upNode.getState().addWin();
            }
            upNode = upNode.getParent();
        }
    }

}

