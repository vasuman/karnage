package me.vasuman.ator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 6:07 PM
 */
public abstract class Drawer {
    protected static SpriteBatch sprite = new SpriteBatch();
    protected static ShapeRenderer shape = new ShapeRenderer();
    protected static GL20 glCtx = Gdx.graphics.getGL20();
    protected static OrthographicCamera camera;
    protected static final Matrix4 absolute = new Matrix4();

    public static void setCamera(OrthographicCamera camera) {
        Drawer.camera = camera;
        absolute.set(camera.combined);
    }

    public static void setViewport(Vector2 position) {
        camera.position.set(position, camera.position.z);
        camera.update();
    }

    public static void clear() {
        glCtx.glClearColor(0, 0, 0, 0);
        glCtx.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private static void update(Matrix4 matrix) {
        shape.setProjectionMatrix(matrix);
        sprite.setProjectionMatrix(matrix);
    }


    public abstract void draw();

    protected void debugBox(float x, float y, float width, float height) {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.box(x - width / 2, y - height / 2, 1, width, height, 1);
        shape.end();
    }

    protected void debugLine(float x1, float y1, float x2, float y2) {
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.line(x1, y1, x2, y2);
        shape.end();
    }

    protected void debugCircle(float x, float y, float radius) {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.circle(x, y, radius);
        shape.end();
    }

    protected void debugColor(Color color) {
        shape.setColor(color);
    }

    protected void offset(boolean relative) {
        if (relative) {
            update(camera.combined);
        } else {
            update(absolute);
        }
    }
}
