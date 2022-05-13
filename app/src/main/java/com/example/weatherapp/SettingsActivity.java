package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.weatherapp.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportFragmentManager().beginTransaction().replace(R.id.container_settings, new SettingsFragment()).commit();
    }

    public void returnToMain(View view) {
        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
    }
}