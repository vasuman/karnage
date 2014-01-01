package me.vasuman.ator.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import me.vasuman.ator.MainGame;

/**
 * Ator
 * User: vasuman
 * Date: 1/1/14
 * Time: 8:25 PM
 */
public class KeyboardControls implements MainGame.RotationProvider {

    private int getState(int key) {
        return (Gdx.input.isKeyPressed(key)) ? 1 : 0;
    }

    @Override
    public float[] getRotation() {
        return new float[]{-1, getState(Input.Keys.RIGHT) - getState(Input.Keys.LEFT), getState(Input.Keys.DOWN) - getState(Input.Keys.UP)};
    }
}
