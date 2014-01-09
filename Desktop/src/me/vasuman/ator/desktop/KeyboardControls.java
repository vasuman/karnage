package me.vasuman.ator.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
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
    public Vector2 getVector() {
        return new Vector2(getState(Input.Keys.A) - getState(Input.Keys.D),
                getState(Input.Keys.W) - getState(Input.Keys.S));
    }

    @Override
    public void calibrate() {

    }
}
