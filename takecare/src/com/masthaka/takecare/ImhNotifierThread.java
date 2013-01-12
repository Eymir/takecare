package com.masthaka.takecare;

import com.masthaka.takecare.action.ImhINotifier;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ImhNotifierThread extends Thread {

	private static final String TAG = "NotifierThread";
	
	// reference to mainHandler from the mainThread
	private Handler parentHandler;
	
	// Holds Notifier
	private ImhINotifier notifier;
	
	private Context context;
	private boolean running = true;
	//private Message locationMessage;
	
    // local Handler manages messages for MyThread 
    // received from mainThread
	private Handler thisThreadHandler = new Handler(){
		public void handleMessage(Message msg) {
			//locationMessage = msg;
			interrupt();
		}
	};

	//constructor
	public ImhNotifierThread(Context context, Handler parentHandler, ImhINotifier notifier){
		// initialize instance vars
		this.parentHandler = parentHandler;
		this.notifier = notifier;
		this.context = context;
	}

	public void stopImhNotifierThread(){
		running = false;
	}
	
	@Override
	public void run() {

		Message messageToParent = new Message();
		Bundle messageData = new Bundle();
		boolean process = false;
		while(running){
			//Log.d(TAG, "%%%%%%%%%%% running %%%%%%%%%%% "+ running);
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// DoNothing
				process = true;
			}
			Log.d(TAG, "%%%%%%%%%%% process %%%%%%%%%%% "+ process);
			if(process){
				try {
					
					// Thread loop
						// prepare a message with the updated text
					
					double lat = ((ImhApplication)context.getApplicationContext()).getLocation().getLatitude();
					double lng = ((ImhApplication)context.getApplicationContext()).getLocation().getLongitude();
					Log.d(TAG, "$$$$$$$$$$$$ In Thread= " + lat + " lng= " + lng);
						notifier.execute(context);
						messageData.putString("message", "Done Successful");
						messageToParent.setData(messageData);
				} 
				catch (Exception e) {
					messageData.putString("message", "Unsuccessful "+e.getMessage());
					messageToParent.setData(messageData);
					// Logging exception
					Log.e("ImhNotifierThread","Main loop exception - " + e);
				}
				// send message to mainThread
				parentHandler.sendMessage(messageToParent);
				process = false;
			}
			
		}
	}

	// getter for local Handler
	public Handler getHandler() {
		return thisThreadHandler;
	}
}