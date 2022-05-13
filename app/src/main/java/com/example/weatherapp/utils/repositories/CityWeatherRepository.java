package com.example.weatherapp.utils.repositories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.city_list.CityWeatherModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton pattern
 */

public class CityWeatherRepository {

    private static CityWeatherRepository instance;
    private static final String SHARED_PREFERENCE_NAME = "city_weather_data";
    private static SharedPreferences preferences;
    private static List<CityWeatherModel> dataSet = new ArrayList<>();


    public static CityWeatherRepository getInstance() {
        if (instance == null)
            instance = new CityWeatherRepository();
        return instance;
    }

    public MutableLiveData<List<CityWeatherModel>> getCityWeatherModel(Context context) {
        retrieveCityWeatherModel(context);
        MutableLiveData<List<CityWeatherModel>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }


    private void retrieveCityWeatherModel(Context context) {
        //get data from shared preference
        preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("SerializableWeatherData", "");
        Type listOfWeatherData = new TypeToken<ArrayList<CityWeatherModel>>() {
        }.getType();
        List<CityWeatherModel> tempModels = gson.fromJson(json, listOfWeatherData);
        if (tempModels != null && !tempModels.isEmpty())
            dataSet = tempModels;
    }

    public boolean saveCityWeatherModel(Context context, List<CityWeatherModel> data) {
        //save data to share preference
        preferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(data);
        editor.putString("SerializableWeatherData", json);
        editor.apply();
        return false;
    }

}
