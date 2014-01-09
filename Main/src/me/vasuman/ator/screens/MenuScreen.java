package me.vasuman.ator.screens;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import me.vasuman.ator.Drawer;
import me.vasuman.ator.MainGame;
import me.vasuman.ator.entities.Player;
import me.vasuman.ator.levels.CustomLevel;

import java.util.Random;

/**
 * Ator
 * User: vasuman
 * Date: 12/29/13
 * Time: 6:25 PM
 */
public class MenuScreen extends BaseScreen implements LoadScreen.LoadCallback {
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
                        new AssetDescriptor("player-base.g3db", Model.class),
                        new AssetDescriptor("canon.g3db", Model.class),
                        new AssetDescriptor("tetrahedron.g3db", Model.class),
                        new AssetDescriptor("game.atlas", TextureAtlas.class));
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
        def.spawnRegions = new Rectangle[]{new Rectangle(50, 50, 100, 100)};
        def.lights = new BaseLight[]{
                new DirectionalLight().set(1, 0.6f, 0.3f, 0.6f, 1, 0),
                new DirectionalLight().set(0.7f, 1, 1, -0.6f, -1, 0),
                new DirectionalLight().set(0.7f, 1, 1, -0.8f, -0.3f, -1)
        };
        Player.PlayerDef playerDef = new Player.PlayerDef();
        playerDef.position = new Vector2(def.width / 2, def.height / 2);
        playerDef.damp = 0.8f;
        playerDef.modelPath = "player-base.g3db";
        playerDef.size = 16;
        playerDef.speed = 250;
        def.playerDef = playerDef;
        new CustomLevel(def);
        Drawer.getEnvironment().set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1));
        //new TheGrid(true);
    }
}
