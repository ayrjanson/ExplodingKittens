package com.example.explodingkittens;

import android.util.Log;

import com.example.explodingkittens.ekActionMessage.EKAttackAction;
import com.example.explodingkittens.ekActionMessage.EKFavorAction;
import com.example.explodingkittens.ekActionMessage.EKNopeAction;
import com.example.explodingkittens.ekActionMessage.EKSeeFutureAction;
import com.example.explodingkittens.ekActionMessage.EKShuffleAction;
import com.example.explodingkittens.infoMessage.CARDTYPE;
import com.example.explodingkittens.infoMessage.EKState;
import com.example.gameframework.LocalGame;
import com.example.gameframework.actionMessage.GameAction;
import com.example.gameframework.players.GamePlayer;

public class EKLocalGame extends LocalGame {

    public EKLocalGame(EKState gameState) {
        super();
        super.state = new EKState(gameState);
    }

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
        int turn = currentState.getPlayerTurn();
        if (action instanceof EKNopeAction) {
            currentState.playCard(turn, CARDTYPE.NOPE, currentState.deck.get(turn), currentState.discard);
            return EKNopeAction(action.getPlayer());
        }
        else {
            this.previousState = new EKState(this.currentState);
        }

        if(action instanceof EKFavorAction) { //repeat for all other card actions
            currentState.playCard(turn, CARDTYPE.FAVOR, currentState.deck.get(turn), currentState.discard);
            return EKFavorAction(action.getPlayer(), (EKFavorAction));
        }

        if (action instanceof EKAttackAction) {
            currentState.playCard(turn, CARDTYPE.ATTACK, currentState.deck.get(turn), currentState.discard);
            return EKAttackAction(action.getPlayer());
        }

        if (action instanceof EKShuffleAction) {
            currentState.playCard(turn, CARDTYPE.SHUFFLE, currentState.deck.get(turn), currentState.discard);
            return EKShuffleAction(action.getPlayer());
        }

        else if (action instanceof EKSeeFutureAction) {
            currentState.playCard(turn, CARDTYPE.SEEFUTURE, currentState.deck.get(turn), currentState.discard);
            return EKSeeFutureAction(action.getPlayer());
        }

        Log.d("Invalid Action",
                "Action provided was an invalid action");
        return false;
    }
}
