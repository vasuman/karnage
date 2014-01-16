package com.bleatware.karnage.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bleatware.karnage.Drawer;
import com.bleatware.karnage.GameState;
import com.bleatware.karnage.MainGame;
import com.bleatware.karnage.screens.ui.PowerupButton;
import com.bleatware.karnage.screens.ui.PowerupWindow;
import com.bleatware.karnage.screens.ui.RefreshLabel;

import java.util.ArrayList;

/**
 * Karnage
 * User: vasuman
 * Date: 1/13/14
 * Time: 9:17 PM
 */
public class LoadoutScreen extends GridScreen {
    public static final float PAD_SCROLL = 100;
    private PowerupWindow window;
    private ArrayList<PowerupButton> buttons = new ArrayList<PowerupButton>();

    public LoadoutScreen() {
        Skin skin = MainGame.assets.get("game.json", Skin.class);
        Table table = new Table(skin);
        table.setFillParent(true);
        table.top();
        table.debug();
        RefreshLabel label = new RefreshLabel(skin, new RefreshLabel.RefreshTrigger() {
            @Override
            public String getText() {
                return "Coins: " + MainGame.state.getCoins();
            }
        });
        table.add(label);
        table.row();
        Table buttonLayout = new Table();
        ScrollPane pane = new ScrollPane(buttonLayout);
        table.add(pane).pad(PAD_SCROLL);
        for (GameState.Powerup powerup : MainGame.state.getPowerups()) {
            PowerupButton button = new PowerupButton(powerup, skin, this);
            buttonLayout.add(button);
            buttons.add(button);
        }
        pane.layout();
        stage.addActor(table);
        ImageButton backButton = new ImageButton(skin, "back");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new MenuScreen();
            }
        });
        backButton.setPosition(0, stage.getHeight() - backButton.getHeight());
        stage.addActor(backButton);
        Gdx.input.setCatchBackKey(true);
        stage.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.BACK) {
                    if (window != null) {
                        removeWindow();
                    } else {
                        new MenuScreen();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void removeWindow() {
        window.remove();
        window = null;
    }

    @Override
    public void render(float delta) {
        Drawer.clearScreen();
        Drawer.setupCamera();
        getDrawer().draw();
        Drawer.finishDraw();
        super.render(delta);
    }

    public void showWindow(PowerupWindow window) {
        if (this.window == null) {
            stage.addActor(window);
            this.window = window;
        }
    }

    public void updateButtons() {
        for (PowerupButton button : buttons) {
            button.syncState();
        }
    }
}
