package me.vasuman.ator.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import me.vasuman.ator.Drawer;
import me.vasuman.ator.util.Point;

import java.util.ArrayList;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 5:33 PM
 */
public class TouchInput implements InputProcessor {

    public static final int MAX_TOUCH = 5;
    protected ArrayList<SensorRegion> regions;
    protected int[] deref;

    public TouchInput() {
        regions = new ArrayList();
        deref = new int[MAX_TOUCH];
        for (int i = 0; i < MAX_TOUCH; i++) {
            deref[i] = -1;
        }
    }

    public void addRegion(SensorRegion region) {
        regions.add(region);
    }

    public Vector3 getGyro() {
        return new Vector3(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerZ());
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (pointer >= MAX_TOUCH) {
            return false;
        }
        Point actual = getVirtual(screenX, screenY);
        for (int i = 0; i < regions.size(); i++) {
            SensorRegion region = regions.get(i);
            if (region.getPointer() == -1 && region.touch(actual.x, actual.y, pointer)) {
                deref[pointer] = i;
                return true;
            }
        }
        return false;
    }

    private Point getVirtual(int screenX, int screenY) {
        // TODO: optimize if necessary
        Vector3 actual = Drawer.unTouch(screenX, screenY);
        return new Point((int) actual.x, (int) actual.y);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (pointer >= MAX_TOUCH) {
            return false;
        }
        int position = deref[pointer];
        if (position == -1) {
            return false;
        }
        Point actual = getVirtual(screenX, screenY);
        SensorRegion region = regions.get(position);
        region.up(actual.x, actual.y);
        deref[pointer] = -1;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (pointer >= MAX_TOUCH) {
            return false;
        }
        int position = deref[pointer];
        if (position == -1) {
            return false;
        }
        Point actual = getVirtual(screenX, screenY);
        SensorRegion region = regions.get(position);
        region.update(actual.x, actual.y);
        return false;
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}