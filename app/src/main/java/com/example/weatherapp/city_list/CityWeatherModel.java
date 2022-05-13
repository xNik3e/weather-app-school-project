package com.example.weatherapp.city_list;

import com.example.weatherapp.remote.model.one_call_current_weather.OneCallWeatherResponse;
import com.example.weatherapp.search.CityModel;

import java.io.Serializable;

public class CityWeatherModel implements Serializable {
    private CityModel cityModel;
    private OneCallWeatherResponse currentWeather;
    private long lastUpdate;

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public CityWeatherModel(CityModel cityModel, OneCallWeatherResponse currentWeather) {
        this.cityModel = cityModel;
        this.currentWeather = currentWeather;
        lastUpdate = 0;
    }

    public CityWeatherModel() {
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
