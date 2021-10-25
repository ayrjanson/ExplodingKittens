package com.example.gameframework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends edu.up.cs301.game.GameFramework.GameMainActivity {

    public edu.up.cs301.game.GameFramework.LocalGame createLocalGame(edu.up.cs301.game.GameFramework.infoMessage.GameState gameState);

    public edu.up.cs301.game.GameFramework.gameConfiguration.GameConfig createDefaultConfig();
}