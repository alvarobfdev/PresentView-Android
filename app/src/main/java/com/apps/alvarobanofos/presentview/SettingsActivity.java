package com.apps.alvarobanofos.presentview;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by alvarobanofos on 30/5/16.
 */
public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }


}
