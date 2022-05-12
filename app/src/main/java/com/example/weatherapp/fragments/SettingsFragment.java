package com.example.weatherapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.weatherapp.AboutMeActivity;
import com.example.weatherapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private ListPreference temp, wind, precip, visibility;
    private Preference about;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        addPreferencesFromResource(R.xml.setting_preference);

        temp = getPreferenceManager().findPreference("settings_preference_temperature");
        wind = getPreferenceManager().findPreference("settings_preference_wind");
        precip = getPreferenceManager().findPreference("settings_preference_precipitation");
        visibility = getPreferenceManager().findPreference("settings_preference_visibility");
        about = getPreferenceScreen().findPreference("settings_preference_about");


        if(temp.getValue() != null){
            temp.setSummary(temp.getValue());
        }

        if(wind.getValue() != null){
            wind.setSummary(wind.getValue());
        }

        if(precip.getValue() != null){
            precip.setSummary(precip.getValue());
        }

        if(visibility.getValue() != null){
            visibility.setSummary(visibility.getValue());
        }

        temp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                temp.setSummary(newValue.toString());
                return true;
            }
        });

        wind.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                wind.setSummary(newValue.toString());
                return true;
            }
        });

        precip.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                precip.setSummary(newValue.toString());
                return true;
            }
        });

        visibility.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                visibility.setSummary(newValue.toString());
                return true;
            }
        });

        about.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                startActivity(new Intent(getContext(), AboutMeActivity.class));
                return true;
            }
        });

    }
}
