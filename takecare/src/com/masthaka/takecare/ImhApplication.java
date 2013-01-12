package com.masthaka.takecare;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.os.Message;
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
		if(imhPower!=null)
		imhPower.suspendLocationUpdates();
		Toast.makeText(context, "Suspended notification", Toast.LENGTH_SHORT)
				.show();
		Log.i(TAG, "Suspended notification");
	}

	//
	// public boolean sendNotifications(String msg) {
	// Log.d(TAG, "Sending notifications");
	// // SmsManager smsManager = SmsManager.getDefault();
	// // smsManager.sendTextMessage("phoneNo", null, msg, null, null);
	// // new ImhNotifierThread(this, mainHandler, new
	// // TwitterNotifierImpl()).start();
	// Log.d(TAG, "Successfully sent all notifications");
	// return true;
	// }
	//
	@Override
	public void onTerminate() {
		super.onTerminate();
		imhPower.unRegisterImhPowerReceiver(context);
		Log.i(TAG, "Application terminated");
	}

}