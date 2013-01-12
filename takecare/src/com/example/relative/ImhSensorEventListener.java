package com.example.relative;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class ImhSensorEventListener implements SensorEventListener {

	private static final String TAG = "ImhApplication";

	SensorManager sensorEventManager;
	Sensor sensor;
	Context context;
	long lastUpdate;

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Received onAccuracyChanged");
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
		// getAccelerometer(event);
		// }

	}

	public void initSensor(Context context) {

		this.context = context;

		// Obtain a reference to system-wide sensor event manager.
		sensorEventManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);

		// Get the default sensor for accel
		sensor = sensorEventManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}

	public void registerListener() {
		// Register for events.
		sensorEventManager.registerListener(this, sensor,
				SensorManager.SENSOR_DELAY_NORMAL);

	}

	public void unregisterListener() {
		// Un-Register Sensor
		sensorEventManager.unregisterListener(this);
	}

	private void getAccelerometer(SensorEvent event) {
		float[] values = event.values;
		// Movement
		float x = values[0];
		float y = values[1];
		float z = values[2];

		float accelationSquareRoot = (x * x + y * y + z * z)
				/ (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
		long actualTime = System.currentTimeMillis();
		if (accelationSquareRoot >= 3) //
		{

			if (actualTime - lastUpdate < 200) {
				return;
			}
			lastUpdate = actualTime;
			Log.d(TAG, "Device onSensorChanged");
		}
	}

}
