package com.example.test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import com.example.explodingkittens.EKLocalGame;
import com.example.explodingkittens.infoMessage.CARDTYPE;
import com.example.explodingkittens.infoMessage.Card;
import com.example.explodingkittens.infoMessage.EKState;
import com.example.explodingkittens.infoMessage.STATE;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EKStateTest {
//WRITTEN BY ALEX
//TODO ADD IN TEST FOR ATTACK
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
    public void moveStart() {
    }

    @Test
    //Audrey
    public void takeFromDraw() {
        EKState state = new EKState(4);
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

        stateTest.playCard(stateTest.playerTurn,CARDTYPE.TACO,stateTest.deck.get(stateTest.playerTurn));
        
        assertEquals(stateTest.discard, stateTest.deck.get(stateTest.playerTurn));




        stateTest.playCard(stateTest.playerTurn,CARDTYPE.SKIP,stateTest.deck.get(stateTest.playerTurn));
        assertEquals(2, stateTest.playerTurn);

    }

    @Test
    public void hasExplode() {

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

    @Test
    // Anna Implemented
    public void nextPlayer() {
        EKState state = new EKState(4);

    }

    @Test
    // Anna Implemented
    // THIS METHOD DOESN'T WORK - Arrays are never set to null, only 0
    public void checkIfValid() {
        /*
        EKState state = new EKState(4);
        state.prepareGame();

        for (int i = 0; i < 4; i++) {
            assertEquals(true, state.checkIfValid(i));
        }


        for (int i = 0; i < 4; i++) {
            ArrayList<Card> hand = new ArrayList<Card>();
            state.endTurn(i, EKState.LOST);
            assertTrue(state.checkIfValid(i) == false);
        }
        */
    }

    @Test
    public void gameStatetoString() {
    }

    @Test
    public void testToString() {
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

    @Test
    public void createCards() {
    }

    @Test
    public void takeTurn() {
    }

    @Test
    public void startGame() {
    }

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

    @Test
    public void getPlayerTurn() {
    }

    @Test
    public void gameOver() {
    }

    @Test
    public void testEquals() {
    }
//WRITTEN BY ALEX
    @Test
    public void equals(){
        EKState firstInstance = new EKState(4);
        EKState secondInstance = new EKState(firstInstance);
        assertEquals(true,firstInstance.equals(secondInstance));
    }
}