package com.example.relative;

import com.example.action.SMSNotifierImpl;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ImhLocationListener implements LocationListener {
	private static final String TAG = "ImhLocationListener";

	private LocationManager locationManager;
	private String provider;
	private Context context;
	ImhNotifierThread imhNotThread;

	public ImhLocationListener(Context context) {

		this.context = context;
		// Get the location manager
		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		// Define the criteria how to select the locatioin provider -> use
		// default
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);

	}

	public void requestLocationUpdates() {
		locationManager.requestLocationUpdates(provider, 100, 1,
				this);
		imhNotThread = new ImhNotifierThread(context, mainHandler,new SMSNotifierImpl());
		imhNotThread.start();
		Log.d(TAG, "@@@@@@@@@@@@@ Location updates initiated ");

	}

	public void suspendLocationUpdates() {
		locationManager.removeUpdates(this);
		if(imhNotThread != null)
			imhNotThread.stopImhNotifierThread();
	}

	public Location getLastKnownLocation() {
		return locationManager.getLastKnownLocation(provider);
	}

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		int lat = (int) (location.getLatitude());
		int lng = (int) (location.getLongitude());
		Log.d(TAG, "getLatitude: " + lat);
		Log.d(TAG, "getLongitude: " + lng);
		Log.d(TAG, "@@@@@@@@@@@@@ Initiating Thread............. ");
		
		Message lmessage = new Message();
		Bundle locationData = new Bundle();
		locationData.putInt("lat", lat);
		locationData.putInt("lng", lng);
		lmessage.setData(locationData);
		//Log.d(TAG, "@@@@@@@@@@@@@ Thread............. "+ imhNotThread);
		//Log.d(TAG, "@@@@@@@@@@@@@ Handler............. "+ imhNotThread.getHandler());
		//Log.d(TAG, "@@@@@@@@@@@@@ Thread............. "+ imhNotThread);
		
		imhNotThread.getHandler().sendMessage(lmessage);		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public void onProviderEnabled(String provider) {
	}

	public void onProviderDisabled(String provider) {
	}
	
	/** The main handler. */
	public Handler mainHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				Log.d(TAG, "Sent message... : "+msg.getData().getString("message"));
			}
		};
	};

}
