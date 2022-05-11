package com.example.weatherapp.utils.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.remote.model.one_call_current_weather.OneCallWeatherResponse;
import com.example.weatherapp.utils.repositories.Repository;

import java.util.Map;

public class WeatherSearchViewModel extends ViewModel {
    private Repository repository;

    public WeatherSearchViewModel(Repository repository) {
        this.repository = repository;
    }

    public OneCallWeatherResponse getWeatherData(Map<String, String> params) {
        OneCallWeatherResponse data = repository.fetchCurrentWeatherData(params);
        return data;
    }

    public LiveData<OneCallWeatherResponse> getWeatherDataAsync(Map<String, String> params) {
        MutableLiveData<OneCallWeatherResponse> oneCall = new MutableLiveData<>();
        OneCallWeatherResponse data = repository.fetchCurrentWeatherDataAsync(params);
        oneCall.setValue(data);
        return oneCall;
    }
}