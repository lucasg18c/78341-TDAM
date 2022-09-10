package com.slavik.tdam.ui;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.slavik.tdam.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}