package com.example.explodingkittens;

import android.util.Log;

import com.example.explodingkittens.ekActionMessage.EKAttackAction;
import com.example.explodingkittens.ekActionMessage.EKCatCardAction;
import com.example.explodingkittens.ekActionMessage.EKEndTurnAction;
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
import com.example.gameframework.utilities.Logger;

import java.util.Collections;


public class EKLocalGame extends LocalGame {
    // instance vars for current and previous states
    //public EKState state;
    //private EKState previousState;
    //private GameAction action;


    // BASIC CONSTRUCTOR

    public EKLocalGame() {
        super();
        super.state = new EKState(4); //game with 4 players
        //state = (EKState) super.state;
        //this.previousState = null;
    }

    // CONSTRUCTOR WITH LOADED EK GAME STATE
    public EKLocalGame(EKState ekgamestate) {
        super();
        super.state = new EKState(ekgamestate);
        //state = (EKState) super.state;
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
        if (((EKState) state).getPlayerTurn() == playerIdx) return true;
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
            if (((EKState) state).gameOver() != -1) {
                return "Player " + ((EKState) state).gameOver() + " wins!";
            }
        }
        return null; // Game not over
    }


    @Override
    protected boolean makeMove(GameAction action) {
        int turn = ((EKState) state).playerTurn;



        if (action instanceof EKNopeAction) {
            if(((EKState) state).playCard(((EKState) state).playerTurn, CARDTYPE.NOPE, ((EKState) state).deck.get(((EKState) state).playerTurn), ((EKState) state).discard)){
                //go do the stuff :)
                return true;
            }
        }

        else if (action instanceof EKFavorAction) {
            if(((EKState) state).playCard(((EKState) state).getPlayerTurn(), CARDTYPE.FAVOR, ((EKState) state).deck.get(((EKState) state).playerTurn), ((EKState) state).discard)){
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
            if(((EKState) state).playCard(((EKState) state).playerTurn, CARDTYPE.SKIP, ((EKState) state).deck.get(((EKState) state).playerTurn), ((EKState) state).discard)) {
                ((EKState) state).endTurn(turn, ((EKState) state).SKIPTURN);
                ((EKState) state).nextPlayer(((EKState) state).getPlayerTurn());
                return true;
            }
        }

        else if (action instanceof EKAttackAction) {
            if(((EKState) state).playCard(((EKState) state).playerTurn, CARDTYPE.ATTACK, ((EKState) state).deck.get(((EKState) state).playerTurn), ((EKState) state).discard)){
                //Send message that next player need to take two turns
                //End the current player's turn - attack end turn excuse
                return true;
            }
        }

        else if (action instanceof EKShuffleAction) {
            if(((EKState) state).playCard(((EKState) state).playerTurn, CARDTYPE.SHUFFLE, ((EKState) state).deck.get(((EKState) state).playerTurn), ((EKState) state).discard)){
                //Shuffle the draw deck
                Collections.shuffle(((EKState) state).draw);
                return true;
            }
        }

        else if (action instanceof EKSeeFutureAction) {
            if(((EKState) state).playCard(((EKState) state).playerTurn, CARDTYPE.SEEFUTURE, ((EKState) state).deck.get(((EKState) state).playerTurn), ((EKState) state).discard)){
                //Display the first three cards in the draw pile in the player's deck
                //All other cards have no display on them
                //Once ok, turn the player's deck back to normal
                return true;
            }
        }

        else if (action instanceof EKEndTurnAction) {
            //if(((EKState) state).playCard(turn, CARDTYPE.SEEFUTURE, ((EKState) state).deck.get(turn), ((EKState) state).discard)) {
                ((EKState) state).endTurn(turn, ((EKState) state).DRAWCARD);
                ((EKState) state).nextPlayer(((EKState) state).getPlayerTurn());
                Logger.log("makeMove","Ended Turn, current player now is" + ((EKState) state).playerTurn);
                return true;
            //}
        }

        else if (action instanceof EKCatCardAction) {
            return true;
        }

        else {
            Card drawn = ((EKState) state).takeFromDraw();
            if(((EKState) state).playCard(((EKState) state).playerTurn, drawn.getType(), ((EKState) state).draw, ((EKState) state).deck.get(((EKState) state).getPlayerTurn()))) {
                //This turn is a draw card turn
                //End turn using the draw card excuse - resume play as normal
                ((EKState) state).nextPlayer(((EKState) state).getPlayerTurn());
                return true;
            }
        }

        Log.d("Invalid Action",
                "Action provided was an invalid action");
        return false;
    }
}
