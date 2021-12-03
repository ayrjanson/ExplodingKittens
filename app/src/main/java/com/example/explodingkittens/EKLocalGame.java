package com.example.explodingkittens;

import com.example.explodingkittens.ekActionMessage.EKPlayCardAction;
import com.example.explodingkittens.infoMessage.CARDTYPE;
import com.example.explodingkittens.infoMessage.Card;
import com.example.explodingkittens.infoMessage.EKState;
import com.example.explodingkittens.players.EKComputerPlayer1;
import com.example.gameframework.LocalGame;
import com.example.gameframework.actionMessage.GameAction;
import com.example.gameframework.players.GamePlayer;
import com.example.gameframework.utilities.GameTimer;
import com.example.gameframework.utilities.Logger;

/**
 * EKLocalGame - the local game that determines the legality of moves from the various players and
 * changes the gameState accordingly
 * @author Alex Nastase
 * @author Anna Yrjanson
 * @author Audrey Sauter
 */

public class EKLocalGame extends LocalGame {

    private GameTimer timer;

    /**
     * EKLocalGame - creates the state and creates a timer for specific actions
     */
    public EKLocalGame() {
        super();
        super.state = new EKState(4);
        timer = this.getTimer();

    }

    /**
     * EKLocalGame - copy constructor that takes in an already existing gameState to then be
     * assigned as a new game state in a new local game, this also assigns a timer
     * @param ekgamestate
     */
    // CONSTRUCTOR WITH LOADED EK GAME STATE
    public EKLocalGame(EKState ekgamestate) {
        super();
        super.state = new EKState(ekgamestate);
        timer = this.getTimer();

    }

    /**
     * sendUpdatedStateTo - sends an updated state to a specific player
     * @param p - the player that the state gets sent to
     */
    //send updated state to player
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        EKState gameCopy = new EKState((EKState) state);
        p.sendInfo(gameCopy);
    }

    /**
     * canMove - determines that the player turn assigned in the state is the same as the one
     * given as the player's player-number
     * @param playerIdx - the player's player-number (ID)
     * @return whether the values between the ID and playerTurn value
     */
    @Override
    protected boolean canMove(int playerIdx) {
        if (((EKState) state).getPlayerTurn() == playerIdx) return true;
        return false;
    }

    /**
     * checkIfGameOver - checks if one player remains to end the game, continues if not
     * @return string if only one player remains with a message displaying which player won, returns
     * null if no player is selected
     */
    @Override
    protected String checkIfGameOver() {
        for (int i = 0; i < players.length; i++) {
            if (((EKState) state).gameOver() != -1) {
                return "Player " + ((EKState) state).gameOver() + " wins! ";
            }
        }
        return null; // Game not over
    }

    /**
     * makeMove - sends the appropriate action to the state if the play is legal
     * @param action - the move that the player has sent to the game
     * @return whether the action was executed/legal or not
     */

    @Override
    protected boolean makeMove(GameAction action) {
        int turn = ((EKState) state).playerTurn;

        if (action instanceof EKPlayCardAction) {

            EKPlayCardAction at = (EKPlayCardAction) action;
            CARDTYPE type = at.type;
            EKState currentState = (EKState) state;
            //currentState.lastMessage += ("Player " + currentState.playerTurn + " played a " + type.name() + " card.\n" );

            switch (type) {
                case MELON:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.MELON, currentState.deck.get(currentState.playerTurn))) {
                        Logger.log("LocalGame", "Playing a Melon Card");
                        return true;
                    }
                    break;
                case BEARD:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.BEARD, currentState.deck.get(currentState.playerTurn))) {
                        Logger.log("LocalGame", "Playing a Beard Card");
                        return true;
                    }
                    break;
                case POTATO:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.POTATO, currentState.deck.get(currentState.playerTurn))) {
                        Logger.log("LocalGame", "Playing a Potato Card");
                        return true;
                    }
                    break;
                case TACO:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.TACO, currentState.deck.get(currentState.playerTurn))) {
                        Logger.log("LocalGame", "Playing a Taco Card");
                        return true;
                    }
                    break;
                case ATTACK:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.ATTACK, currentState.deck.get(currentState.playerTurn))) {
                        Logger.log("LocalGame", "Playing a Attack Card");
                        return true;
                    }
                    break;
                case SHUFFLE:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.SHUFFLE, currentState.deck.get(currentState.playerTurn))) {
                        Logger.log("LocalGame", "Playing a Shuffle Card");
                        return true;
                    }
                    break;
                case FAVOR:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.FAVOR, currentState.deck.get(currentState.playerTurn))) {
                        Logger.log("LocalGame", "Playing a Favor Card");
                        return true;
                    }
                    break;
                case SKIP:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.SKIP, currentState.deck.get(currentState.playerTurn))) {
                        Logger.log("LocalGame", "Playing a Skip Card");
                        return true;
                    }
                    break;
                case SEEFUTURE:
                    if(players[currentState.playerTurn] instanceof EKComputerPlayer1){
                        return false;
                    }
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.SEEFUTURE, currentState.deck.get(currentState.playerTurn))) {
                        Logger.log("LocalGame", "Playing a SeeFuture Card");
                        timer.setInterval(2000);
                        timer.start();
                        return true;
                    }
                    break;
                case NOPE:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.NOPE, currentState.deck.get(currentState.playerTurn))) {
                        Logger.log("LocalGame", "Playing a Nope Card");
                        return true;
                    }
                    break;
                case DEFUSE:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.DEFUSE, currentState.deck.get(currentState.playerTurn))) {
                        Logger.log("LocalGame", "Playing a Defuse Card - this shouldn't be a thing");
                        return true;
                    }
                    break;
                case EXPLODE:
                    if (currentState.playCard(currentState.playerTurn, CARDTYPE.EXPLODE, currentState.deck.get(currentState.playerTurn))) {
                        Logger.log("LocalGame", "Playing a Explode Card - this shouldn't be a thing");
                        return true;
                    }
                    break;
                case ENDTURN:
                    ((EKState) state).endTurn(turn, ((EKState) state).DRAWCARD);
                    //((EKState) state).nextPlayer(((EKState) state).getPlayerTurn());
                    Logger.log("makeMove", "Ended Turn, current player now is" + ((EKState) state).playerTurn);
                    return true;
                case DRAW:
                    ((EKState) state).endTurn(((EKState) state).playerTurn,((EKState) state).DRAWCARD);
                    return true;
                case STEAL:
                    if(currentState.playCard(currentState.playerTurn, CARDTYPE.STEAL, currentState.deck.get(currentState.playerTurn))){
                        return true;
                    }
            }
        }
        return false;
    }

    /**
     * timerTicked - ends the timer when the SeeFuture timer is up
     */

    @Override
    protected void timerTicked(){
        ((EKState) state).justPlayedSeeFuture = false;
        this.sendAllUpdatedState();
        timer.stop();
    }
}



