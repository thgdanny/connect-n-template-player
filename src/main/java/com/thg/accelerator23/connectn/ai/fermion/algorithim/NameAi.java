package com.thg.accelerator23.connectn.ai.fermion.algorithim;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;

public class NameAi extends Player {

  public NameAi(Counter counter) {
    //TODO: fill in your name here
    super(counter, NameAi.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it

//    localBoardAnalyser moveChecker = new localBoardAnalyser();
//    int randomColumn = 4;
//
//
//    while(!moveChecker.checkForFullColumn(randomColumn,board)){
//      randomColumn = (int) (Math.random() * (10));
//    }
//
//
//
//
////    return validMove.get(new Random().nextInt(validMove.size())
//
      MCTS algo = new MCTS(board);
      return algo.actualPlay(getCounter());

  }
}

