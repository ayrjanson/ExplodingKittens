package com.example.test;

import static org.junit.Assert.*;

import androidx.annotation.NonNull;

import com.example.explodingkittens.EKLocalGame;
import com.example.explodingkittens.infoMessage.CARDTYPE;
import com.example.explodingkittens.infoMessage.Card;
import com.example.explodingkittens.infoMessage.EKState;
import com.example.explodingkittens.infoMessage.STATE;

import org.junit.Test;

import java.util.ArrayList;

public class EKStateTest {

    //WRITTEN BY ALEX
    @Test
    public void endTurn() {
        //THE DRAW CARD PART
        EKState test = new EKState(4);
        int drawSize = test.draw.size();
        int handSize = test.deck.get(test.playerTurn).size();
        test.endTurn(test.playerTurn,EKState.DRAWCARD);
        assertEquals(1,test.playerTurn);
        assertEquals(drawSize-1,test.draw.size());
        assertEquals(handSize+1,test.deck.get(0).size());

        //THE LOST PART
        test.endTurn(test.playerTurn,EKState.LOST);
        assertEquals(true,(test.deck.get(test.playerTurn-1).isEmpty()));
        assertEquals(false,test.playerStatus[test.playerTurn-1]);

        //THE SKIP PART
        if(!test.deck.get(test.playerTurn).contains(new Card(CARDTYPE.SKIP))) {
            test.deck.get(test.playerTurn).add(new Card(CARDTYPE.SKIP));
        }
        test.playCard(test.playerTurn,CARDTYPE.SKIP,test.deck.get(test.playerTurn));
        assertEquals(false,test.deck.get(test.playerTurn).contains(new Card(CARDTYPE.SKIP)));

        //ATTACK Part
        if(!test.deck.get(test.playerTurn).contains(new Card(CARDTYPE.ATTACK))){
            test.deck.get(test.playerTurn).add(new Card(CARDTYPE.ATTACK));
        }
        int oldTurnNum = test.playerTurn;
        int oldHandSize = test.deck.get(oldTurnNum).size();

        test.playCard(test.playerTurn,CARDTYPE.ATTACK,test.deck.get(test.playerTurn));
        assertEquals((oldHandSize-1),test.deck.get(oldTurnNum).size());
        assertEquals(handSize+2,test.deck.get(test.playerTurn).size());

    }

    @Test
    // Anna Implemented
    public void moveToDiscard() {
        EKState state = new EKState(4);
        state.prepareGame();
        for (int i = 0; i < 4; i++) {
            state.moveToDiscard(state.deck.get(i), state.discard);
            assertEquals(0, state.deck.get(i).size());
        }
    }

    @Test
    //Audrey
    public void takeFromDraw() {
        EKState state = new EKState(4);
        state.prepareGame();
        Card compare = state.draw.get(0);
        assertEquals(state.takeFromDraw(), compare);
    }

    @Test
//Audrey
    public void playCard() {
        EKLocalGame test = new EKLocalGame();
        EKState stateTest = (EKState) test.getGameState();
        int x = stateTest.playerTurn;
        stateTest.playCard(stateTest.playerTurn,CARDTYPE.ATTACK,stateTest.deck.get(stateTest.playerTurn));
        assertEquals(x+1,stateTest.playerTurn); // see if players turn is incremented
        assertEquals(false, stateTest.compareArray(stateTest.draw));// checking to see if the draw pile stays the same
        // after ATTACK card is played
        stateTest.playCard(stateTest.playerTurn,CARDTYPE.SHUFFLE,stateTest.deck.get(stateTest.playerTurn));
        assertEquals(false,stateTest.compareList(stateTest.draw, stateTest.deck.get(stateTest.playerTurn)));
        stateTest.playCard(stateTest.playerTurn,CARDTYPE.SKIP,stateTest.deck.get(stateTest.playerTurn));
        assertEquals(2, stateTest.playerTurn);

    }


    @Test
    //Audrey
    public void getCardIndex() {
        EKState state = new EKState(4);
        ArrayList<Card> testCardArray = new ArrayList<>();

        CARDTYPE[] cardTypes = {CARDTYPE.MELON, CARDTYPE.BEARD, CARDTYPE.POTATO, CARDTYPE.TACO,
                CARDTYPE.ATTACK, CARDTYPE.SHUFFLE, CARDTYPE.FAVOR, CARDTYPE.SKIP, CARDTYPE.SEEFUTURE,
                CARDTYPE.NOPE, CARDTYPE.DEFUSE, CARDTYPE.EXPLODE};
        for (int i = 0; i < cardTypes.length; i++) {
            Card toAdd = new Card(cardTypes[i]);
            testCardArray.add(toAdd);
        }
        for (int i = 0; i < testCardArray.size(); i++) {
            assertEquals(state.getCardIndex(cardTypes[i], testCardArray), i);
        }
    }

