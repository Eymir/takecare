package com.masthaka.takecare;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.provider.ContactsContract;
import android.util.Log;

public class ImhContactPrefActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {

	private static final String TAG = "ImhContactPrefActivity";

	private static final String DEFAULT_CONTACT_TITLE = "Contact";
	private static final String DEFAULT_CONTACT_SUMMARY = "Tap to set contact from address book";

	public static final String IMH_CONTACT1 = "IMH_CONTACT1";
	public static final String IMH_CONTACT2 = "IMH_CONTACT2";
	public static final String IMH_CONTACT3 = "IMH_CONTACT3";
	public static final String IMH_CONTACT4 = "IMH_CONTACT4";
	public static final String IMH_CONTACT5 = "IMH_CONTACT5";

	private Preference c1p;
	private Preference c2p;
	private Preference c3p;
	private Preference c4p;
	private Preference c5p;

	private Preference current;

	private SharedPreferences contactPreference;
	String name = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.contacts);

		c1p = (Preference) getPreferenceScreen().findPreference(IMH_CONTACT1);
		c2p = (Preference) getPreferenceScreen().findPreference(IMH_CONTACT2);
		c3p = (Preference) getPreferenceScreen().findPreference(IMH_CONTACT3);
		c4p = (Preference) getPreferenceScreen().findPreference(IMH_CONTACT4);
		c5p = (Preference) getPreferenceScreen().findPreference(IMH_CONTACT5);
		contactPreference = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		c1p.setTitle(contactPreference.getString(c1p.getKey() + ".nme",
				DEFAULT_CONTACT_TITLE + " 1"));
		c1p.setSummary(contactPreference.getString(c1p.getKey() + ".num",
				DEFAULT_CONTACT_SUMMARY));

		c2p.setTitle(contactPreference.getString(c2p.getKey() + ".nme",
				DEFAULT_CONTACT_TITLE + " 2"));
		c2p.setSummary(contactPreference.getString(c2p.getKey() + ".num",
				DEFAULT_CONTACT_SUMMARY));

		c3p.setTitle(contactPreference.getString(c3p.getKey() + ".nme",
				DEFAULT_CONTACT_TITLE + " 3"));
		c3p.setSummary(contactPreference.getString(c3p.getKey() + ".num",
				DEFAULT_CONTACT_SUMMARY));

		c4p.setTitle(contactPreference.getString(c4p.getKey() + ".nme",
				DEFAULT_CONTACT_TITLE + " 4"));
		c4p.setSummary(contactPreference.getString(c4p.getKey() + ".num",
				DEFAULT_CONTACT_SUMMARY));

		c5p.setTitle(contactPreference.getString(c5p.getKey() + ".nme",
				DEFAULT_CONTACT_TITLE + " 5"));
		c5p.setSummary(contactPreference.getString(c5p.getKey() + ".num",
				DEFAULT_CONTACT_SUMMARY));

	}

	@Override
	protected void onResume() {
		super.onResume();

		// Set up a listener whenever a key changes
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
		contactPreference = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		c1p.setTitle(contactPreference.getString(c1p.getKey() + ".nme",
				DEFAULT_CONTACT_TITLE + " 1"));
		c1p.setSummary(contactPreference.getString(c1p.getKey() + ".num",
				DEFAULT_CONTACT_SUMMARY));

		c2p.setTitle(contactPreference.getString(c2p.getKey() + ".nme",
				DEFAULT_CONTACT_TITLE + " 2"));
		c2p.setSummary(contactPreference.getString(c2p.getKey() + ".num",
				DEFAULT_CONTACT_SUMMARY));

		c3p.setTitle(contactPreference.getString(c3p.getKey() + ".nme",
				DEFAULT_CONTACT_TITLE + " 3"));
		c3p.setSummary(contactPreference.getString(c3p.getKey() + ".num",
				DEFAULT_CONTACT_SUMMARY));

		c4p.setTitle(contactPreference.getString(c4p.getKey() + ".nme",
				DEFAULT_CONTACT_TITLE + " 4"));
		c4p.setSummary(contactPreference.getString(c4p.getKey() + ".num",
				DEFAULT_CONTACT_SUMMARY));

		c5p.setTitle(contactPreference.getString(c5p.getKey() + ".nme",
				DEFAULT_CONTACT_TITLE + " 5"));
		c5p.setSummary(contactPreference.getString(c5p.getKey() + ".num",
				DEFAULT_CONTACT_SUMMARY));

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
				String id = result.getLastPathSegment();
				cursor = getContentResolver().query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ "=?", new String[] { id }, null);
				phoneIdx = cursor
						.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);
				if (cursor.moveToFirst()) {
					name = cursor
							.getString(cursor
									.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

					while (cursor.isAfterLast() == false) {
						phoneNumber = cursor.getString(phoneIdx);
						allNumbers.add(phoneNumber);
						cursor.moveToNext();
					}
				} else {
					// no results actions
				}
			} catch (Exception e) {
				Log.d(TAG, "##### Exception " + e);
			} finally {
				if (cursor != null) {
					cursor.close();
				}
				final CharSequence[] items = allNumbers
						.toArray(new String[allNumbers.size()]);
				AlertDialog.Builder builder = new AlertDialog.Builder(
						ImhContactPrefActivity.this);
				builder.setTitle("Choose a number");
				builder.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						String selectedNumber = items[item].toString();
						selectedNumber = selectedNumber.replace("-", "");
						current.setTitle(name);
						current.setSummary(selectedNumber != null ? selectedNumber
								.trim() : selectedNumber);

						Editor editor = contactPreference.edit();
						editor.putString(current.getKey() + ".num",
								selectedNumber != null ? selectedNumber.trim()
										: selectedNumber);
						editor.putString(current.getKey() + ".nme", name);
						editor.commit();
					}
				});
				AlertDialog alert = builder.create();
				if (allNumbers.size() > 1) {
					alert.show();
				} else {
					String selectedNumber = phoneNumber.toString();
					selectedNumber = selectedNumber.replace("-", "");
					current.setTitle(name);
					current.setSummary(selectedNumber != null ? selectedNumber
							.trim() : selectedNumber);

					Editor editor = contactPreference.edit();
					editor.putString(current.getKey() + ".num",
							selectedNumber != null ? selectedNumber.trim()
									: selectedNumber);
					editor.putString(current.getKey() + ".nme", name);
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

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		current = preference;
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
		startActivityForResult(intent, 1000);

		return super.onPreferenceTreeClick(preferenceScreen, preference);

	}

	@Override
	protected void onPause() {
		super.onPause();

		// Unregister the listener whenever a key changes
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {

	}
}
