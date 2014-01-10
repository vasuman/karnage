package me.vasuman.ator.android;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.badlogic.gdx.math.Vector2;
import me.vasuman.ator.MainGame;

/**
 * Ator
 * User: vasuman
 * Date: 12/29/13
 * Time: 9:48 PM
 */
public class SystemRotation implements MainGame.RotationProvider, SensorEventListener {
    private Sensor magSensor;
    private Sensor accSensor;

    private final float[] accValues = new float[3];
    private final float[] magValues = new float[3];
    private final float[] rotMatrix = new float[9];

    private final float[] absAngles = new float[3];
    private final float[] fixAngles = new float[3];
    /* OK! So,
     * Now I have two ways to hold the device
     * 1st as originally planned -- flat on a surface style
     * 2nd traditional landscape -- in front of your face.
     * -- Configure and add more here
     */

    public SystemRotation(SensorManager manager) {
        magSensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accSensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(this, magSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == magSensor) {
            aCopy(magValues, event.values);
        } else if (event.sensor == accSensor) {
            aCopy(accValues, event.values);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void getOrientation() {
        SensorManager.getRotationMatrix(rotMatrix, null, accValues, magValues);
        SensorManager.getOrientation(rotMatrix, absAngles);
    }

    @Override
    public Vector2 getVector() {
        getOrientation();
        return new Vector2(absAngles[1] - fixAngles[1], fixAngles[2] - absAngles[2]).limit(LIMIT);
    }

    @Override
    public void calibrate() {
        aCopy(fixAngles, absAngles);
    }

    private void aCopy(float[] arrA, float[] arrB) {
        System.arraycopy(arrB, 0, arrA, 0, 3);
    }
}
