package com.example.relative;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.EditText;

public class ContactActivity extends Activity implements OnClickListener {

	private static final String TAG = "ContactActivity";
	private static final int CONTACT_PICKER_VIEW1_RESULT = 11;
	private static final int CONTACT_PICKER_VIEW2_RESULT = 22;
	private static final int CONTACT_PICKER_VIEW3_RESULT = 33;
	private static final int CONTACT_PICKER_VIEW4_RESULT = 44;
	private static final int CONTACT_PICKER_VIEW5_RESULT = 55;

	EditText editText1;
	EditText editText2;
	EditText editText3;
	EditText editText4;
	EditText editText5;
	EditText currEditText;
	
	SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imh_contacts);
		prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		editText1 = (EditText) findViewById(R.id.p1);
		editText1.setOnClickListener(this);
		editText1.setKeyListener(null);
		editText1.setText(prefs.getString(R.id.p1+"", ""));

		editText2 = (EditText) findViewById(R.id.p2);
		editText2.setOnClickListener(this);
		editText2.setKeyListener(null);
		editText2.setText(prefs.getString(R.id.p2+"", ""));

		editText3 = (EditText) findViewById(R.id.p3);
		editText3.setOnClickListener(this);
		editText3.setKeyListener(null);
		editText3.setText(prefs.getString(R.id.p3+"", ""));

		editText4 = (EditText) findViewById(R.id.p4);
		editText4.setOnClickListener(this);
		editText4.setKeyListener(null);
		editText4.setText(prefs.getString(R.id.p4+"", ""));

		editText5 = (EditText) findViewById(R.id.p5);
		editText5.setOnClickListener(this);
		editText5.setKeyListener(null);
		editText5.setText(prefs.getString(R.id.p5+"", ""));
	}

	
	@Override
	public void onClick(View view) {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

		switch (view.getId()) {
		case R.id.p1:
			startActivityForResult(intent, CONTACT_PICKER_VIEW1_RESULT);
			currEditText = editText1;
			break;
		case R.id.p2:
			startActivityForResult(intent, CONTACT_PICKER_VIEW2_RESULT);
			currEditText = editText2;
			break;
		case R.id.p3:
			startActivityForResult(intent, CONTACT_PICKER_VIEW3_RESULT);
			currEditText = editText3;
			break;
		case R.id.p4:
			startActivityForResult(intent, CONTACT_PICKER_VIEW4_RESULT);
			currEditText = editText4;
			break;
		case R.id.p5:
			startActivityForResult(intent, CONTACT_PICKER_VIEW5_RESULT);
			currEditText = editText5;
			break;
		default:
			startActivityForResult(intent, CONTACT_PICKER_VIEW1_RESULT);
		}

		// Intent myIntent = new Intent();
		// myIntent.setAction(Intent.ACTION_VIEW);
		// myIntent.setData(android.provider.Contacts.People.CONTENT_URI);
		// startActivity(myIntent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Cursor cursor = null;
			String phoneNumber = "";
			List<String> allNumbers = new ArrayList<String>();
			int phoneIdx = 0;
			try {
				Uri result = data.getData();
				Log.d(TAG, "##### Data " + result);
				String id = result.getLastPathSegment();
				cursor = getContentResolver().query(Phone.CONTENT_URI, null,
						Phone.CONTACT_ID + "=?", new String[] { id }, null);
				phoneIdx = cursor.getColumnIndex(Phone.DATA);
				if (cursor.moveToFirst()) {
					Log.d(TAG, "##### inside if ");
					while (cursor.isAfterLast() == false) {
						Log.d(TAG, "##### while ");
						phoneNumber = cursor.getString(phoneIdx);
						Log.d(TAG, "##### phoneNumber " + phoneNumber);
						allNumbers.add(phoneNumber);
						cursor.moveToNext();
					}
				} else {
					// no results actions
				}
				Log.d(TAG, "##### allNumbers " + allNumbers);
			} catch (Exception e) {
				Log.d(TAG, "##### Exception " + e);
			} finally {
				if (cursor != null) {
					cursor.close();
				}

				Log.d(TAG, "##### finally ");

				final CharSequence[] items = allNumbers
						.toArray(new String[allNumbers.size()]);
				Log.d(TAG, "##### items " + items);
				AlertDialog.Builder builder = new AlertDialog.Builder(
						ContactActivity.this);
				builder.setTitle("Choose a number");
				builder.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						String selectedNumber = items[item].toString();
						selectedNumber = selectedNumber.replace("-", "");
						currEditText.setText(selectedNumber);
						
						Editor editor = prefs.edit();
						editor.putString(currEditText.getId()+"", selectedNumber);
						editor.commit();
					}
				});
				AlertDialog alert = builder.create();
				if (allNumbers.size() > 1) {
					alert.show();
				} else {
					String selectedNumber = phoneNumber.toString();
					selectedNumber = selectedNumber.replace("-", "");
					currEditText.setText(selectedNumber);
					
					Editor editor = prefs.edit();
					editor.putString(currEditText.getId()+"", selectedNumber);
					editor.commit();
				}

				if (phoneNumber.length() == 0) {
					// no numbers found actions
				}
			}

		} else {
			// activity result error actions
		}
	}

}
