package com.example.weatherapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.TypedValue;
import android.view.View;

import com.example.weatherapp.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WeatherUtils {

    private static final String SHARED_PREFERENCE_NAME = "com.example.weatherapp_preferences";

    public static String getLastUpdatedString(long diffSec) {

        long ThisEpoch = System.currentTimeMillis() / 1000;

        long difference = (ThisEpoch - diffSec);

        int lastUpdate = (difference / 3600) < 24 ? (int) (difference / 3600) : (int) (difference / (3600 * 24));
        String timeUnit = (difference / 3600) < 24 ? ((int) (difference / 3600) == 1 ? " hour" : " hours") : ((int) ((difference / 3600) / 24) > 1 ? " days" : " day");
        StringBuilder sb = new StringBuilder();
        sb.append("Last updated ").append(lastUpdate).append(timeUnit).append(" ago");
        return sb.toString();
    }

    public static Map<String, Double> convertTemp(Context context, double val) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        String temperature = preferences.getString("settings_preference_temperature", "°C");

        Map<String, Double> mTemperature = new HashMap<>();
        double newVal;

        if (temperature.equals("°C"))
            newVal = val;
        else
            newVal = (1.8 * val + 32);

        mTemperature.put(temperature, newVal);
        return mTemperature;
    }

    public static Map<String, Double> convertWind(Context context, double val) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        String wind = preferences.getString("settings_preference_wind", "km/h");

        Map<String, Double> mWind = new HashMap<>();
        double newVal;

        switch (wind) {
            case "km/h":
                newVal = val;
            case "m/s":
                newVal = (val * 5 / 18);
            case "ft/s":
                newVal = (val * 0.911344);
            default:
                newVal = (val / 1.609344);
        }
        mWind.put(wind, newVal);
        return mWind;
    }

    public static Map<String, Double> convertPrecipitation(Context context, double val) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        String precipitation = preferences.getString("settings_preference_precipitation", "mm");

        Map<String, Double> mPrecipitation = new HashMap<>();
        double newVal;

        if (precipitation.equals("mm"))
            newVal = val;
        else
            newVal = (val / 25.4);

        mPrecipitation.put(precipitation, newVal);
        return mPrecipitation;
    }

    public static Map<String, Double> convertVisibility(Context context, double val) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        String visibility = preferences.getString("settings_preference_visibility", "km");

        Map<String, Double> mVisibility = new HashMap<>();
        double newVal;

        if (visibility.equals("km"))
            newVal = val;
        else
            newVal = (val * 0.6214);

        mVisibility.put(visibility, newVal);
        return mVisibility;
    }

    public static String getKey(Map<String, Double> map) {
        Set<Map.Entry<String, Double>> entrySet = map.entrySet();

        String key = null;

        for (Map.Entry<String, Double> set : entrySet)
            key = set.getKey();

        return key;
    }

    public static double getValue(Map<String, Double> map) {
        Set<Map.Entry<String, Double>> entrySet = map.entrySet();

        double val = 0;

        for (Map.Entry<String, Double> set : entrySet)
            val = set.getValue();

        return val;
    }
}
