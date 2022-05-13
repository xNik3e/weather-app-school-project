package com.example.weatherapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.weatherapp.city_list.CityWeatherModel;
import com.example.weatherapp.fragments.WeatherInfo;
import com.example.weatherapp.utils.viewmodels.CityWeatherViewModel;
import com.example.weatherapp.utils.viewmodels.ViewModelFactory;
import com.example.weatherapp.utils.viewmodels.WeatherSearchViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WeatherInfo.NotifyMainActivity {


    private FrameLayout mainViewBackground;
    private FrameLayout weather;
    private FrameLayout toolbarIconContainer;
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
    private CityWeatherViewModel cityWeatherViewModel;
    private WeatherSearchViewModel weatherSearchViewModel;
    private List<CityWeatherModel> models = new ArrayList<>();
    private boolean isGit = false;
    private PagerAdapter pagerAdapter;
    private static int position = 0;

    private final static int OPNETWORK = 0;
    private final static int OPNOCITIES = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        Intent intent = getIntent();
        if(intent.hasExtra("POSITION"))
        {
            this.position = intent.getIntExtra("POSITION", 0);
        }

        cityWeatherViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(CityWeatherViewModel.class);
        weatherSearchViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(WeatherSearchViewModel.class);

        cityWeatherViewModel.init(this);

        mainViewBackground = findViewById(R.id.ViewBackground);
        weather = findViewById(R.id.current_weather_background);
        toolbarIconContainer = findViewById(R.id.toolbar_icon_container);
        viewPager = findViewById(R.id.pager);
        toolbar = findViewById(R.id.toolbar);
        ic_gps = findViewById(R.id.ic_gps);
        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarSubtitle = findViewById(R.id.toolbar_subtitle);
        selectCity = findViewById(R.id.city_select);
        settings = findViewById(R.id.menu_more);
        gpsTip = findViewById(R.id.gps_tip);
        btnGPSSettings = findViewById(R.id.button_gps_settings);

        init();
        isGit = checkProviders();

        LiveData<List<CityWeatherModel>> liveDataList = cityWeatherViewModel.getCityWeatherModels();
        List<CityWeatherModel> liveDataListValue = liveDataList.getValue();

        if (liveDataListValue != null && !liveDataListValue.isEmpty()) {
            fragmentManager = getSupportFragmentManager();
            weatherInfo = new WeatherInfo(this.position);
            //viewPager.setAdapter(pagerAdapter);
            setFragment(weatherInfo, fragmentManager);
        } else {
            showAlert(R.string.no_city_data, R.string.ok_city, R.string.CANCEL, OPNOCITIES, false);
        }


        selectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CityListActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

    }

    private boolean checkProviders() {
        NetworkInfo activeNetworkInfo = getSystemService(ConnectivityManager.class).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
            showAlert(R.string.no_network_connection, R.string.OPEN, R.string.DISMISS, OPNETWORK, true);
            return false;
        } else {
            if (checkGPS()) {
                gpsTip.setVisibility(View.GONE);
            } else {
                gpsTip.setVisibility(View.VISIBLE);
            }
            return true;
        }
    }

    private void showAlert(int messageId, int positiveID, int negativeID, int OPTYPE, boolean cancelable) {
        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);

        builder.setMessage(messageId).setCancelable(cancelable).setPositiveButton(positiveID, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (OPTYPE == OPNOCITIES) {
                    startActivity(new Intent(MainActivity.this, SearchActivity.class));
                } else {
                    Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(intent);
                }
            }
        }).setNegativeButton(negativeID, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (OPTYPE == OPNOCITIES)
                    finish();
                else {
                    dialog.cancel();
                }
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void init() {
        mainViewBackground.setBackgroundColor(getResources().getColor(R.color.weather_sun));
        toolbarIconContainer.setVisibility(View.VISIBLE);
        toolbarTitle.setText("");
        toolbarSubtitle.setText("");

    }

    private boolean checkGPS() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
    }

    public void openGPSSettings(View view) {

    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0)
            super.onBackPressed();
        else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    private void setFragment(Fragment fragment, FragmentManager fragmentManager) {
        fragmentManager.beginTransaction()
                .replace(R.id.current_weather_background, fragment)
                .commit();
    }

    @Override
    public void sendData(String title, String updated, boolean isLocated) {
        if (isLocated)
            toolbarIconContainer.setVisibility(View.VISIBLE);
        else
            toolbarIconContainer.setVisibility(View.GONE);

        toolbarTitle.setText(title);
        if (updated.equals("Last updated 0 hours ago"))
            toolbarSubtitle.setText("Up to date");
        else
            toolbarSubtitle.setText(updated);

    }

    @Override
    public void changeBackground(int resId, int colorId) {
        mainViewBackground.setBackgroundColor(getColor(colorId));

    }



    public void changeTextColor(int colorId){

    }
}