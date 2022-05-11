package com.example.weatherapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.utils.view_services.AlwaysMarqueeTextView;
import com.example.weatherapp.utils.view_services.MyWeatherTemperatureView;

public class WeatherInfo extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout relativeLayoutInfo;
    private ScrollView weatherScrollView;
    private Space blankSpace;
    private LinearLayout realtimeTemperature;
    private TextView currentTemperature;
    private AlwaysMarqueeTextView currentWeatherType;
    private TextView currentHighTemperature;
    private TextView currentLowTemperature;
    private FrameLayout forecastWeatherFrameLayout;
    private LinearLayout weatherTempViewLayout;
    private MyWeatherTemperatureView weatherTempView;
    private LinearLayout fifteenDayForecastBox;
    private TextView fifteenDayForecast;
    private FrameLayout hourlyForecastFrameLayout;
    private FrameLayout realtimeWeatherDetailsFrameLayout;
    private FragmentManager fragmentManager;
    private Context context;
    private ForecastWeather forecastWeather;
    private HourForecast hourForecast;
    private RealtimeWeatherDetail realtimeWeatherDetail;

    public WeatherInfo() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        relativeLayoutInfo = view.findViewById(R.id.relative_layout_info);
        weatherScrollView = view.findViewById(R.id.weather_scroll_view);
        blankSpace = view.findViewById(R.id.blank_area);
        realtimeTemperature = view.findViewById(R.id.realtime_temperature);
        currentTemperature = view.findViewById(R.id.current_temperature);
        currentWeatherType = view.findViewById(R.id.current_weather_type);
        currentHighTemperature = view.findViewById(R.id.current_high_temperature);
        currentLowTemperature = view.findViewById(R.id.current_low_temperature);
        forecastWeatherFrameLayout = view.findViewById(R.id.forecast_weather);
        weatherTempViewLayout = view.findViewById(R.id.weather_temp_view_layout);
        weatherTempView = view.findViewById(R.id.weather_temp_view);
        fifteenDayForecastBox = view.findViewById(R.id.fifteen_day_forecast_box);
        fifteenDayForecast = view.findViewById(R.id.fifteen_day_forecast);
        hourlyForecastFrameLayout = view.findViewById(R.id.hourly_forecast);
        realtimeWeatherDetailsFrameLayout = view.findViewById(R.id.realtime_weather_detail);

        forecastWeather = new ForecastWeather();
        hourForecast = new HourForecast();
        realtimeWeatherDetail = new RealtimeWeatherDetail();

        fragmentManager = getChildFragmentManager();

        setFragment(R.id.forecast_weather, forecastWeather, fragmentManager);
        setFragment(R.id.hourly_forecast, hourForecast, fragmentManager);
        setFragment(R.id.realtime_weather_detail, realtimeWeatherDetail, fragmentManager);
    }

    private void setFragment(int container, Fragment fragment, FragmentManager fm) {
        fm.beginTransaction().replace(container, fragment).commit();
    }
}