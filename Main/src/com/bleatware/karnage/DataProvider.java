package com.bleatware.karnage;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bleatware.karnage.levels.CustomLevel;

import java.util.Random;

/**
 * Karnage
 * User: vasuman
 * Date: 1/13/14
 * Time: 9:44 PM
 */
public class DataProvider {
    public static CustomLevel.LevelDef getGridDef() {
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
        def.playerDef = MainGame.state.getPlayerDef();
        def.playerDef.position = new Vector2(def.width / 2, def.height / 2);
        return def;
    }

    public static AssetDescriptor[] getGridDependencies() {
        return new AssetDescriptor[]{
                new AssetDescriptor<Model>("player-base.g3db", Model.class),
                new AssetDescriptor<Model>("canon.g3db", Model.class),
                new AssetDescriptor<Model>("simple-glob.g3db", Model.class),
                new AssetDescriptor<Model>("tetrahedron.g3db", Model.class),
                new AssetDescriptor<Model>("icosahedron.g3db", Model.class),
                new AssetDescriptor<Model>("shoot-glob.g3db", Model.class),
                new AssetDescriptor<TextureAtlas>("game.atlas", TextureAtlas.class)};
    }
}
