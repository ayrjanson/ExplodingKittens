package com.example.explodingkittens;

import android.util.Log;
import android.widget.TextView;


import com.example.explodingkittens.ekActionMessage.EKPlayCardAction;
import com.example.explodingkittens.infoMessage.CARDTYPE;
import com.example.explodingkittens.infoMessage.Card;
import com.example.explodingkittens.infoMessage.EKState;
import com.example.gameframework.LocalGame;
import com.example.gameframework.actionMessage.GameAction;
import com.example.gameframework.players.GamePlayer;
import com.example.gameframework.utilities.Logger;


public class EKLocalGame extends LocalGame {
    // instance vars for current and previous states
    //public EKState state;
    //private EKState previousState;
    //private GameAction action;
    public TextView logView = null;

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
        if (action instanceof EKPlayCardAction) {
            EKPlayCardAction at = (EKPlayCardAction) action;
            CARDTYPE type = at.type;
            EKState currentState = (EKState) state;
            switch (type) {
                case MELON:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.MELON, currentState.deck.get(currentState.playerTurn), currentState.discard)) {
                        Logger.log("LocalGame", "Playing a Melon Card");
                        return true;
                    }
                    break;
                case BEARD:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.BEARD, currentState.deck.get(currentState.playerTurn), currentState.discard)) {
                        Logger.log("LocalGame", "Playing a Beard Card");
                        return true;
                    }
                    break;
                case POTATO:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.POTATO, currentState.deck.get(currentState.playerTurn), currentState.discard)) {
                        Logger.log("LocalGame", "Playing a Potato Card");
                        return true;
                    }
                    break;
                case TACO:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.TACO, currentState.deck.get(currentState.playerTurn), currentState.discard)) {
                        Logger.log("LocalGame", "Playing a Taco Card");
                        return true;
                    }
                    break;
                case ATTACK:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.ATTACK, currentState.deck.get(currentState.playerTurn), currentState.discard)) {
                        Logger.log("LocalGame", "Playing a Attack Card");
                        return true;
                    }
                    break;
                case SHUFFLE:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.SHUFFLE, currentState.deck.get(currentState.playerTurn), currentState.discard)) {
                        Logger.log("LocalGame", "Playing a Shuffle Card");
                        return true;
                    }
                    break;
                case FAVOR:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.FAVOR, currentState.deck.get(currentState.playerTurn), currentState.discard)) {
                        Logger.log("LocalGame", "Playing a Favor Card");
                        return true;
                    }
                    break;
                case SKIP:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.SKIP, currentState.deck.get(currentState.playerTurn), currentState.discard)) {
                        Logger.log("LocalGame", "Playing a Skip Card");
                        return true;
                    }
                    break;
                case SEEFUTURE:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.SEEFUTURE, currentState.deck.get(currentState.playerTurn), currentState.discard)) {
                        Logger.log("LocalGame", "Playing a SeeFuture Card");
                        return true;
                    }
                    break;
                case NOPE:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.NOPE, currentState.deck.get(currentState.playerTurn), currentState.discard)) {
                        Logger.log("LocalGame", "Playing a Nope Card");
                        return true;
                    }
                    break;
                case DEFUSE:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.DEFUSE, currentState.deck.get(currentState.playerTurn), currentState.discard)) {
                        Logger.log("LocalGame", "Playing a Defuse Card - this shouldn't be a thing");
                        return true;
                    }
                    break;
                case EXPLODE:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.EXPLODE, currentState.deck.get(currentState.playerTurn), currentState.discard)) {
                        Logger.log("LocalGame", "Playing a Explode Card - this shouldn't be a thing");
                        return true;
                    }
                    break;
                case ENDTURN:
                    ((EKState) state).endTurn(turn, ((EKState) state).DRAWCARD);
                    ((EKState) state).nextPlayer(((EKState) state).getPlayerTurn());
                    Logger.log("makeMove", "Ended Turn, current player now is" + ((EKState) state).playerTurn);
                    return true;
                case DRAW:
                    Card drawn = ((EKState) state).takeFromDraw();
                    Logger.log("LocalGame", "Drawing a Card");
                    if (((EKState) state).playCard(((EKState) state).playerTurn, drawn.getType(), ((EKState) state).draw, ((EKState) state).deck.get(((EKState) state).getPlayerTurn()))) {
                        //This turn is a draw card turn
                        //End turn using the draw card excuse - resume play as normal
                        ((EKState) state).nextPlayer(((EKState) state).getPlayerTurn());
                        return true;
                    }
                    break;
                default:
                    Log.d("Invalid Action",
                            "Action provided was an invalid action");
                    return false;
            }


        }
        return false;
    }
}



