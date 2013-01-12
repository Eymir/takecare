package com.masthaka.takecare.action;

import java.util.Random;

import com.example.relative.R;
import com.masthaka.takecare.ImhApplication;

import winterwell.jtwitter.Twitter;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

public class TwitterNotifierImpl implements ImhINotifier{	

	private static final String TAG = "TwitterNotifierImpl";
	Twitter twitter;
	
	@Override
	public void execute(Context context,  Message msg) {
		Twitter.Status status =  getTwitter(context).updateStatus(
				new Random().nextInt() + "");
	}
	
	public synchronized Twitter getTwitter(Context context) {
		if (this.twitter == null) {
			SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(context);
			String username = prefs.getString(context.getString(R.string.ukey), "username");
			String password = prefs.getString(context.getString(R.string.pkey), "password");
			String url = prefs.getString(context.getString(R.string.skey), "http://yamba.marakana.com/api");
			
			if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)
					&& !TextUtils.isEmpty(url)) {
				this.twitter = new Twitter(username, password);
				this.twitter.setAPIRootUrl(url);
			}
		}
		return this.twitter;
	}



}
