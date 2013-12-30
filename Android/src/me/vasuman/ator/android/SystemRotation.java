package me.vasuman.ator.android;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import me.vasuman.ator.MainGame;

/**
 * Ator
 * User: vasuman
 * Date: 12/29/13
 * Time: 9:48 PM
 */
public class SystemRotation implements MainGame.RotationProvider, SensorEventListener {
    private SensorManager sensorManager;
    private Sensor magSensor;
    private Sensor accSensor;
    private final float[] accValues = new float[3];
    private final float[] magValues = new float[3];
    private final float[] refAngles = new float[3];
    private final float[] retVal = new float[3];
    private final float[] absAngles = new float[3];
    private final float[] rotMatrix = new float[9];
    /* OK! So,
     * Now I have two ways to hold the device
     * 1st as originally planned -- flat on a surface style
     * 2nd traditional landscape -- in front of your face.
     * -- Configure and add more here
     */

    public SystemRotation(SensorManager manager) {
        sensorManager = manager;
        magSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magSensor, SensorManager.SENSOR_DELAY_GAME);
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
    public void calibrate() {
        getOrientation();
        aCopy(refAngles, absAngles);
    }

    @Override
    public float[] getRotation() {
        getOrientation();
        postOp(retVal, absAngles, refAngles);
        return retVal;
    }

    private void postOp(float[] result, float[] arrA, float[] arrB) {
        result[0] = arrA[1] - arrB[1];
        result[1] = arrA[2] - arrB[2];
    }

    private void aCopy(float[] arrA, float[] arrB) {
        System.arraycopy(arrB, 0, arrA, 0, 3);
    }
}