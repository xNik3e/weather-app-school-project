package com.example.weatherapp.city_list;

import com.example.weatherapp.remote.model.one_call_weather.OneCallWeatherResponse;
import com.example.weatherapp.search.CityModel;

import java.io.Serializable;

public class CityWeatherModel implements Serializable {
    private CityModel cityModel;
    private OneCallWeatherResponse currentWeather;

    public CityWeatherModel(CityModel cityModel, OneCallWeatherResponse currentWeather) {
        this.cityModel = cityModel;
        this.currentWeather = currentWeather;
    }

    public OneCallWeatherResponse getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(OneCallWeatherResponse currentWeather) {
        this.currentWeather = currentWeather;
    }

    public CityModel getCityModel() {
        return cityModel;
    }

    public void setCityModel(CityModel cityModel) {
        this.cityModel = cityModel;
    }
}
