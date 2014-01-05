package me.vasuman.ator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import me.vasuman.ator.screens.BaseScreen;
import me.vasuman.ator.screens.LoadScreen;
import me.vasuman.ator.screens.MenuScreen;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 6:29 PM
 */
public class MainGame extends Game implements LoadScreen.LoadCallback {

    @Override
    public void doneLoading() {
        new MenuScreen();
    }

    public static interface RotationProvider {
        public float[] getRotation();
    }

    public static RotationProvider rotation;
    public static AssetManager assets;

    public MainGame(RotationProvider rotationProvider) {
        rotation = rotationProvider;
        assets = new AssetManager();
    }

    @Override
    public void create() {
        DefaultShader.defaultCullFace = 0;
        BaseScreen.setGame(this);
        new LoadScreen(this, new AssetDescriptor("game.json", Skin.class));
    }

    @Override
    public void render() {
        super.render();
        assets.update();
    }
}
