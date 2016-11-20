package org.vanguardmatrix.engine.android;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class ShakeEventManager implements SensorEventListener {

    private static final int MOV_COUNTS = 3;
    private static final int MOV_THRESHOLD = 10;
    private static final float ALPHA = 0.8F;
    private static final int SHAKE_WINDOW_TIME_INTERVAL = 500; // milliseconds
    private SensorManager sManager;
    private Sensor s;
    // Gravity force on x,y,z axis
    private float gravity[] = new float[3];

    private int counter;
    private long firstMovTime;
    private ShakeListener listener;

    public ShakeEventManager() {
    }

    public void setListener(ShakeListener listener) {
        this.listener = listener;
    }

    public void init(Context ctx) {
        sManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
        s = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        register();
    }

    public void register() {
        sManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float maxAcc = calcMaxAcceleration(sensorEvent);
        // Log.d("SwA", "Max Acc ["+maxAcc+"]");
        if (maxAcc >= MOV_THRESHOLD) {
            if (counter == 0) {
                counter++;
                firstMovTime = System.currentTimeMillis();
                Log.d("SwA", "First mov..");
            } else {
                long now = System.currentTimeMillis();
                if ((now - firstMovTime) < SHAKE_WINDOW_TIME_INTERVAL)
                    counter++;
                else {
                    resetAllData();
                    // counter++;
                    return;
                }
                Log.d("SwA", "Mov counter [" + counter + "]");

                if (counter >= MOV_COUNTS)
                    if (listener != null)
                        listener.onShake();
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void deregister() {
        sManager.unregisterListener(this);
    }

    private float calcMaxAcceleration(SensorEvent event) {
        gravity[0] = calcGravityForce(event.values[0], 0);
        gravity[1] = calcGravityForce(event.values[1], 1);
        gravity[2] = calcGravityForce(event.values[2], 2);

        float accX = event.values[0] - gravity[0];
        float accY = event.values[1] - gravity[1];
        float accZ = event.values[2] - gravity[2];

        float max1 = Math.max(accX, accY);
        return Math.max(max1, accZ);
    }

    // Low pass filter
    private float calcGravityForce(float currentVal, int index) {
        return ALPHA * gravity[index] + (1 - ALPHA) * currentVal;
    }

    private void resetAllData() {
        Log.d("SwA", "Reset all data");
        counter = 0;
        firstMovTime = System.currentTimeMillis();
    }

    public static interface ShakeListener {
        public void onShake();
    }
}
