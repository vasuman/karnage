package me.vasuman.ator.screens;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import me.vasuman.ator.Drawer;
import me.vasuman.ator.MainGame;
import me.vasuman.ator.levels.TheGrid;

/**
 * Ator
 * User: vasuman
 * Date: 12/29/13
 * Time: 6:25 PM
 */
public class MenuScreen extends BaseScreen implements LoadScreen.LoadCallback {
    private Drawer drawer;

    public MenuScreen() {
        super();
        Skin skin = MainGame.assets.get("game.json", Skin.class);
        Drawer.setFont(skin.getFont("default-font"));
        TextButton startButton = new TextButton("Start", skin.get(TextButton.TextButtonStyle.class));
        setActorPosition(startButton, 640, 400);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new LoadScreen(MenuScreen.this,
                        new AssetDescriptor("triframe-base.g3db", Model.class),
                        new AssetDescriptor("canon.g3db", Model.class));
            }
        });
        stage.addActor(startButton);
    }


    @Override
    public void render(float delta) {
        Drawer.clearScreen();

        super.render(delta);
    }

    @Override
    public void doneLoading() {
        new TheGrid(true);
    }
}
