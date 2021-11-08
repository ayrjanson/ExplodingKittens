package com.example.explodingkittens.ekActionMessage;

import com.example.explodingkittens.infoMessage.Card;
import com.example.gameframework.actionMessage.GameAction;
import com.example.gameframework.players.GamePlayer;

public class EKCatCardAction extends GameAction {

    private Card cardToPlay;
    private int numCards;

    public EKCatCardAction(GamePlayer player, Card cardToPlay, int numCards) {
        super(player);

        this.cardToPlay = cardToPlay;
        this.numCards = numCards;

        //Differentiate by the type and number

        //TODO: Member variables
        // extract the enum of specific card types
    }

    public Card getCardToPlay() {
        return cardToPlay;
    }

    public int getNumCards() {
        return numCards;
    }
}
