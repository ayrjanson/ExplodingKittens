//TODO: For Anna to work on

package com.example.explodingkittens;

import android.widget.ImageButton;

import com.example.explodingkittens.EKLocalGame;
import com.example.explodingkittens.infoMessage.Card;
import com.example.explodingkittens.infoMessage.EKState;
import com.example.explodingkittens.players.EKComputerPlayer1;
import com.example.explodingkittens.players.EKHumanPlayer1;
import com.example.gameframework.GameMainActivity;
import com.example.gameframework.LocalGame;
import com.example.gameframework.R;
import com.example.gameframework.gameConfiguration.GameConfig;
import com.example.gameframework.gameConfiguration.GamePlayerType;
import com.example.gameframework.infoMessage.GameState;
import com.example.gameframework.players.GamePlayer;
import com.example.gameframework.utilities.Logger;
import com.example.gameframework.utilities.Saving;

import java.util.ArrayList;
import java.util.HashMap;

public class EKMainActivity extends GameMainActivity {

    private static final String TAG = "EKMainActivity";
    public static final int PORT_NUMBER = 5213;

    //TODO make hashmap for opponents with arraylist card
    HashMap<ImageButton, Card> bc = new HashMap() {{
        put(findViewById(R.id.drawPile),null);
        put(findViewById(R.id.discardPile),null);
        put(findViewById(R.id.playerCard1),null);
        put(findViewById(R.id.playerCard2),null);
        put(findViewById(R.id.playerCard3),null);
        put(findViewById(R.id.playerCard4),null);
        put(findViewById(R.id.playerCard5),null);

    }};




    /**
     * createLocalGame
     * @param gameState
     *              The desired gameState to start at or null for new game
     * @return EKLocalGame
     */

    public LocalGame createLocalGame(GameState gameState) {
        if(gameState == null) return new EKLocalGame((EKState) gameState);
        return new EKLocalGame((LocalGame) gameState);
    }

    /**
     * createDefaultConfig
     * @return GameConfig
     */

    public GameConfig createDefaultConfig() {
        ArrayList<GamePlayerType> playerTypes = new ArrayList<>();
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new EKHumanPlayer1(name, R.layout.activity_main);
                //TODO: Find how to do the layouts for the players
            }
        });

        playerTypes.add(new GamePlayerType("Dumb Computer Player") {
            public GamePlayer createPlayer(String name) {
                return new EKComputerPlayer1(name);
            }
        });

        GameConfig defaultConfig = new GameConfig(playerTypes, 4, 4, "Exploding Kittens", PORT_NUMBER);

        defaultConfig.addPlayer("Human", 0);
        defaultConfig.addPlayer("Computer", 3);

        defaultConfig.setRemoteData("Remote Player", "", 1);

        return defaultConfig;
    }

    @Override
    public GameState saveGame(String gameName) { return super.saveGame(getGameString(gameName));}

    @Override
    public GameState loadGame(String gameName) {
        String appName = getGameString(gameName);
        super.loadGame(appName);
        Logger.log(TAG, "Loading: "+ gameName);
        return (GameState) new EKState((EKState) Saving.readFromFile(appName, this.getApplicationContext()));
    }
}