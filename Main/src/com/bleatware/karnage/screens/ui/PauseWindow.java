package com.bleatware.karnage.screens.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bleatware.karnage.MainGame;
import com.bleatware.karnage.screens.BaseScreen;
import com.bleatware.karnage.screens.MenuScreen;

/**
 * Ator
 * User: vasuman
 * Date: 1/2/14
 * Time: 10:43 PM
 */
public class PauseWindow extends OverlayWindow {

    public PauseWindow(final Skin skin, final BaseScreen screen) {
        super("Paused", skin, screen);

        // DEBUG!!
        debug();

        ImageButton resumeButton = new ImageButton(skin, "resume");
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.setInstigate(true);
                screen.resume();
            }
        });


        ImageButton quitButton = new ImageButton(skin, "quit");
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO confirm dialog
                /*
                Dialog confirmQuit = new Dialog("Confirm", skin);
                confirmQuit.setModal(true);
                screen.getStage().addActor(confirmQuit);
                */
                new MenuScreen();
            }
        });

        TextButton calibrateButton = new TextButton("Calibrate", skin);
        calibrateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.rotation.calibrate();
            }
        });

        top();
        add(resumeButton).space(SPACE);
        add(quitButton).space(SPACE);
        row();
        add(calibrateButton).colspan(2).space(SPACE);
    }


}
