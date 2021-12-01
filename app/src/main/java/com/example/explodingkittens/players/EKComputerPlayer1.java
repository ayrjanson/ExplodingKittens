package com.example.explodingkittens.players;

import com.example.explodingkittens.EKLocalGame;
import com.example.explodingkittens.ekActionMessage.EKPlayCardAction;
import com.example.explodingkittens.infoMessage.CARDTYPE;
import com.example.explodingkittens.infoMessage.EKState;
import com.example.gameframework.infoMessage.GameInfo;
import com.example.gameframework.players.GameComputerPlayer;
import com.example.gameframework.utilities.Logger;

public class EKComputerPlayer1 extends GameComputerPlayer {
    private boolean playedCard = false;


    public EKComputerPlayer1(String name) {
        super(name);
    }

    //TODO test this new computer type
    /**
     * callback method--game's state has changed
     * @param info
     *      the information (presumably containing the game's state)
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if(!(info instanceof EKState)) return;
        EKState receive = new EKState((EKState) info);
        if (receive.getPlayerTurn() == playerNum) {
            Logger.log("CP", "Turn: " + playerNum);
            if(playedCard != true) {
                sleep(1);
                int handSize = receive.deck.get(receive.playerTurn).size();
                int numCardsToPlay = (int)(Math.random() * (handSize));
                //this plays every card in their hand???
                for(int i = 0; i < numCardsToPlay; i++) {
                    int randoIdx = (int)(Math.random() * receive.deck.get(receive.playerTurn).size());
                    CARDTYPE type;
                    try {
                        type = receive.deck.get(receive.playerTurn).get(randoIdx).getType();
                    }catch(IndexOutOfBoundsException e){
                        type = CARDTYPE.ENDTURN;
                    }
                    EKPlayCardAction action;
                    switch(type){
                            case MELON:
                                action = new EKPlayCardAction(this,CARDTYPE.MELON);
                                break;
                            case BEARD:
                                action = new EKPlayCardAction(this,CARDTYPE.BEARD);
                                break;
                            case POTATO:
                                action = new EKPlayCardAction(this,CARDTYPE.POTATO);
                                break;
                            case TACO:
                                action = new EKPlayCardAction(this,CARDTYPE.TACO);
                                break;
                            case ATTACK:
                                action = new EKPlayCardAction(this,CARDTYPE.ATTACK);
                                break;
                            case SHUFFLE:
                                action = new EKPlayCardAction(this,CARDTYPE.SHUFFLE);
                                break;
                            case FAVOR:
                                action = new EKPlayCardAction(this,CARDTYPE.FAVOR);
                                break;
                            case SKIP:
                                action = new EKPlayCardAction(this,CARDTYPE.SKIP);
                                break;
                            case SEEFUTURE:
                                action = new EKPlayCardAction(this,CARDTYPE.SEEFUTURE);
                                break;
                            case NOPE:
                                action = new EKPlayCardAction(this,CARDTYPE.NOPE);
                                break;
                            case DEFUSE:
                                action = new EKPlayCardAction(this,CARDTYPE.DEFUSE);
                                break;
                            case EXPLODE:
                                action = new EKPlayCardAction(this,CARDTYPE.EXPLODE);
                                break;
                            case ENDTURN:
                                action = new EKPlayCardAction(this,CARDTYPE.ENDTURN);
                                break;
                            default:
                                action = new EKPlayCardAction(this,CARDTYPE.ENDTURN);
                        }
                        playedCard = true;
                        game.sendAction(action);

                        //((EKState) info).lastMessage = ("Player " + playerNum + " played a " + type.name() + " card." );
                        return;
                }
            }
            EKPlayCardAction end = new EKPlayCardAction(this,CARDTYPE.ENDTURN);
            playedCard = false;
            game.sendAction(end);
            ((EKState) info).lastMessage = ("Player " + playerNum + " ended their turn by drawing a card.");
            return;
        }
    }
}
