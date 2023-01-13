package com.thg.accelerator23.connectn.ai.fermion.algorithm;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;

public class CONNECTCONNECTCONNECTCONNECT extends Player {

  public CONNECTCONNECTCONNECTCONNECT(Counter counter) {

    super(counter, CONNECTCONNECTCONNECTCONNECT.class.getName());
  }

  @Override
  public int makeMove(Board board) {
      MCTS algo = new MCTS(board);
      return algo.play(getCounter());
  }
}

