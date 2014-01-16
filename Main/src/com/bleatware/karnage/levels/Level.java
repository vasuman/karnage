package com.bleatware.karnage.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bleatware.karnage.*;
import com.bleatware.karnage.entities.Player;
import com.bleatware.karnage.screens.BaseScreen;
import com.bleatware.karnage.screens.Layout;
import com.bleatware.karnage.screens.ui.PauseWindow;

/**
 * Ator
 * User: vasuman
 * Date: 12/11/13
 * Time: 8:52 PM
 */
public abstract class Level extends BaseScreen implements Drawable {

    private Manager manager;
    private Physics physics;
    protected Player player;
    public CustomInputListener inputListener;
    private Window pauseWindow;
    private Label resumeCounter;

    public Level() {
        super();
        manager = Manager.getInstance();
        manager.init(this);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
        MainGame.rotation.calibrate();

        InputListener keyListen = new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK) {
                    pause();
                    return true;
                }
                return false;
            }
        };
        stage.addListener(keyListen);

        inputListener = new CustomInputListener(Drawer.getPerspectiveCamera());
        stage.addListener(inputListener);

        Skin skin = MainGame.assets.get("game.json", Skin.class);

        // DEBUG!

        pauseWindow = new PauseWindow(skin, this);

        resumeCounter = new Label("", skin);
        Layout.setActorPosition(resumeCounter, stage, Layout.Position.Center);

        ImageButton pauseButton = new ImageButton(skin, "pause");
        pauseButton.setPosition(1200, 650);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pause();
            }
        });

        stage.addActor(pauseButton);
        physics = Physics.getInstance();
        physics.init();
    }


    @Override
    public void pause() {
        if (paused) {
            return;
        }
        super.pause();
        stage.addActor(pauseWindow);
    }

    @Override
    public void resume() {
        if (!isInstigate()) {
            return;
        }
        setInstigate(false);
        pauseWindow.remove();
        resumeCounter.addAction(countdownResumeAction(3));
        stage.addActor(resumeCounter);
    }

    private Action countdownResumeAction(final int time) {
        return Actions.sequence(
                new Action() {
                    private float count = time;
                    private float rate = 1;

                    @Override
                    public boolean act(float delta) {
                        if (count <= 0) {
                            return true;
                        }
                        ((Label) getActor()).setText("" + ((int) count + 1));
                        count -= rate * delta;
                        return false;
                    }
                },
                new Action() {
                    @Override
                    public boolean act(float delta) {
                        getActor().remove();
                        Level.super.resume();
                        return true;
                    }
                }
        );
    }

    @Override
    public void render(float delta) {
        if (!paused) {
            physics.update(delta);
            manager.update(delta);
            update(delta);
        }
        inputListener.clearTap();
        Drawer.clearScreen();
        updateCamera(delta);
        Drawer.setupCamera();
        // Draw self
        getDrawer().draw();
        // Draw all entities
        manager.draw();
        Drawer.finishDraw();
        super.render(delta);
    }

    @Override
    public void dispose() {
        Gdx.input.setCatchBackKey(false);
        Gdx.input.setCatchMenuKey(false);
        manager.clear();
        super.dispose();
    }

    public void addActor(Actor actor) {
        stage.addActor(actor);
    }

    public abstract void update(float delT);

    public abstract void updateCamera(float delT);
}