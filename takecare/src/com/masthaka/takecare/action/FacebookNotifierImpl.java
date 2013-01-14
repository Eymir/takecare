package com.masthaka.takecare.action;

import java.util.Random;

import winterwell.jtwitter.Twitter;
import android.content.Context;

public class FacebookNotifierImpl implements ImhINotifier{	
		private static final String TAG = "FacebookNotifierImpl";

		Twitter twitter;
		
		@Override
		public void execute(Context context) {
			Twitter.Status status = getTwitter().updateStatus(
					new Random().nextInt() + "");
		}
		
		public synchronized Twitter getTwitter() {return null;}
	}