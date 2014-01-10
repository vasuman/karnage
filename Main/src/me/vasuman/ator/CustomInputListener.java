package me.vasuman.ator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import me.vasuman.ator.screens.BaseScreen;
import me.vasuman.ator.util.Projection;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Ator
 * User: vasuman
 * Date: 1/6/14
 * Time: 5:54 PM
 */
public class CustomInputListener extends InputListener {
    // TODO: Multitouch
    public static final int MAX_TOUCH = 2;
    private HashMap<Integer, Vector2> map = new HashMap<Integer, Vector2>(MAX_TOUCH);

    private Vector2 tapPosition = new Vector2();
    private boolean tap = false;
    private Camera camera;

    public CustomInputListener(PerspectiveCamera camera) {
        this.camera = camera;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        map.remove(pointer);
        tap = true;
        setPosition(tapPosition, x, y);
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        Vector2 position = map.get(pointer);
        setPosition(position, x, y);
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (getTouch() >= MAX_TOUCH) {
            return false;
        }
        Vector2 position = new Vector2();
        map.put(pointer, position);
        setPosition(position, x, y);
        return true;
    }

    private void setPosition(Vector2 position, float x, float y) {
        System.out.println(x + "," + y);
        position.set(Projection.getPlanarZ(camera.getPickRay(x, Gdx.graphics.getHeight() - y,
                0, 0, BaseScreen.resX, BaseScreen.resY)));
    }


    public int getTouch() {
        return map.size();
    }

    public boolean getTap() {
        if (tap) {
            tap = false;
            return true;
        }
        return tap;
    }

    public Vector2 getTapPosition() {
        return tapPosition.cpy();
    }

    public Vector2 getPosition(int num) {
        Iterator<Vector2> iterator = map.values().iterator();
        while (num > 0) {
            iterator.next();
        }
        return new Vector2(iterator.next());
    }

}
