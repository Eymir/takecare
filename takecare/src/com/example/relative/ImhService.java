package com.example.relative;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ImhService extends Service {

	private static final String TAG = "ImhService";
	private ImhApplication imh;

	public ImhService() {
		super();
		Log.d(TAG, "UpdaterService constructed");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreated");
		imh = (ImhApplication) getApplication();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand !!!");
		super.onStartCommand(intent, flags, startId);

		// /imh.sendNotifications("some message");
		// PReceiver p = new PReceiver();
		imh.registerImhPowerReceiver();
		return START_STICKY;

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		imh.unRegisterImhPowerReceiver();
		// Unregister our receiver.
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void display(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

	}

}
