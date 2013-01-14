package com.masthaka.takecare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorEventListener;
import android.util.Log;

public class ImhBroadcastReceiver extends BroadcastReceiver {

	private ImhSensorEventListener sensorEventListener;
	Context context;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		// Check action just to be on the safe side.
		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
			Log.v("shake mediator screen off", "trying re-registration");
			// Unregisters the listener and registers it again.

			sensorEventListener.unregisterListener();
			sensorEventListener.registerListener();
		}
	}

	public ImhBroadcastReceiver(SensorEventListener sensorEventListener) {
		this.sensorEventListener = (ImhSensorEventListener) sensorEventListener;
	}

	// TODO: Call this in IMH Application
	public void registerForScreenOffEvent() {
		// Register our receiver for the ACTION_SCREEN_OFF action. This will
		// make our receiver
		// code be called whenever the phone enters standby mode.
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		context.registerReceiver(this, filter);
	}

}