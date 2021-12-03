package com.example.explodingkittens.players;

import com.example.explodingkittens.ekActionMessage.EKPlayCardAction;
import com.example.explodingkittens.infoMessage.CARDTYPE;
import com.example.explodingkittens.infoMessage.EKState;
import com.example.gameframework.infoMessage.GameInfo;
import com.example.gameframework.players.GameComputerPlayer;
import com.example.gameframework.utilities.Logger;

/**
 * EKComputerPlayer1 - Algorithm for deciding dumb computer player actions
 * @author Anna Yrjanson
 * @author Audrey Sauter
 * @author Alex Nastase
 */

public class EKComputerPlayer1 extends GameComputerPlayer {
    private boolean playedCard = false;

    /**
     * EKComputerPlayer1 - the constructor for the computer player
     * @param name - takes in the name of the computer player
     */
    public EKComputerPlayer1(String name) {
        super(name);
    }

    //TODO test this new computer type
    /**
     * receiveInfo - takes the current game state and decides the computer player's action
     * @param info - information which contains the game state
     */

    @Override
    protected void receiveInfo(GameInfo info) {
        //If info isn't an instance of EKState, stop processing
        if(!(info instanceof EKState)){
            return;
        }
        EKState receive = new EKState((EKState) info);
        //If it is this player's turn and this player is still in the game, continue
        if (receive.getPlayerTurn() == playerNum && receive.playerStatus[receive.playerTurn]) {
            Logger.log("CP", "Turn: " + playerNum);
            int handSize = receive.deck.get(receive.playerTurn).size();
            int numCardsToPlay = (int) (Math.random() * (handSize));
            //Runs this loop for the number of cards in the player's hand
            for (int i = 0; i < numCardsToPlay; i++) {
                sleep(1);
                //Selects a random index in the hand to play as a card
                int randoIdx = (int) (Math.random() * receive.deck.get(receive.playerTurn).size());
                    CARDTYPE type;
                    try {
                        //Extracts the card type from the specific index
                        type = receive.deck.get(receive.playerTurn).get(randoIdx).getType();
                    } catch (IndexOutOfBoundsException e) {
                        //If an inapplicable type, picks from the draw deck to end turn
                        type = CARDTYPE.ENDTURN;
                    }
                    EKPlayCardAction action;

                    //Plays specific type of card action based on the type extracted
                    switch (type) {
                        case MELON:
                            action = new EKPlayCardAction(this, CARDTYPE.MELON);
                            break;
                        case BEARD:
                            action = new EKPlayCardAction(this, CARDTYPE.BEARD);
                            break;
                        case POTATO:
                            action = new EKPlayCardAction(this, CARDTYPE.POTATO);
                            break;
                        case TACO:
                            action = new EKPlayCardAction(this, CARDTYPE.TACO);
                            break;
                        case ATTACK:
                            action = new EKPlayCardAction(this, CARDTYPE.ATTACK);
                            break;
                        case SHUFFLE:
                            action = new EKPlayCardAction(this, CARDTYPE.SHUFFLE);
                            break;
                        case FAVOR:
                            action = new EKPlayCardAction(this, CARDTYPE.FAVOR);
                            break;
                        case SKIP:
                            action = new EKPlayCardAction(this, CARDTYPE.SKIP);
                            break;
                        case SEEFUTURE:
                            action = new EKPlayCardAction(this, CARDTYPE.SEEFUTURE);
                            break;
                        case NOPE:
                            action = new EKPlayCardAction(this, CARDTYPE.NOPE);
                            break;
                        case DEFUSE:
                            action = new EKPlayCardAction(this,CARDTYPE.DEFUSE);
                            break;
                        case EXPLODE:
                            action = new EKPlayCardAction(this, CARDTYPE.EXPLODE);
                            break;
                        default:
                            action = new EKPlayCardAction(this, CARDTYPE.ENDTURN);
                            break;
                    }
                    //If an ENDTURN action, ends the turn by drawing a card from the draw deck
                    if(action.type == CARDTYPE.ENDTURN){
                        game.sendAction(action);
                        return;
                    }
                    game.sendAction(action);
                }
            }
        //Ensures that the turn always ends for the computer player
        EKPlayCardAction end = new EKPlayCardAction(this, CARDTYPE.ENDTURN);
        game.sendAction(end);
        return;
    }
}
