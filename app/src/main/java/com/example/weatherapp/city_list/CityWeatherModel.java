package com.example.weatherapp.city_list;

import com.example.weatherapp.remote.model.one_call_current_weather.OneCallCurrentWeatherResponse;
import com.example.weatherapp.search.CityModel;

import java.io.Serializable;

public class CityWeatherModel implements Serializable {
    private CityModel cityModel;
    private OneCallCurrentWeatherResponse currentWeather;

    public CityWeatherModel(CityModel cityModel, OneCallCurrentWeatherResponse currentWeather) {
        this.cityModel = cityModel;
        this.currentWeather = currentWeather;
    }

    public OneCallCurrentWeatherResponse getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(OneCallCurrentWeatherResponse currentWeather) {
        this.currentWeather = currentWeather;
    }

    public CityModel getCityModel() {
        return cityModel;
    }

    public void setCityModel(CityModel cityModel) {
        this.cityModel = cityModel;
    }
}
