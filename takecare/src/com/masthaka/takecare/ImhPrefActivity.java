package com.masthaka.takecare;

import com.example.relative.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.util.Log;

public class ImhPrefActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener  {

	private static final String TAG = "ImhPrefActivity";
	
	public static final String KNAME = "kname";
	public static final String KPHONE = "kphone";
	public static final String KLOCATION = "klocation";
	

	private EditTextPreference namePreference;
	private EditTextPreference phonePreference;
	private Preference locationPreference;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.imh);
		
		namePreference = (EditTextPreference)getPreferenceScreen().findPreference(KNAME);
		phonePreference = (EditTextPreference)getPreferenceScreen().findPreference(KPHONE);
		locationPreference = getPreferenceScreen().findPreference(KLOCATION);
		
		//Log.d(TAG, "##########???? "+locationPreference);
		
//		Preference myPref = (Preference) findPreference(KLOCATION);
//		myPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
//		             public boolean onPreferenceClick(Preference preference) {
//		            	 Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//		         		startActivity(intent);
//						return true;
//		                 //open browser or intent here
//		             }
//		         });
		
	}

	
    @Override
    protected void onResume() {
        super.onResume();
        //Log.d(TAG, "##########^^^^RRRRRRR.....");

        // Setup the initial values
        
        namePreference.setSummary(getPreferenceScreen().getSharedPreferences().getString(KNAME, "Enter your name"));
        phonePreference.setSummary(getPreferenceScreen().getSharedPreferences().getString(KPHONE, "Enter your mobile number of this device"));
        

		 LocationManager service = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		 boolean gpsEnabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
		 boolean localEnabled = service.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		
		 // Check if enabled and if not send user to the GSP settings
		 // Better solution would be to display a dialog and suggesting to
		 // go to the settings
		 
		 Log.d(TAG, "@@@ GPS "+gpsEnabled);
		 Log.d(TAG, "@@@ Local "+localEnabled);
		 
		 
		 if (!gpsEnabled && !localEnabled){ 
			 locationPreference.setSummary("You must enable GPS for tracking! GSP is used only during notification");
		 }else if (!gpsEnabled) {
			 locationPreference.setSummary("You must enable GPS for tracking! GSP is used only during notification");
		 }else{
			 locationPreference.setSummary("GPS is enabled for tracking! GSP is used only during notification");
		 }

        // Set up a listener whenever a key changes            
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);


    }

    @Override
    protected void onPause() {
        super.onPause();

        // Unregister the listener whenever a key changes            
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);    
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        // Let's do something a preference value changes
        if (key.equals(KNAME)) {
        	namePreference.setSummary(getPreferenceScreen().getSharedPreferences().getString(KNAME, "Enter your name"));
        }else if(key.equals(KPHONE)) {
        	phonePreference.setSummary(getPreferenceScreen().getSharedPreferences().getString(KPHONE, "Enter your mobile number of this device"));
        }
        
        
    }
}
