package com.bleatware.karnage.android;


import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.bleatware.karnage.GameState;
import com.bleatware.karnage.MainGame;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 7:11 PM
 */
public class GameActivity extends AndroidApplication {
    private static final String GAME_STATE_FILE = "GameState";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useGL20 = true;
        config.useAccelerometer = true;
        config.useWakelock = true;
        GameState state = getSavedGameState();
        initialize(new MainGame(new SystemRotation((SensorManager) getSystemService(SENSOR_SERVICE)), "{}"), config);
    }

    private GameState getSavedGameState() {
        SharedPreferences preferences = getSharedPreferences(GAME_STATE_FILE, MODE_PRIVATE);
        GameState state = new GameState();
        return null;
    }


}