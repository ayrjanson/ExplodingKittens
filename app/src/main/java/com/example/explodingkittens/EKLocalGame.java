package com.example.explodingkittens;

import android.util.Log;

import com.example.explodingkittens.ekActionMessage.EKAttackAction;
import com.example.explodingkittens.ekActionMessage.EKFavorAction;
import com.example.explodingkittens.ekActionMessage.EKNopeAction;
import com.example.explodingkittens.ekActionMessage.EKSeeFutureAction;
import com.example.explodingkittens.ekActionMessage.EKShuffleAction;
import com.example.explodingkittens.ekActionMessage.EKSkipAction;
import com.example.explodingkittens.infoMessage.CARDTYPE;
import com.example.explodingkittens.infoMessage.Card;
import com.example.explodingkittens.infoMessage.EKState;
import com.example.gameframework.LocalGame;
import com.example.gameframework.actionMessage.GameAction;
import com.example.gameframework.players.GamePlayer;



public class EKLocalGame extends LocalGame {
    // instance vars for current and previous states
    public EKState currentState;
    //private EKState previousState;
    //private GameAction action;
    //private int turn = currentState.playerTurn;

    // BASIC CONSTRUCTOR

    public EKLocalGame() {
        super();
        super.state = new EKState(4); //game with 4 players

        //this.previousState = null;
    }

    // CONSTRUCTOR WITH LOADED EK GAME STATE
    public EKLocalGame(EKState ekgamestate) {
        super();
        super.state = new EKState(ekgamestate);
    }

    //send updated state to player
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        EKState gameCopy = new EKState((EKState) state);
        p.sendInfo(gameCopy);
    }

    //checks if player can play card
    @Override
    protected boolean canMove(int playerIdx) {
        if (currentState.playerTurn == playerIdx) return true;
        return false;
    }

    //checks if game has ended
    @Override
    protected String checkIfGameOver() {
        // Do a check for all computer players
        // Have the computer players continue playing each other

        //Change 1: The state to say which player remains, if only one remains - in EKState
            // Returns the int of the player that remains (0-3), -1 if the game isn't over
        //Change 2: Look up the player name based on which player remains - string
        //Change 3: Print out the specific player name that won
        int outPlayers = 0;
        for (int i = 0; i < players.length; i++) {
            if (currentState.gameOver() != -1) {
                return "Player " + currentState.gameOver() + " wins!";
            }
        }
        return null; // Game not over
    }


    @Override
    protected boolean makeMove(GameAction action) {
        if (action instanceof EKNopeAction) {
            if(currentState.playCard(currentState.playerTurn, CARDTYPE.NOPE, currentState.deck.get(currentState.playerTurn), currentState.discard)){
                //go do the stuff :)
                return true;
            }
        }

        else if (action instanceof EKFavorAction) {
            if(currentState.playCard(currentState.playerTurn, CARDTYPE.FAVOR, currentState.deck.get(currentState.playerTurn), currentState.discard)){
                //Prompt the user to choose which player to steal a card from
                //Ask the user to choose which type of card they would like to steal a card from by
                //displaying cards in their deck
                //If the card type selected matches what the user has
                    // Delete the card from the selected user's deck
                    // Add the card to the asker's deck
                    // Resume play - end turn by asker drawing card
                // If the card isn't in the selected user's deck
                    // Change nothing; resume play
                    // Resume play - asker draws card
                return true;
            }
        }

        else if (action instanceof EKSkipAction) {
            if(currentState.playCard(currentState.playerTurn, CARDTYPE.SKIP, currentState.deck.get(currentState.playerTurn), currentState.discard)) {
                return true;
            }
        }

        else if (action instanceof EKAttackAction) {
            if(currentState.playCard(currentState.playerTurn, CARDTYPE.ATTACK, currentState.deck.get(currentState.playerTurn), currentState.discard)){
                //Send message that next player need to take two turns
                //End the current player's turn - attack end turn excuse
                return true;
            }
        }

        else if (action instanceof EKShuffleAction) {
            if(currentState.playCard(currentState.playerTurn, CARDTYPE.SHUFFLE, currentState.deck.get(currentState.playerTurn), currentState.discard)){
                //Shuffle the draw deck
                return true;
            }
        }

        else if (action instanceof EKSeeFutureAction) {
            if(currentState.playCard(currentState.playerTurn, CARDTYPE.SEEFUTURE, currentState.deck.get(currentState.playerTurn), currentState.discard)){
                //Display the first three cards in the draw pile in the player's deck
                //All other cards have no display on them
                //Once ok, turn the player's deck back to normal
                return true;
            }
        }

        else {
            Card drawn = currentState.takeFromDraw();
            if(currentState.playCard(currentState.playerTurn, drawn.getType(), currentState.draw, currentState.deck.get(currentState.playerTurn))) {
                //This turn is a draw card turn
                //End turn using the draw card excuse - resume play as normal
                return true;
            }
        }

        Log.d("Invalid Action",
                "Action provided was an invalid action");
        return false;
    }
}
