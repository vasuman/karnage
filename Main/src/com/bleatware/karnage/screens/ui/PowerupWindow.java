package com.bleatware.karnage.screens.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bleatware.karnage.GameState;
import com.bleatware.karnage.MainGame;
import com.bleatware.karnage.screens.LoadoutScreen;

/**
 * Karnage
 * User: vasuman
 * Date: 1/14/14
 * Time: 9:30 PM
 */
public class PowerupWindow extends OverlayWindow {
    private GameState.Powerup powerup;
    private Skin skin;
    private LoadoutScreen screen;

    public PowerupWindow(GameState.Powerup powerup, Skin skin, LoadoutScreen screen) {
        super(powerup.getName(), skin, screen);
        this.powerup = powerup;
        this.skin = skin;
        this.screen = screen;
        initWindow();
    }

    public void initWindow() {
        clearChildren();
        top();
        add(new Label(powerup.getDescription(), skin, "small-font", "white")).expand();
        row();
        Table buttonGroup = new Table();
        if (!powerup.isUnlocked()) {
            add(new Label("Cost: " + powerup.getCost(), skin, "small-font", "blue")).colspan(2);
            row();
            if (powerup.getCost() < MainGame.state.getCoins()) {
                TextButton buy = new TextButton("Unlock", skin, "dialog");
                buttonGroup.add(buy);
                buy.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        MainGame.state.tryUnlock(powerup);
                        initWindow();
                        screen.updateButtons();
                    }
                });
            }
        } else {
            if (!powerup.isActivated()) {
                TextButton activate = new TextButton("Activate", skin, "dialog");
                buttonGroup.add(activate);
                activate.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        MainGame.state.activate(powerup);
                        initWindow();
                        screen.updateButtons();
                    }
                });
            } else {
                TextButton deactivate = new TextButton("Deactivate", skin, "dialog");
                buttonGroup.add(deactivate);
                deactivate.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        MainGame.state.deactivate(powerup);
                        initWindow();
                        screen.updateButtons();
                    }
                });
            }
        }
        TextButton exit = new TextButton("Quit", skin, "dialog");
        buttonGroup.add(exit).space(SPACE);
        add(buttonGroup).padBottom(BOTTOM_PAD).colspan(2);
        row();
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.removeWindow();
            }
        });
    }

}
