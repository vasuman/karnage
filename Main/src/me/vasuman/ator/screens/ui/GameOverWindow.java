package me.vasuman.ator.screens.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import me.vasuman.ator.screens.BaseScreen;
import me.vasuman.ator.screens.MenuScreen;

/**
 * Ator
 * User: vasuman
 * Date: 1/13/14
 * Time: 12:18 AM
 */
public class GameOverWindow extends OverlayWindow {
    public GameOverWindow(Skin skin, int score, BaseScreen screen) {
        super("Game Over", skin, screen);
        top();
        add("Score: " + score).space(SPACE);
        row();
        TextButton quitButton = new TextButton("Exit", skin);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new MenuScreen();
            }
        });
        add(quitButton).space(SPACE);
    }
}
