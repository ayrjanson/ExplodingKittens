package com.example.explodingkittens.ekActionMessage;

import com.example.explodingkittens.infoMessage.CARDTYPE;
import com.example.gameframework.actionMessage.GameAction;
import com.example.gameframework.players.GamePlayer;

public class EKPlayCardAction extends GameAction {
    public CARDTYPE type;

    public EKPlayCardAction(GamePlayer player, CARDTYPE type) {
        super(player);
        this.type = type;
    }
}



