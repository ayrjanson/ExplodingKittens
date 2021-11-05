package com.example.explodingkittens.players;

import com.example.explodingkittens.ekActionMessage.EKAttackAction;
import com.example.explodingkittens.ekActionMessage.EKBeardAction;
import com.example.explodingkittens.ekActionMessage.EKFavorAction;
import com.example.explodingkittens.ekActionMessage.EKMelonAction;
import com.example.explodingkittens.ekActionMessage.EKMoveAction;
import com.example.explodingkittens.ekActionMessage.EKNopeAction;
import com.example.explodingkittens.ekActionMessage.EKShuffleAction;
import com.example.explodingkittens.ekActionMessage.EKSkipAction;
import com.example.explodingkittens.infoMessage.CARDTYPE;
import com.example.explodingkittens.infoMessage.EKState;
import com.example.gameframework.infoMessage.GameInfo;
import com.example.gameframework.players.GameComputerPlayer;
import java.util.Random;
import com.example.gameframework.players.GamePlayer;

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
        int ran = new Random().nextInt(10);
        int turn = receive.getPlayerTurn();
                //receive.endTurn(EKState.DRAWCARD, turn);
                //receive.deck.get(turn);
        //if 0 is drawn, and we can play MELON
        if((ran == 0) && receive.playCard(turn, CARDTYPE.MELON, receive.deck.get(turn), receive.discard)){
            EKMelonAction melon = new EKMelonAction(this);
            game.sendAction(melon);
        }
        else{
            receive.endTurn(EKState.DRAWCARD, turn);
        }
        //if 1 is draw, and we can play BEARD
        if((ran == 1) && receive.playCard(turn, CARDTYPE.BEARD, receive.deck.get(turn), receive.discard)){
            EKBeardAction beard = new EKBeardAction(this);
            game.sendAction(beard);
        }
        else{
            receive.endTurn(EKState.DRAWCARD, turn);
        }
        // if 2 is drawn and we can play POTATO
       /* if((ran == 2) && receive.playCard(turn, CARDTYPE.POTATO, receive.deck.get(turn), receive.discard)){
            EKPotatoAction potato = new EKPotatoAction(this);
            game.sendAction(potato);
        }
        else{
            receive.endTurn(EKState.DRAWCARD, turn);
        }
        //if 3 is drawn and we can play TACO
        if((ran == 3) && receive.playCard(turn, CARDTYPE.TACO, receive.deck.get(turn), receive.discard)){
            EKTacoAction taco = new EKTacoAction(this);
            game.sendAction(taco);
        }
        else{
            receive.endTurn(EKState.DRAWCARD, turn);
        }*/
        //if 4 is drawn and we can play ATTACK
        if((ran == 4) && receive.playCard(turn, CARDTYPE.ATTACK, receive.deck.get(turn), receive.discard)){
            EKAttackAction attack = new EKAttackAction(this);
            game.sendAction(attack);
        }
        else{
            receive.endTurn(EKState.DRAWCARD, turn);
        }
        //if 5 is drawn and we can play SHUFFLE
        if((ran == 5) && receive.playCard(turn, CARDTYPE.SHUFFLE, receive.deck.get(turn), receive.discard)){
            EKShuffleAction shuffle = new EKShuffleAction(this);
            game.sendAction(shuffle);
        }
        else{
            receive.endTurn(EKState.DRAWCARD, turn);
        }
        //if 6 is drawn and we can play FAVOR
        if((ran == 6) && receive.playCard(turn, CARDTYPE.FAVOR, receive.deck.get(turn), receive.discard)){
            EKFavorAction favor = new EKFavorAction(this);
            game.sendAction(favor);
        }
        else{
            receive.endTurn(EKState.DRAWCARD, turn);
        }
        //if 7 is drawn and we can play SKIP
        if((ran == 7) && receive.playCard(turn, CARDTYPE.SKIP, receive.deck.get(turn), receive.discard)){
            EKSkipAction skip = new EKSkipAction(this);
            game.sendAction(skip);
        }
        else{
            receive.endTurn(EKState.DRAWCARD, turn);
        }
        //if 8 is drawn and we can play NOPE
        if((ran == 8) && receive.playCard(turn, CARDTYPE.NOPE, receive.deck.get(turn), receive.discard)){
            EKNopeAction nope = new EKNopeAction(this);
            game.sendAction(nope);
        }
        else{
            receive.endTurn(EKState.DRAWCARD, turn);
        }
        //if 9 is drawn and we can play DEFUSE
       /* if((ran == 9) && receive.playCard(turn, CARDTYPE.DEFUSE, receive.deck.get(turn), receive.discard)){
            receive.playCard(turn, CARDTYPE.DEFUSE, receive.deck.get(turn), receive.discard);
            receive.endTurn(EKState.DRAWCARD, turn);
        }
        else{
            receive.endTurn(EKState.DRAWCARD, turn);
        }

        */
        //sleep(2);
        }

}


