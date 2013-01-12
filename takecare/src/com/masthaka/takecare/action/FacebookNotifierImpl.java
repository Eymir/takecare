package com.masthaka.takecare.action;

import java.util.Random;

import android.content.Context;
import android.os.Message;

import winterwell.jtwitter.Twitter;

public class FacebookNotifierImpl implements ImhINotifier{	
		private static final String TAG = "FacebookNotifierImpl";

		Twitter twitter;
		
		@Override
		public void execute(Context context,  Message msg) {
			Twitter.Status status = getTwitter().updateStatus(
					new Random().nextInt() + "");
		}
		
		public synchronized Twitter getTwitter() {return null;}
	}