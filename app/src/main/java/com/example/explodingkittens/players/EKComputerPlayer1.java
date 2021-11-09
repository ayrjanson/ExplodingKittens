package com.example.explodingkittens.players;

import com.example.explodingkittens.ekActionMessage.EKAttackAction;
import com.example.explodingkittens.ekActionMessage.EKEndTurnAction;
import com.example.explodingkittens.ekActionMessage.EKFavorAction;
import com.example.explodingkittens.ekActionMessage.EKNopeAction;
import com.example.explodingkittens.ekActionMessage.EKSeeFutureAction;
import com.example.explodingkittens.ekActionMessage.EKShuffleAction;
import com.example.explodingkittens.ekActionMessage.EKSkipAction;
import com.example.explodingkittens.infoMessage.CARDTYPE;
import com.example.explodingkittens.infoMessage.EKState;
import com.example.gameframework.infoMessage.GameInfo;
import com.example.gameframework.players.GameComputerPlayer;

import java.util.Random;

public class EKComputerPlayer1 extends GameComputerPlayer {

    private boolean playedCard = false;
    // constructor
    public EKComputerPlayer1(String name) { super(name); }

    /**
     * callback method--game's state has changed
     * @param info
     *      the information (presumably containing the game's state)
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        EKState receive = new EKState((EKState) info);
        int ran = new Random().nextInt(6);
        int turn = receive.getPlayerTurn();
        if (turn == playerNum) {
            //if played card, then just send end turn action
            //for
            // pick ith card in hand, try to play
            // if it works, set played & send
            if(playedCard != true) {
                for(int i = 0; i < receive.deck.get(receive.getPlayerTurn()).size(); i++) {
                    //if 0 is drawn and we can play ATTACK
                    if (playedCard == false && receive.deck.get(playerNum).get(i).getType().equals(CARDTYPE.ATTACK)) {
                        receive.playCard(turn, CARDTYPE.ATTACK, receive.deck.get(turn), receive.discard);
                        EKAttackAction attack = new EKAttackAction(this);
                        playedCard = true;
                        game.sendAction(attack);
                    }

                    //if 1 is drawn and we can play SHUFFLE
                    else if (playedCard == false && receive.deck.get(playerNum).get(i).getType().equals(CARDTYPE.SHUFFLE)) {
                        receive.playCard(turn, CARDTYPE.SHUFFLE, receive.deck.get(turn), receive.discard);
                        EKShuffleAction shuffle = new EKShuffleAction(this);
                        playedCard = true;
                        game.sendAction(shuffle);
                    }

                    //if 2 is drawn and we can play FAVOR
                    else if (playedCard == false && receive.deck.get(playerNum).get(i).getType().equals(CARDTYPE.FAVOR)) {
                        receive.playCard(turn, CARDTYPE.FAVOR, receive.deck.get(turn), receive.discard);
                        EKFavorAction favor = new EKFavorAction(this);
                        playedCard = true;
                        game.sendAction(favor);
                    }

                    //if 3 is drawn and we can play SKIP
                    else if (playedCard == false && receive.deck.get(playerNum).get(i).getType().equals(CARDTYPE.SKIP)) {
                        receive.playCard(turn, CARDTYPE.SKIP, receive.deck.get(turn), receive.discard);
                        EKSkipAction skip = new EKSkipAction(this);
                        playedCard = true;
                        game.sendAction(skip);
                    }

                    //if 4 is drawn and we can play NOPE
                    else if (playedCard == false && receive.deck.get(playerNum).get(i).getType().equals(CARDTYPE.NOPE)) {
                        receive.playCard(turn, CARDTYPE.NOPE, receive.deck.get(turn), receive.discard);
                        EKNopeAction nope = new EKNopeAction(this);
                        playedCard = true;
                        game.sendAction(nope);
                    }

                    //IF 5 is drawn and we can play SEETHEFUTURE
                    else if (playedCard == false && receive.deck.get(playerNum).get(i).getType().equals(CARDTYPE.SEEFUTURE)) {
                        receive.playCard(turn, CARDTYPE.SEEFUTURE, receive.deck.get(turn), receive.discard);
                        EKSeeFutureAction see = new EKSeeFutureAction(this);
                        game.sendAction(see);
                        playedCard = true;
                        game.sendAction(see);
                    }

                    else {
                        EKEndTurnAction end = new EKEndTurnAction(this);
                        game.sendAction(end);
                    }
                }
            }
            else {
                EKEndTurnAction end = new EKEndTurnAction(this);
                game.sendAction(end);
            }
            playedCard = false;
        }
    }
}
