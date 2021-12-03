package com.example.explodingkittens.infoMessage;
import com.example.gameframework.infoMessage.GameInfo;
import com.example.gameframework.players.GameComputerPlayer;

/*
        *An Exploding Kittens Smart Computer Player Class
        *Computer acts based on probability, available cards, and previous player
        * actions
        */

public class EKComputerPlayer2 extends GameComputerPlayer {


    private double probability;
    private double random;
    private EKState computerState;
    private int EKLocation;
    private int playerIdx;

    /**
            * constructor
     * Initializes all instance variables
     * @param name the player's name (e.g., "John")*/

    public void EKSmartComputerPlayer(String name) {
        super(name);
        this.probability = 0;
        this.random = 0;
        this.EKLocation = -1;
    }
   /* *
            *
            * @param info
     *      GameInfo object that has the current game information
     *      computer receives an EKGameState and decides the next course of action
     * @throws InterruptedException
*/
    @Override
    protected void receiveInfo(GameInfo info) throws InterruptedException {

        if(!(info instanceof EKState)){ return;}
        this.computerState = (EKState) info;

        if(!computerState.endTurn(0,1)) {
            Thread.sleep(500);
        }
        else{
            Thread.sleep(300);
        }

        //check to see if it's this player's turn
        if(this.computerState.getPlayerTurn() != this.playerNum){
            return;
        }
        else if(this.computerState.getPlayerTurn() == this.playerNum) {

            //generates random number from 0 - 100
            random = (double) Math.random() * 101;
            probability =
                    (((double)computerState.getEKCount()*computerState.getCardIndex()) / (double)computerState.deck.size()) * 100;
            if(checkForDefuse() == false){
                probability = probability *1.5;
            }

            //if random # is within probability play a card
            //otherwise draw
            if (random <= probability) {
                //play a card: Skip, Attack
                // (Prioritize Defuse)
                //else draw a card
                if (computerState.deck.get(playerIdx).size() != null) {
                    if (computerState.draw(this.computerState) == true) {
                        return;
                    }
                }
            }
        }
        else {
            //draw a card
            Card draw = new takeFromDraw(this);
            this.game.sendAction(draw);
        }
        takeFromDraw draw = new takeFromDraw(this);
        this.game.sendAction(draw);
    }

}//receive info

}

