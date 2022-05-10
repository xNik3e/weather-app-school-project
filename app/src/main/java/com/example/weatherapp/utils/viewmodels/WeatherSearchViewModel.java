package com.example.weatherapp.utils.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.utils.repositories.Repository;
import com.example.weatherapp.remote.model.one_call_weather.OneCallWeatherResponse;

import java.util.Map;

public class WeatherSearchViewModel extends ViewModel {
    private Repository repository;

    public WeatherSearchViewModel(Repository repository) {
        this.repository = repository;
    }
    public LiveData<OneCallWeatherResponse> getWeatherData(Map<String, String> params){
        return repository.fetchWeatherData(params);
    }
}