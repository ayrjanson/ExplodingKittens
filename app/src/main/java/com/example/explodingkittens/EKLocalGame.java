package com.example.explodingkittens;

import android.util.Log;

import com.example.explodingkittens.ekActionMessage.EKAttackAction;
import com.example.explodingkittens.ekActionMessage.EKCatCardAction;
import com.example.explodingkittens.ekActionMessage.EKEndTurnAction;
import com.example.explodingkittens.ekActionMessage.EKFavorAction;
import com.example.explodingkittens.ekActionMessage.EKMoveAction;
import com.example.explodingkittens.ekActionMessage.EKNopeAction;
import com.example.explodingkittens.ekActionMessage.EKSeeFutureAction;
import com.example.explodingkittens.ekActionMessage.EKShuffleAction;
import com.example.explodingkittens.ekActionMessage.EKSkipAction;
import com.example.explodingkittens.infoMessage.CARDTYPE;
import com.example.explodingkittens.infoMessage.EKState;
import com.example.gameframework.LocalGame;
import com.example.gameframework.actionMessage.GameAction;
import com.example.gameframework.infoMessage.GameInfo;
import com.example.gameframework.players.GameComputerPlayer;

import java.util.Collections;
import java.util.Random;
import com.example.gameframework.players.GamePlayer;



public class EKLocalGame extends LocalGame {
    // instance vars for current and previous states
    private EKState currentState;
    private GameAction action;
    private int turn = currentState.getPlayerTurn();


    //constructor

    public EKLocalGame() {
        this.currentState = new EKState(4); //game with 4 players
    }

    //send updated state to player
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        EKState gameCopy = new EKState(currentState);
        p.sendInfo(gameCopy);
    }

    //checks if player can play card
    @Override
    protected boolean canMove(int playerIdx) {
        return playerIdx == currentState.getPlayerTurn();
    }

    //checks if game has ended
    @Override
    protected String checkIfGameOver() {
        int outPlayers = 0;
        for (int i = 0; i < players.length; i++) {
            if (currentState.gameOver() != -1) {
                return "Player" + currentState.gameOver() + "wins!";
            }
        }
        return null ;
    }


    @Override
    protected boolean makeMove(GameAction action) {
        if (action instanceof EKNopeAction) {
            if(currentState.playCard(turn, CARDTYPE.NOPE, currentState.deck.get(turn), currentState.discard)){
                //go do the stuff :)
                return true;
            }
        }
        else {
           // this.previousState = new EKState(this.currentState);
        }

        if(action instanceof EKFavorAction) { //repeat for all other card actions
            if(currentState.playCard(turn, CARDTYPE.FAVOR, currentState.deck.get(turn), currentState.discard)){
                return true;
            }
        }

        if (action instanceof EKAttackAction) {
            if(currentState.playCard(turn, CARDTYPE.ATTACK, currentState.deck.get(turn), currentState.discard)){
                currentState.endTurn(turn, currentState.SKIPTURN);
                // TODO ben questions :P
                return true;
            }
        }

        if (action instanceof EKShuffleAction) {
            if(currentState.playCard(turn, CARDTYPE.SHUFFLE, currentState.deck.get(turn), currentState.discard)){
                Collections.shuffle(currentState.draw);
                return true;
            }
        }

        if (action instanceof EKSeeFutureAction) {
            if(currentState.playCard(turn, CARDTYPE.SEEFUTURE, currentState.deck.get(turn), currentState.discard)){
                return true;
            }
        }

        if (action instanceof EKSkipAction) {
            if(currentState.playCard(turn, CARDTYPE.SKIP, currentState.deck.get(turn), currentState.discard)){
                currentState.endTurn(turn, currentState.SKIPTURN);
                return true;
            }
        }

        if (action instanceof EKEndTurnAction) {
            if(currentState.playCard(turn, CARDTYPE.SEEFUTURE, currentState.deck.get(turn), currentState.discard)){
                currentState.endTurn(turn, currentState.DRAWCARD);
                return true;
            }
        }

        if (action instanceof EKCatCardAction) {
            /*if(currentState.playCard(turn, CARDTYPE.SEEFUTURE, currentState.deck.get(turn), currentState.discard)){
                return true;
            }*/
        }

        Log.d("Invalid Action",
        "Action provided was an invalid action");
        return false;
        }


///////
/*
public boolean Attack(GamePlayer p) {
        int card = checkHand(currentState.getPlayerTurn()), 6);
        //move the card into the discard pile
        if(card == -1){
        return false;
        }
        currentState.getDiscardPile().add(currentState.getCurrentPlayerHand().get(card));
        currentState.getCurrentPlayerHand().remove(card);

        //Sending a message to the log
        Log.d("Log Played Attack", playerNames[currentState.getWhoseTurn()] + " played an Attack card ");

        //This player doesn't have to draw and the next player has to take two cards
        int tempCardsToDraw = currentState.getCardsToDraw();
        currState.setCardsToDraw(0);
        nextTurn();
        currentState.setCardsToDraw(tempCardsToDraw+1);

        return true;
        }//Attack()


//Nope card
/*public boolean Nope(GamePlayer p) {
        int turn = currentState.getPlayerTurn();
       // int card = checkPLayerHand(currentState.getCurrentPlayerHand(), 11);
        //move the played nope card to the discard pile and remove it from
        //the players hand
        currentState.playCard(turn, CARDTYPE.NOPE, currentState.deck.get(turn), currentState.discard);
        /*if(card == -1){
        return false;
        }

        currentState.getDiscardPile().add(currentState.getCurrentPlayerHand().get(card));
        currentState.getCurrentPlayerHand().remove(card);



        //Sending a message to the log
        Log.d("Log Played Nope", playerNames[currentState.getPLayerTurn()] + " played a Nope card");

        EKState temp = new EKGameState(currentState);
        currentState = previousState;
        previousState = temp;

        return true;
        }

public int checkHand(ArrayList<Card> hand, int cardTypeValue) {

        for (int i = 0; i < hand.size(); i++) {
        if (hand.get(i).getCardType() == cardTypeValue) {
        return i;
        }
        }
        return -1;
        }

public EKGameState getCurrentState() {
        return this.currentState;
        }
*/

        /// do we need other classes that are super to pul from??

/*import edu.up.cs301.game.GameFramework.GamePlayer;
        import edu.up.cs301.game.GameFramework.actionMessage.GameAction;

public class DrawCardAction extends GameAction {
    public DrawCardAction(GamePlayer p) {
        super(p);
    }
}
*/

        }
