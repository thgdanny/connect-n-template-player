package com.thg.accelerator23.connectn.ai.fermion.algorithim;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.GameConfig;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//class MCTSTest {
//
//    Board board;
//
//    @Test
//    public void simulateMoves() {
//        board = new Board(new GameConfig(10,8,4));
//
//        try {
//            for (int i = 0; i < 2; i++) {
//                board = new Board(board,3, Counter.O);
//            }
//        } catch (InvalidMoveException e) {
//            throw new RuntimeException(e);
//        }
//        MCTS algo = new MCTS(board);
//        Node parentNode = new Node();
//
//        Node node = new Node(parentNode,3,Counter.O);
//
//        Counter winningCounter = algo.simulateMoves(node);
//
//        Assertions.assertEquals(Counter.O,winningCounter);
//    }

//}