package me.vasuman.ator.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import me.vasuman.ator.MainGame;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 6:33 PM
 */
public abstract class BaseScreen implements Screen {
    protected static MainGame game;

    public static void setGame(MainGame game) {
        BaseScreen.game = game;
    }

    public int width, height;
    protected boolean paused;

    public BaseScreen() {
        paused = false;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
    }

    public void transit(BaseScreen next) {
        game.setScreen(next);
    }

    public void setAsCurrent() {
        game.setScreen(this);
    }

    public Screen getScreen() {
        return game.getScreen();
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void dispose() {
    }
}
