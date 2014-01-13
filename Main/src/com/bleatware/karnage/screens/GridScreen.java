package com.bleatware.karnage.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.bleatware.karnage.Config;
import com.bleatware.karnage.Drawable;
import com.bleatware.karnage.Drawer;
import com.bleatware.karnage.MainGame;
import com.bleatware.karnage.levels.CustomLevel;

/**
 * Ator
 * User: vasuman
 * Date: 1/9/14
 * Time: 2:03 PM
 */
public class GridScreen extends BaseScreen implements Drawable {
    private Drawer drawer;
    private static int GRID_SPACE = 80;
    private static int OFFSET = 3;

    public GridScreen() {
        PerspectiveCamera camera = Drawer.getPerspectiveCamera();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, Config.CAM_ELEVATION);
        drawer = new Drawer() {
            @Override
            public void draw() {
                shapeDraw.setColor(Color.GREEN);
                shapeDraw.begin(ShapeRenderer.ShapeType.Line);
                for (int i = -OFFSET; i <= OFFSET + (resX / GRID_SPACE); i++) {
                    int posX = i * GRID_SPACE;
                    shapeDraw.line(posX, -OFFSET * GRID_SPACE, posX, resY + OFFSET * GRID_SPACE);
                }
                for (int j = -OFFSET; j <= OFFSET + (resY / GRID_SPACE); j++) {
                    int posY = j * GRID_SPACE;
                    shapeDraw.line(-OFFSET * GRID_SPACE, posY, resX + OFFSET * GRID_SPACE, posY);
                }
                shapeDraw.end();
            }
        };
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        PerspectiveCamera camera = Drawer.getPerspectiveCamera();
        Vector3 offset = new Vector3(MainGame.rotation.getVector().scl(-Config.MOTION_IMPACT), -1);
        CustomLevel.LPFSet(camera.direction, offset);
        camera.up.set(0, 1, 0);
    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }
}
