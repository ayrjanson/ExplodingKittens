package com.example.explodingkittens.views;

import com.example.gameframework.LocalGame;
import com.example.gameframework.actionMessage.GameAction;
import com.example.gameframework.players.GamePlayer;

public class EKLocalGame extends LocalGame {
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {

    }

    @Override
    protected boolean canMove(int playerIdx) {
        return false;
    }

    @Override
    protected String checkIfGameOver() {
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        return false;
    }
}
