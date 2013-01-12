package com.masthaka.takecare.action;

import java.util.Random;

import android.content.Context;
import android.os.Message;
import android.telephony.SmsManager;
import android.util.Log;

public class SMSNotifierImpl implements ImhINotifier{	

	private static final String TAG = "SMSNotifierImpl";
	
	@Override
	public void execute(Context context,  Message msg) {
		SmsManager smsManager = SmsManager.getDefault();
		
		int lat = msg.getData().getInt("lat");
		int lng = msg.getData().getInt("lng");
		Log.d(TAG, "############## Sms Sent lat="+ lat +" lng="+lng);
		//smsManager.sendTextMessage("9686682256", null, new Random().nextInt() + "", null, null);
	}
	

}