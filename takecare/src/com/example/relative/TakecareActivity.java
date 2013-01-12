package com.example.relative;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TakecareActivity extends Activity implements OnClickListener {

	private static final String TAG = "RelActivity";

	ImhPowerReceiver powerControl;
	ToggleButton onoff;

	SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// bundle = savedInstanceState
		setContentView(R.layout.imh);

		Button contacts = (Button) findViewById(R.id.contacts);
		contacts.setOnClickListener(this);

		Button settings = (Button) findViewById(R.id.setting);
		settings.setOnClickListener(this);

		Button help = (Button) findViewById(R.id.help);
		help.setOnClickListener(this);

		Button info = (Button) findViewById(R.id.info);
		info.setOnClickListener(this);

		onoff = (ToggleButton) findViewById(R.id.onoff);
		onoff.setOnClickListener(this);
		prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		onoff.setChecked(prefs.getBoolean("onoff", false));

		powerControl = new ImhPowerReceiver();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// if(powerControl!=null)
		// powerControl.unRegisterImhPowerReceiver(getApplicationContext());
	}

	@Override
	public void onPause() {
		super.onPause();
		// bundle.putBoolean("ToggleButtonState", toggleButton.isChecked());
	}

	@Override
	public void onResume() {
		super.onResume();
		onoff.setChecked(prefs.getBoolean("onoff", false));
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

		switch (view.getId()) {
		case R.id.contacts:
			startActivity(new Intent(this, ContactActivity.class));
			break;
		case R.id.setting:
			startActivity(new Intent(this, ImhPrefActivity.class));
			break;
		case R.id.help:
			// startActivity(new Intent(this, HelpActivity.class));
			break;
		case R.id.info:
			// startActivity(new Intent(this, InfoActivity.class));
			break;
		case R.id.onoff:
			Button tbutton = (ToggleButton) view;
			boolean on = ((ToggleButton) view).isChecked();

			if (on) {
				((ImhApplication)getApplication()).registerImhPowerReceiver();

				createNotification();

			} else {
				((ImhApplication)getApplication()).suspendLocationUpdates();
				
				NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				notificationManager.cancel(2013);

			}
			Editor editor = prefs.edit();
			editor.putBoolean("onoff", on);
			editor.commit();

			break;
		default:
			// startService(new Intent(this, ImhService.class));
		}

	}

	public void createNotification() {

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("Take Care App")
				.setContentText("Take Care App is running. Click to manage.");
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, TakecareActivity.class);

		// The stack builder object will contain an artificial back stack for
		// the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(TakecareActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		Notification notification = mBuilder.build();
		notification.tickerText = "Take Care App notification initiated";
		mNotificationManager.notify(2013, notification);
	}

}