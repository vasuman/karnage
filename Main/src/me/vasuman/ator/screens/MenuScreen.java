package me.vasuman.ator.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import me.vasuman.ator.Drawer;
import me.vasuman.ator.iface.LabelButton;
import me.vasuman.ator.levels.TheGrid;

/**
 * Ator
 * User: vasuman
 * Date: 12/29/13
 * Time: 6:25 PM
 */
public class MenuScreen extends BaseScreen {
    private Drawer drawer;

    public MenuScreen() {
        super();
        Actor startButton1 = new LabelButton("Mode 1", resX / 2, 200);
        startButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new TheGrid();
            }
        });
        Actor startButton2 = new LabelButton("Mode 2", resX / 2, 400);
        startButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new TheGrid();
            }
        });
        stage.addActor(startButton1);
        stage.addActor(startButton2);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
