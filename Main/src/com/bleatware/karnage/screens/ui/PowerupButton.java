package com.bleatware.karnage.screens.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bleatware.karnage.GameState;
import com.bleatware.karnage.screens.LoadoutScreen;

/**
 * Karnage
 * User: vasuman
 * Date: 1/13/14
 * Time: 9:30 PM
 */
public class PowerupButton extends Button {
    private GameState.Powerup powerup;
    private Skin skin;

    public PowerupButton(final GameState.Powerup powerup, final Skin skin, final LoadoutScreen screen) {
        this.skin = skin;
        this.powerup = powerup;
        top();
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                PowerupWindow window = new PowerupWindow(powerup, skin, screen);
                screen.showWindow(window);
            }
        });
        add(new Label(powerup.getName(), skin));
        syncState();
    }

    public void syncState() {
        if (powerup.isUnlocked()) {
            if (powerup.isActivated()) {
                setStyle(skin.get("pup-active", ButtonStyle.class));
            } else {
                setStyle(skin.get("pup-unlock", ButtonStyle.class));
            }
        } else {
            setStyle(skin.get("pup-lock", ButtonStyle.class));
        }
    }
}
