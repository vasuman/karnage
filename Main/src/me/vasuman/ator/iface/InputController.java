package me.vasuman.ator.iface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 5:33 PM
 */
public class InputController {
    public Vector3 getGyro() {
        return new Vector3(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerZ());
    }
}