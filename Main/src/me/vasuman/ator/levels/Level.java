package me.vasuman.ator.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import me.vasuman.ator.*;
import me.vasuman.ator.entities.Player;
import me.vasuman.ator.screens.BaseScreen;
import me.vasuman.ator.screens.MenuScreen;

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
    private CustomInputListener tapListen;
    private Window pauseWindow;
    private Label resumeCounter;
    private boolean instigate = false;

    public static enum VectorType {
        Movement, Firing, CamShift
    }

    public Level() {
        super();
        manager = Manager.getInstance();
        manager.init(this);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
        MainGame.rotation.calibrate();

        InputListener backListen = new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK) {
                    new MenuScreen();
                    return true;
                }
                return false;
            }
        };
        stage.addListener(backListen);

        tapListen = new CustomInputListener(Drawer.getPerspectiveCamera());
        Skin skin = MainGame.assets.get("game.json", Skin.class);
        Label fpsCount = new Label("", skin);
        fpsCount.setPosition(100, 100);
        fpsCount.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                Label actor = (Label) getActor();
                actor.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
                return false;
            }
        });
        stage.addActor(fpsCount);
        stage.addListener(tapListen);

        pauseWindow = new Window("Paused", skin);
        pauseWindow.setSize(800, 480);
        pauseWindow.padTop(100);
        pauseWindow.padBottom(50);
        pauseWindow.setKeepWithinStage(false);

        TextButton resumeButton = new TextButton("Resume", skin);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                instigate = true;
                resume();
            }
        });
        pauseWindow.add(resumeButton);
        pauseWindow.top();

        resumeCounter = new Label("", skin);
        setActorPosition(resumeCounter, 640, 360);


        ImageButton pauseButton = new ImageButton(skin, "pause");
        setActorPosition(pauseButton, 1240, 650);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pause();
            }
        });
        stage.addActor(pauseButton);

        physics = Physics.getInstance();
        physics.init();
        //DEBUG!!
    }


    @Override
    public void pause() {
        if (paused) {
            return;
        }
        super.pause();
        pauseWindow.setPosition(240, -480);
        pauseWindow.addAction(Actions.moveTo(240, 70, 0.5f));
        stage.addActor(pauseWindow);
    }

    @Override
    public void resume() {
        if (!instigate) {
            return;
        }
        instigate = false;
        pauseWindow.addAction(Actions.sequence(Actions.moveTo(240, -480, 0.5f), new Action() {
            @Override
            public boolean act(float delta) {
                pauseWindow.remove();
                resumeCounter.addAction(Actions.sequence(
                        new Action() {
                            private float count = 4;
                            private float rate = 1;

                            @Override
                            public boolean act(float delta) {
                                if (count <= 0) {
                                    return true;
                                }
                                ((Label) getActor()).setText("" + (int) count);
                                count -= rate * delta;
                                return false;
                            }
                        },
                        new Action() {
                            @Override
                            public boolean act(float delta) {
                                getActor().remove();
                                // Clear all taps!!
                                // KINDA hackish!
                                tapListen.getTapDirection(new Vector2());
                                Level.super.resume();
                                return true;
                            }
                        }
                ));
                stage.addActor(resumeCounter);
                return true;
            }
        }));
    }

    @Override
    public void render(float delta) {
        // TODO: fix delta
        if (!paused) {
            physics.update(delta);
            manager.update(delta);
            update(delta);
        }
        Drawer.clearScreen();
        updateCamera();
        Drawer.setupCamera();
        // Draw self
        getDrawer().draw();
        // Draw all entities
        manager.draw();
        super.render(delta);
    }

    /**
     * A function that returns a specified <b>input</b> vector that is accessed via an index
     *
     * @param idx [Movement] [Firing]
     * @return A direction vector
     */

    public Vector2 getVector(VectorType idx) {
        switch (idx) {
            case CamShift:
            case Movement:
                return MainGame.rotation.getVector();
            case Firing:
                return tapListen.getTapDirection(player.getPosition());
        }
        return new Vector2();
    }

    @Override
    public void dispose() {
        Gdx.input.setCatchBackKey(false);
        Gdx.input.setCatchMenuKey(false);
        manager.clear();
        super.dispose();
    }

    public abstract void update(float delT);

    public abstract void updateCamera();
}