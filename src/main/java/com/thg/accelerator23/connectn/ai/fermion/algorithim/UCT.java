package com.thg.accelerator23.connectn.ai.fermion.algorithim;

import java.util.Collections;
import java.util.Comparator;

public class UCT {

    public static double getUCTValue(State state, int totalVisits) {
        if (state.getNodeVisits() == 0) {
            return Integer.MAX_VALUE;
        } else {
            return (((double) state.getNodeWins() / (state.getNodeVisits())) + 1.41 * Math.sqrt(Math.log(totalVisits) / (double) state.getNodeVisits()));
        }
    }

    public static Node highestChildUCTNode(Node node) {
        int totalVisits = node.getState().getNodeVisits();

        return Collections.max(node.getChildren(),
                Comparator.comparing(c->getUCTValue(c.getState(),totalVisits)));


    }
}
