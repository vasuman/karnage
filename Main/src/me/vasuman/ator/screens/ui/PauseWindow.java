package me.vasuman.ator.screens.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import me.vasuman.ator.MainGame;
import me.vasuman.ator.screens.BaseScreen;
import me.vasuman.ator.screens.MenuScreen;

/**
 * Ator
 * User: vasuman
 * Date: 1/2/14
 * Time: 10:43 PM
 */
public class PauseWindow extends OverlayWindow {
    public static float PREF_WIDTH = 800;
    public static float PREF_HEIGHT = 480;
    public static float TOP_PAD = 100;
    public static final float SPACE = 50;

    public PauseWindow(Skin skin, final BaseScreen screen) {
        super("Paused", skin, screen);

        // DEBUG!!
        debug();

        TextButton resumeButton = new TextButton("Resume", skin);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.setInstigate(true);
                screen.resume();
            }
        });

        Dialog confirmQuit;

        TextButton quitButton = new TextButton("Quit", skin);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO confirm dialog
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
        add(resumeButton).align(Align.left).space(SPACE);
        add(quitButton).space(SPACE);
        row();
        add(calibrateButton).colspan(2).space(SPACE);
    }


}
