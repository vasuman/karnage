package com.bleatware.karnage.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.JsonReader;
import com.bleatware.karnage.GameState;
import com.bleatware.karnage.MainGame;

/**
 * Ator
 * User: vasuman
 * Date: 1/1/14
 * Time: 8:25 PM
 */
public class GameRunner {
    public static void main(String args[]) {
        BuildHook.main(args);
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 400;
        config.useGL20 = true;
        new LwjglApplication(new MainGame(new KeyboardControls(), "{}"), config);
    }

    private GameState loadState() {
        GameState state = new GameState();
        JsonReader reader = new JsonReader();

        return state;
    }
}
