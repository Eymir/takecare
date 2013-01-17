package com.masthaka.takecare;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

public class ImhApplication extends Application {
	private static final String TAG = "ImhApplication";
	private Context context;
	private ImhPowerReceiver imhPower;
	Location location;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "Application started");
		context = getApplicationContext();
		imhPower = new ImhPowerReceiver();

	}

	public void registerImhPowerReceiver() {
		imhPower.registerImhPowerReceiver(context);
	}

	public void unRegisterImhPowerReceiver() {
		imhPower.unRegisterImhPowerReceiver(context);
	}

	public void suspendLocationUpdates() {
		if (imhPower != null)
			imhPower.suspendLocationUpdates();
		Toast.makeText(context, "App is turned off", Toast.LENGTH_SHORT)
				.show();
		Log.i(TAG, "App is turned off");
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		imhPower.unRegisterImhPowerReceiver(context);
		Log.i(TAG, "Application terminated");
	}

}