package com.example.explodingkittens.ekActionMessage;

import com.example.explodingkittens.infoMessage.Card;
import com.example.gameframework.actionMessage.GameAction;
import com.example.gameframework.players.GamePlayer;

public class EKMoveAction extends GameAction {
    private static final String TAG ="EKMoveAction";
    private static final long serialVersionUID = -2242980258970485343L;

    private Card choice;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public EKMoveAction(GamePlayer player, Card selected) {
        super(player);

        //Extract the card types
        this.choice = selected;
    }

    public Card getCard() { return choice; }
}
