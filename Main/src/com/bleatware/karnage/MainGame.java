package com.bleatware.karnage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.bleatware.karnage.screens.BaseScreen;
import com.bleatware.karnage.screens.LoadScreen;
import com.bleatware.karnage.screens.MenuScreen;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 6:29 PM
 */
public class MainGame extends Game implements LoadScreen.LoadCallback {

    public static int offsetX, offsetY, viewWidth, viewHeight;

    @Override
    public void doneLoading() {
        Skin skin = assets.get("game.json", Skin.class);
        Drawer.setFont(skin.getFont("default-font"));
        new MenuScreen();
    }

    public static interface RotationProvider {
        public static final float LIMIT = 1; //(float) Math.sqrt(2);

        public Vector2 getVector();

        public void calibrate();
    }

    public static RotationProvider rotation;
    public static AssetManager assets;
    public static GameState state;

    public MainGame(RotationProvider rotationProvider, String serializedGameState) {
        rotation = rotationProvider;
        assets = new AssetManager();
        Json json = new Json(JsonWriter.OutputType.json);
        state = json.fromJson(GameState.class, serializedGameState);
    }

    public String getGameState() {
        Json json = new Json(JsonWriter.OutputType.json);
        return json.toJson(state);
    }

    @Override
    public void create() {
        DefaultShader.defaultCullFace = 0;
        BaseScreen.setGame(this);
        new LoadScreen(this, new AssetDescriptor<Skin>("game.json", Skin.class));
        setViewport();
    }

    public static void setViewport() {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        float idealRatio = (float) BaseScreen.resX / BaseScreen.resY;
        float realRatio = (float) width / height;
        if (realRatio > idealRatio) {
            viewHeight = height;
            viewWidth = (int) (idealRatio * height);
            offsetX = (width - viewWidth) / 2;
            offsetY = 0;
        } else if (realRatio < idealRatio) {
            viewWidth = width;
            viewHeight = (int) (width / idealRatio);
            offsetX = 0;
            offsetY = (height - viewHeight) / 2;
        } else {
            viewHeight = height;
            viewWidth = width;
            offsetX = 0;
            offsetY = 0;
        }
        Gdx.gl.glViewport(offsetX, offsetY, viewWidth, viewHeight);
    }

    @Override
    public void render() {
        super.render();
        assets.update();
    }
}
