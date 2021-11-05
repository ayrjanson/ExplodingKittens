package com.example.explodingkittens.players;

import android.view.View;

import com.example.gameframework.GameMainActivity;
import com.example.gameframework.infoMessage.GameInfo;
import com.example.gameframework.players.GameHumanPlayer;

public class EKHumanPlayer1 extends GameHumanPlayer {
    /**
     * constructor
     *
     * @param name the name of the player
     */
    public EKHumanPlayer1(String name) {
        super(name);
    }

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {

    }

    @Override
    public void setAsGui(GameMainActivity activity) {

    }
}
