package me.vasuman.ator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import me.vasuman.ator.util.Projection;

/**
 * Ator
 * User: vasuman
 * Date: 1/6/14
 * Time: 5:54 PM
 */
public class CustomInputListener extends InputListener {
    // TODO: Multitouch
    private Vector2 position;
    private boolean touch;
    private boolean tap;
    private Camera camera;

    public CustomInputListener(PerspectiveCamera camera) {
        this.camera = camera;
        this.position = new Vector2();
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        touch = false;
        tap = true;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        setTouch(x, y);
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (touch) {
            return false;
        }
        touch = true;
        tap = false;
        setTouch(x, y);
        return true;
    }

    private void setTouch(float x, float y) {
        position.set(Projection.getPlanarZ(camera.getPickRay(x, Gdx.graphics.getHeight() - y,
                0, 0, camera.viewportWidth, camera.viewportHeight)));
    }


    public Vector2 getTouchDirection(Vector2 relative) {
        if (!touch) {
            return new Vector2();
        }
        return getDirection(relative);
    }

    public Vector2 getTapDirection(Vector2 relative) {
        if (!tap) {
            return new Vector2(0, 0);
        }
        tap = false;
        return getDirection(relative);
    }

    public Vector2 getPosition() {
        return new Vector2(position);
    }

    private Vector2 getDirection(Vector2 relative) {
        Vector2 result = new Vector2(position);
        result.sub(relative);
        return result;
    }
}
