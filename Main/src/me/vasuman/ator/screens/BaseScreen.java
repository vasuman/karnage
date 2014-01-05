package me.vasuman.ator.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import me.vasuman.ator.Drawer;
import me.vasuman.ator.MainGame;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 6:33 PM
 */
public abstract class BaseScreen implements Screen {
    public static final int resX = 1280;
    public static final int resY = 720;

    protected static MainGame game;

    public static void setGame(MainGame game) {
        BaseScreen.game = game;
    }

    public int width, height;
    protected boolean paused;
    protected Stage stage;

    public BaseScreen() {
        paused = false;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        Drawer.init(resX, resY);
        stage = new Stage(resX, resY, true);
        Gdx.input.setInputProcessor(stage);
        Screen current = game.getScreen();
        if (current != null) {
            current.dispose();
        }
        game.setScreen(this);
    }

    protected void setActorPosition(Actor actor, float x, float y) {
        actor.setPosition(x - actor.getWidth() / 2, y - actor.getHeight() / 2);
    }

    protected MoveToAction moveWidget(Group actor, float x, float y, float delT) {
        MoveToAction action = Actions.moveTo(x - actor.getWidth() / 2, y - actor.getHeight() / 2, delT);
        actor.addAction(action);
        return action;
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
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
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
