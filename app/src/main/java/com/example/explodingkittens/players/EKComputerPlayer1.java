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
            //if 0 is drawn and we can play ATTACK
            if((ran == 0) && receive.playCard(turn, CARDTYPE.ATTACK, receive.deck.get(turn), receive.discard)){
                EKAttackAction attack = new EKAttackAction(this);
                game.sendAction(attack);
            }
            else{
                EKEndTurnAction end = new EKEndTurnAction(this);
                game.sendAction(end);
            }
            //if 1 is drawn and we can play SHUFFLE
            if((ran == 1) && receive.playCard(turn, CARDTYPE.SHUFFLE, receive.deck.get(turn), receive.discard)){
                EKShuffleAction shuffle = new EKShuffleAction(this);
                game.sendAction(shuffle);
            }
            else{
                EKEndTurnAction end = new EKEndTurnAction(this);
                game.sendAction(end);
            }
            //if 2 is drawn and we can play FAVOR
            if((ran == 2) && receive.playCard(turn, CARDTYPE.FAVOR, receive.deck.get(turn), receive.discard)){
                EKFavorAction favor = new EKFavorAction(this);
                game.sendAction(favor);
            }
            else{
                EKEndTurnAction end = new EKEndTurnAction(this);
                game.sendAction(end);
            }
            //if 3 is drawn and we can play SKIP
            if((ran == 3) && receive.playCard(turn, CARDTYPE.SKIP, receive.deck.get(turn), receive.discard)){
                EKSkipAction skip = new EKSkipAction(this);
                game.sendAction(skip);
            }
            else{
                EKEndTurnAction end = new EKEndTurnAction(this);
                game.sendAction(end);
            }
            //if 4 is drawn and we can play NOPE
            if((ran == 4) && receive.playCard(turn, CARDTYPE.NOPE, receive.deck.get(turn), receive.discard)){
                EKNopeAction nope = new EKNopeAction(this);
                game.sendAction(nope);
            }
            else{
                EKEndTurnAction end = new EKEndTurnAction(this);
                game.sendAction(end);
            }
            //IF 5 is drawn and we can play SEETHEFUTURE
            if((ran == 5) && receive.playCard(turn, CARDTYPE.SEEFUTURE, receive.deck.get(turn), receive.discard)){
                EKSeeFutureAction see = new EKSeeFutureAction(this);
                game.sendAction(see);
            }
            else{
                EKEndTurnAction end = new EKEndTurnAction(this);
                game.sendAction(end);
            }
        }
    }
}
