package com.example.test;

import static org.junit.Assert.*;

import com.example.explodingkittens.infoMessage.CARDTYPE;
import com.example.explodingkittens.infoMessage.Card;
import com.example.explodingkittens.infoMessage.EKState;
import com.example.explodingkittens.infoMessage.STATE;

import org.junit.Test;

import java.util.ArrayList;

public class EKStateTest {

    @Test
    public void endTurn() {
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
    public void takeFromDraw() {
    }

    @Test
    public void playCard() {
    }

    @Test
    public void hasExplode() {
    }

    @Test
    public void getCardIndex() {
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
}