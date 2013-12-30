package me.vasuman.ator;

import com.badlogic.gdx.Game;
import me.vasuman.ator.screens.BaseScreen;
import me.vasuman.ator.screens.MenuScreen;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 6:29 PM
 */
public class MainGame extends Game {
    public static interface RotationProvider {
        public void calibrate();

        public float[] getRotation();
    }

    public static RotationProvider rotationProvider;

    public MainGame(RotationProvider rotationProvider) {
        MainGame.rotationProvider = rotationProvider;
    }

    @Override
    public void create() {
        Drawer.setFont(Resource.techFont);
        BaseScreen.setGame(this);
        new MenuScreen();
    }

}
