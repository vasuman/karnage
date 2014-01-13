package com.bleatware.karnage.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.bleatware.karnage.MainGame;

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
                getState(Input.Keys.S) - getState(Input.Keys.W));
    }

    @Override
    public void calibrate() {

    }
}
