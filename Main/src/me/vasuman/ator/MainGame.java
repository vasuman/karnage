package me.vasuman.ator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import me.vasuman.ator.screens.BaseScreen;
import me.vasuman.ator.screens.MenuScreen;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 6:29 PM
 */
public class MainGame extends Game {
    private static String FONT_PATH = "tech.fnt";

    public static interface RotationProvider {
        public float[] getRotation();
    }

    public static RotationProvider rotation;

    public static AssetManager manager;

    public MainGame(RotationProvider rotationProvider) {
        rotation = rotationProvider;
        manager = new AssetManager();
    }

    @Override
    public void create() {
        BitmapFont techFont = new BitmapFont(Gdx.files.internal(FONT_PATH));
        Drawer.setFont(techFont);
        BaseScreen.setGame(this);
        new MenuScreen();
    }

}
