package com.example.weatherapp.utils.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.remote.apis.weather_api.ApiService;
import com.example.weatherapp.remote.model.one_call_weather.OneCallWeatherResponse;
import com.example.weatherapp.remote.model.reverseGeoCode.ReverseGeoCodeResponseItem;

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

    public static Repository getRepository(ApiService apiService){
        if(instance == null)
            instance = new Repository(apiService);
        return instance;
    }

    public LiveData<List<ReverseGeoCodeResponseItem>> fetchReverseGeoCode(Map<String, String> params){
        MutableLiveData<List<ReverseGeoCodeResponseItem>> reverseGeoData = new MutableLiveData<>();
        Call<List<ReverseGeoCodeResponseItem>> call = apiService.getReverseLocation(params);
        call.enqueue(new Callback<List<ReverseGeoCodeResponseItem>>() {
            @Override
            public void onResponse(Call<List<ReverseGeoCodeResponseItem>> call, Response<List<ReverseGeoCodeResponseItem>> response) {
                if(response.isSuccessful())
                    reverseGeoData.postValue(response.body());
                else{
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
    public LiveData<OneCallWeatherResponse> fetchWeatherData(Map<String, String> params) {
        MutableLiveData<OneCallWeatherResponse> weatherData = new MutableLiveData<>();
        Call<OneCallWeatherResponse> call = apiService.getWeatherData(params);
        call.enqueue(new Callback<OneCallWeatherResponse>() {
            @Override
            public void onResponse(Call<OneCallWeatherResponse> call, Response<OneCallWeatherResponse> response) {
                if(response.isSuccessful())
                    weatherData.postValue(response.body());
                else{
                    //error
                }

            }
            @Override
            public void onFailure(Call<OneCallWeatherResponse> call, Throwable t) {
                //error
            }
        });
        return weatherData;
    }
}