    @Test
    public void getCard() {
        EKState state = new EKState(4);
        ArrayList<Card> testArray = new ArrayList<>();
        CARDTYPE[] cardTypes = {CARDTYPE.MELON, CARDTYPE.BEARD, CARDTYPE.POTATO, CARDTYPE.TACO,
            CARDTYPE.ATTACK, CARDTYPE.SHUFFLE, CARDTYPE.FAVOR, CARDTYPE.SKIP, CARDTYPE.SEEFUTURE,
            CARDTYPE.NOPE, CARDTYPE.DEFUSE, CARDTYPE.EXPLODE};
        for (int i = 0; i < cardTypes.length; i++) {
            Card toAdd = new Card(cardTypes[i]);
            testArray.add(toAdd);
        }

        for (int i = testArray.size(); i < testArray.size(); i++) {
            assertEquals(state.getCard(cardTypes[i], testArray).getType(), cardTypes[i]);
        }
    }
/*
    @Test
    // Anna Implemented
    public void nextPlayer() {
        EKState state = new EKState(4);

    }

 */

    //ALEX
    @Test
    public void testToString() {
        EKState test = new EKState(4);
        String testToString = test.toString();
        EKState test2 = new EKState(test);
        assertEquals(true,testToString.equals(test2.toString()));
    }

    @Test
    public void prepareGame() {
        EKState state = new EKState(4);
        state.prepareGame();
        for (int i = 0; i < state.deck.size(); i++) {
            assertEquals(7, state.deck.get(i).size());
        }
        assertEquals(0, state.discard.size());
        assertEquals(23, state.draw.size());
        assertEquals(STATE.GAME_SETUP, state.gameState);
    }

    //ALEX
    @Test
    public void createCards() {
        EKState test = null;
        assertNull(test);
        //assertEquals(0, test.draw.size());
        test = new EKState(4);
        test.gameState = STATE.PROGRAM_LAUNCHED;

        assertEquals(false,test.createCards());
        test.gameState = STATE.INIT_ARRAYS;

        assertEquals(true,test.createCards());
        assertEquals(false,test.draw.isEmpty());

    }

    //Anna
    @Test
    public void endGame() {
        EKState state = new EKState(4);
        state.prepareGame();
        boolean[] playerStatus1 = state.getPlayerStatus();
        boolean[] playerStatus2 = new boolean[] {true, true, true, false};
        boolean[] playerStatus3 = new boolean[] {true, true, false, false};
        boolean[] playerStatus4 = new boolean[] {true, false, false, false};
        boolean[] playerStatus5 = new boolean[] {false, false, false, false};

        assertEquals(-1, state.endGame(playerStatus1));
        assertEquals(-1, state.endGame(playerStatus2));
        assertEquals(-1, state.endGame(playerStatus3));
        assertEquals(0, state.endGame(playerStatus4));
        assertEquals(-1, state.endGame(playerStatus5));
    }

    //ALEX
    @Test
    public void getPlayerTurn() {
        EKState test = new EKState(4);
        assertEquals(0,test.getPlayerTurn());

        test.nextPlayer(0);
        assertEquals(1,test.getPlayerTurn());

        test.nextPlayer(1);
        assertEquals(2,test.getPlayerTurn());

        test.nextPlayer(2);
        assertEquals(3,test.getPlayerTurn());

        test.nextPlayer(3);
        assertEquals(0,test.getPlayerTurn());
    }

//WRITTEN BY ALEX
    @Test
    public void equals(){
        EKState firstInstance = new EKState(4);
        EKState secondInstance = new EKState(firstInstance);
        assertEquals(true,firstInstance.equals(secondInstance));
    }

    @Test
    public void nextPlayer(){
        EKState test = new EKState(4);
        //testing it cycles through normally, when all players are in
        test.nextPlayer(test.playerTurn);
        assertEquals(1,test.playerTurn);

        test.nextPlayer(test.playerTurn);
        assertEquals(2,test.playerTurn);

        test.nextPlayer(test.playerTurn);
        assertEquals(3,test.playerTurn);

        //tests that it will recognize the game is over
        test.nextPlayer(test.playerTurn);
        test.playerStatus = new boolean[]{false,true,false,false};
        assertEquals(false,test.nextPlayer(test.playerTurn));

        //tests that it will skip an out player
        test.playerStatus = new boolean[]{true,false,true,true};
        test.nextPlayer(test.playerTurn);
        assertEquals(2,test.playerTurn);

        //tests that it goes to a next player even if the current player (just got) out.
        test.playerStatus = new boolean[]{true,false,false,true};
        assertEquals(true,test.nextPlayer(test.playerTurn));

        //calls end turn to check if the game realizes that all but one player is out
        test.endTurn(test.playerTurn,EKState.LOST);
        assertEquals(false,test.nextPlayer(test.playerTurn));

        test.playerStatus = new boolean[]{true,false,true,false};

    }
}