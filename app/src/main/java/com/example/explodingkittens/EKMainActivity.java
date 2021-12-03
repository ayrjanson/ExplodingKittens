package com.example.explodingkittens;

import com.example.explodingkittens.players.EKComputerPlayer1;
import com.example.explodingkittens.players.EKHumanPlayer1;
import com.example.gameframework.GameMainActivity;
import com.example.gameframework.LocalGame;
import com.example.gameframework.R;
import com.example.gameframework.gameConfiguration.GameConfig;
import com.example.gameframework.gameConfiguration.GamePlayerType;
import com.example.gameframework.infoMessage.GameState;
import com.example.gameframework.players.GamePlayer;
import java.util.ArrayList;

/**
 * EKMainActivity - hosts the game and allows for the selection of player types locally
 * @author Anna Yrjanson
 */

public class EKMainActivity extends GameMainActivity {

    private static final String TAG = "EKMainActivity";
    public static final int PORT_NUMBER = 5213;

    /**
     * createLocalGame - creates a local game for players to interact with locally
     *
     * @param gameState The desired gameState to start at or null for new game
     * @return EKLocalGame - returns a local game that then facilitates play between local players
     * and determines the legality of moves
     */

    @Override
    public LocalGame createLocalGame(GameState gameState) {
        return new EKLocalGame();
    }

    /**
     * createDefaultConfig - creates the defaulted players and determines the types of players
     * that can be played (dumb computer, smart computer, human, etc.)
     * @return GameConfig - the types of players that can be played and assigned and the default
     * player configuration
     */

    public GameConfig createDefaultConfig() {
        ArrayList<GamePlayerType> playerTypes = new ArrayList<>();
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new EKHumanPlayer1(name, R.layout.activity_main);
            }
        });
        playerTypes.add(new GamePlayerType("Dumb Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new EKComputerPlayer1(name);
            }
        });
        GameConfig defaultConfig = new GameConfig(playerTypes, 4, 4, "Exploding Kittens", PORT_NUMBER);
        defaultConfig.addPlayer("Human", 0);
        defaultConfig.addPlayer("Computer", 1);
        defaultConfig.setRemoteData("Remote Player", "", 0);
        return defaultConfig;
    }

}