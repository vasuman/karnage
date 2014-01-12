package me.vasuman.ator.screens;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import me.vasuman.ator.Drawable;
import me.vasuman.ator.Drawer;
import me.vasuman.ator.MainGame;
import me.vasuman.ator.entities.Extension;
import me.vasuman.ator.entities.GyroMove;
import me.vasuman.ator.entities.Player;
import me.vasuman.ator.entities.TapGun;
import me.vasuman.ator.levels.CustomLevel;

import java.util.Random;

/**
 * Ator
 * User: vasuman
 * Date: 12/29/13
 * Time: 6:25 PM
 */
public class MenuScreen extends GridScreen implements LoadScreen.LoadCallback, Drawable {
    private Drawer drawer;

    public MenuScreen() {
        super();
        Skin skin = MainGame.assets.get("game.json", Skin.class);
        TextButton startButton = new TextButton("Start", skin.get(TextButton.TextButtonStyle.class));
        Table layout = new Table(skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startLoading();
            }
        });
        // DEBUG!
        layout.debug();
        layout.setFillParent(true);
        stage.addActor(layout);
        layout.add(startButton);
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
        new LoadScreen(MenuScreen.this,
                new AssetDescriptor<Model>("player-base.g3db", Model.class),
                new AssetDescriptor<Model>("canon.g3db", Model.class),
                new AssetDescriptor<Model>("tetrahedron.g3db", Model.class),
                new AssetDescriptor<Model>("icosahedron.g3db", Model.class),
                new AssetDescriptor<TextureAtlas>("game.atlas", TextureAtlas.class));

    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void doneLoading() {
        TextureAtlas atlas = MainGame.assets.get("game.atlas", TextureAtlas.class);
        CustomLevel.LevelDef def = new CustomLevel.LevelDef();
        def.tX = 128;
        def.tY = 128;

        Array<TextureAtlas.AtlasRegion> regions = atlas.findRegions("map/grid");
        def.tiles = new TextureRegion[regions.size];
        for (int i = 0; i < regions.size; i++) {
            def.tiles[i] = regions.get(i);
        }

        def.width = 3072;
        def.height = 2048;
        def.seed = new Random();
        def.buildBoundraies(10);
        def.lights = new BaseLight[]{
                new DirectionalLight().set(0.6f, 0.6f, 0.6f, 0, 1, 0),
                new DirectionalLight().set(0.7f, 0.7f, 0.7f, 0, -1, 0),
                new DirectionalLight().set(0.1f, 0.1f, 0.1f, 0, 0, -1)
        };
        Player.PlayerDef playerDef = new Player.PlayerDef();
        playerDef.position = new Vector2(def.width / 2, def.height / 2);
        playerDef.damp = 0.8f;
        playerDef.modelPath = "player-base.g3db";
        playerDef.size = 16;
        playerDef.health = 10;
        playerDef.builders = new Extension.ExtensionBuilder[]{
                new GyroMove.GyroMoveBuilder(450),
                new TapGun.TapGunBuilder()
        };
        def.playerDef = playerDef;
        new CustomLevel(def);
        Drawer.getEnvironment().set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1));
    }
}
