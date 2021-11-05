package com.example.explodingkittens;

import android.util.Log;

import com.example.explodingkittens.ekActionMessage.EKAttackAction;
import com.example.explodingkittens.ekActionMessage.EKFavorAction;
import com.example.explodingkittens.ekActionMessage.EKNopeAction;
import com.example.explodingkittens.ekActionMessage.EKSeeFutureAction;
import com.example.explodingkittens.ekActionMessage.EKShuffleAction;
import com.example.explodingkittens.infoMessage.CARDTYPE;
import com.example.explodingkittens.infoMessage.Card;
import com.example.gameframework.LocalGame;
import com.example.gameframework.actionMessage.GameAction;
import com.example.gameframework.players.GamePlayer;
import java.util.ArrayList;
import com.example.explodingkittens.infoMessage.EKState;

public class EKLocalGame extends LocalGame {
    // instance vars for current and previous states
    private EKState currentState;
    private EKState previousState;

    //constructor

    public EKLocalGame() {
        this.currentState = new EKState(4); //game with 4 players
        this.previousState = null;
    }

    //send updated state to player
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        EKState gameCopy = new EKState(currentState);
        p.sendInfo(gameCopy);
    }

    //checks if player can play card
    @Override
    protected boolean canMove(int playerId) {
        return playerId == currentState.getTurn();
    }

    //checks if game has ended
    @Override
    protected String checkIfGameOver() {
        int outPlayers = 0;
        for (int i = 0; i < players.length; i++) {
            if (checkPlayerHand(currentState.getPlayersHand().get(i), cardTypeValue:0)==-1){
                return "Congrats" + playerNames[i] + "! You've Won";
            }
        }
        return null;
    }


    //ADD MAKE MOVE HERE
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
            return EKFavorAction(action.getPlayer(),(EKFavorAction));
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