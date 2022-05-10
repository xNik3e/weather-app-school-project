package com.example.weatherapp.utils.repositories;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.remote.apis.weather_api.ApiService;
import com.example.weatherapp.remote.model.one_call_current_weather.OneCallWeatherResponse;
import com.example.weatherapp.remote.model.reverseGeoCode.ReverseGeoCodeResponseItem;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static Repository instance = null;
    private final ApiService apiService;

    private Repository(ApiService apiService) {
        this.apiService = apiService;
    }

    public static Repository getRepository(ApiService apiService) {
        if (instance == null)
            instance = new Repository(apiService);
        return instance;
    }

    public LiveData<List<ReverseGeoCodeResponseItem>> fetchReverseGeoCode(Map<String, String> params) {
        MutableLiveData<List<ReverseGeoCodeResponseItem>> reverseGeoData = new MutableLiveData<>();
        Call<List<ReverseGeoCodeResponseItem>> call = apiService.getReverseLocation(params);
        call.enqueue(new Callback<List<ReverseGeoCodeResponseItem>>() {
            @Override
            public void onResponse(Call<List<ReverseGeoCodeResponseItem>> call, Response<List<ReverseGeoCodeResponseItem>> response) {
                if (response.isSuccessful())
                    reverseGeoData.postValue(response.body());
                else {
                    //error
                }
            }

            @Override
            public void onFailure(Call<List<ReverseGeoCodeResponseItem>> call, Throwable t) {
                //error
            }
        });
        return reverseGeoData;
    }

    public OneCallWeatherResponse fetchCurrentWeatherData(Map<String, String> params) {
        OneCallWeatherResponse oneCallWeatherResponse = new OneCallWeatherResponse();
        Call<OneCallWeatherResponse> call = apiService.getCurrentWeatherData(params);
        try {
            Response<OneCallWeatherResponse> response = call.execute();
            if (response.isSuccessful())
                oneCallWeatherResponse = response.body();
        } catch (IOException e) {
            Log.d("TAG", e.getMessage());
        }
        return oneCallWeatherResponse;
    }
}
