package com.masthaka.takecare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Vibrator;

public class ImhPowerReceiver extends BroadcastReceiver {
	private static final String TAG = "ImhPowerReceiver";
	private int count = 1;
	private long startTime = System.currentTimeMillis();
	private long currTime = System.currentTimeMillis();
	private ImhLocationListener lListener;
	private boolean registred;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		currTime = System.currentTimeMillis();

		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)
				|| intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
			if ((currTime - startTime) < 1000) {
				count += 1;
				if (count == 3) {
					lListener.requestLocationUpdates();
					// Get instance of Vibrator from current Context
					Vibrator mVibrator = (Vibrator) context
							.getSystemService(Context.VIBRATOR_SERVICE);

					// Vibrate for 300 milliseconds
					mVibrator.vibrate(300);
					count = 1;
				}

			} else {
				count = 1;
			}
			startTime = currTime;
		}
	}

	// TODO: Call this in IMH Application
	public void registerImhPowerReceiver(Context context) {
		// Register our receiver for the ACTION_SCREEN_OFF action. This will
		// make our receiver
		// code be called whenever the phone enters standby mode.
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		ifilter.addAction(Intent.ACTION_SCREEN_ON);
		lListener = new ImhLocationListener(context);
		context.registerReceiver(this, ifilter);
		registred = true;

	}

	public void suspendLocationUpdates() {
		if (lListener != null)
			lListener.suspendLocationUpdates();
	}

	public void unRegisterImhPowerReceiver(Context context) {
		lListener.suspendLocationUpdates();
		context.unregisterReceiver(this);
		registred = false;
	}

	public boolean isRegistred() {
		return registred;
	}

}