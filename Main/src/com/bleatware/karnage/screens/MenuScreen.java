package com.bleatware.karnage.screens;

import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bleatware.karnage.DataProvider;
import com.bleatware.karnage.Drawable;
import com.bleatware.karnage.Drawer;
import com.bleatware.karnage.MainGame;
import com.bleatware.karnage.levels.CustomLevel;

/**
 * Ator
 * User: vasuman
 * Date: 12/29/13
 * Time: 6:25 PM
 */
public class MenuScreen extends GridScreen implements LoadScreen.LoadCallback, Drawable {
    private Drawer drawer;

    public MenuScreen() {
        Skin skin = MainGame.assets.get("game.json", Skin.class);
        TextButton startButton = new TextButton("Start", skin, "blue-r");
        TextButton loadoutButton = new TextButton("Loadout", skin, "green-r");
        Table layout = new Table(skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startLoading();
            }
        });
        loadoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new LoadoutScreen();
            }
        });
        // DEBUG!
        layout.debug();
        layout.setFillParent(true);
        stage.addActor(layout);
        layout.add(startButton);
        layout.row().space(100);
        layout.add(loadoutButton);
        drawer = new Drawer() {
            @Override
            public void draw() {
                MenuScreen.super.getDrawer().draw();
                drawText("Bleatware Studios", resX / 2, resY / 3);
            }
        };
    }

    @Override
    public void render(float delta) {
        Drawer.clearScreen();
        Drawer.setupCamera();
        getDrawer().draw();
        Drawer.finishDraw();
        super.render(delta);
    }

    private void startLoading() {
        // ADD all dependencies here!
        new LoadScreen(MenuScreen.this, DataProvider.getGridDependencies());

    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void doneLoading() {
        new CustomLevel(DataProvider.getGridDef());
        Drawer.getEnvironment().set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1));
    }
}
