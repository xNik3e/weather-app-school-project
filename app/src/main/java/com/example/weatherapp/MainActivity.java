package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.weatherapp.fragments.WeatherInfo;
import com.google.android.material.appbar.MaterialToolbar;

import java.nio.InvalidMarkException;

public class MainActivity extends AppCompatActivity {

    private FrameLayout weather;
    private ViewPager viewPager;
    private MaterialToolbar toolbar;
    private ImageView ic_gps;
    private TextView toolbarTitle;
    private TextView toolbarSubtitle;
    private TextView selectCity;
    private ImageView settings;
    private RelativeLayout gpsTip;
    private Button btnGPSSettings;
    private FragmentManager fragmentManager;
    private WeatherInfo weatherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        weather = findViewById(R.id.current_weather_background);
        viewPager = findViewById(R.id.pager);
        toolbar = findViewById(R.id.toolbar);
        ic_gps = findViewById(R.id.ic_gps);
        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarSubtitle = findViewById(R.id.toolbar_subtitle);
        selectCity = findViewById(R.id.city_select);
        settings = findViewById(R.id.menu_more);
        gpsTip = findViewById(R.id.gps_tip);
        btnGPSSettings = findViewById(R.id.button_gps_settings);

        fragmentManager = getSupportFragmentManager();
        weatherInfo = new WeatherInfo();
        setFragment(weatherInfo, fragmentManager);
    }

    public void openGPSSettings(View view) {

    }

    private void setFragment(Fragment fragment, FragmentManager fragmentManager){
        fragmentManager.beginTransaction()
                .replace(R.id.current_weather_background, fragment)
                .commit();
    }
}