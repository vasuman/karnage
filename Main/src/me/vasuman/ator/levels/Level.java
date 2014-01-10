package me.vasuman.ator.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
    public CustomInputListener inputListener;
    private Window pauseWindow;
    private Label resumeCounter;
    private boolean instigate = false;

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
        stage.addListener(inputListener);

        pauseWindow = new Window("Paused", skin);
        pauseWindow.setSize(800, 480);
        pauseWindow.padTop(100);
        pauseWindow.padBottom(20);
        pauseWindow.setKeepWithinStage(false);

        TextButton resumeButton = new TextButton("Resume", skin);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                instigate = true;
                resume();
            }
        });
        pauseWindow.add(resumeButton).space(100);

        TextButton quitButton = new TextButton("Quit", skin);
        quitButton.addListener((new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new MenuScreen();
            }
        }));
        pauseWindow.add(quitButton).space(100);
        pauseWindow.row();
        TextButton calibrate = new TextButton("Calibrate", skin);
        calibrate.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.rotation.calibrate();
            }
        });
        pauseWindow.add(calibrate);
        pauseWindow.top();

        resumeCounter = new Label("", skin);
        setActorPosition(resumeCounter, 640, 200);

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
        pauseWindow.setPosition(240, -480);
        pauseWindow.addAction(Actions.moveTo(240, 70, 0.2f));
        stage.addActor(pauseWindow);
    }

    @Override
    public void resume() {
        if (!instigate) {
            return;
        }
        instigate = false;
        pauseWindow.addAction(Actions.sequence(Actions.moveTo(240, -480, 0.2f), new Action() {
            @Override
            public boolean act(float delta) {
                pauseWindow.remove();
                resumeCounter.addAction(Actions.sequence(
                        new Action() {
                            private float count = 3;
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
                                // Clear all taps!!
                                // KINDA hackish!
                                inputListener.getTap();
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
        if (!paused) {
            physics.update(delta);
            update(delta);
            manager.update(delta);
        }
        Drawer.clearScreen();
        updateCamera();
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

    public abstract void updateCamera();
}