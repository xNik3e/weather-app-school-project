package com.example.weatherapp.weather.model;

import com.example.weatherapp.utils.view_services.AlwaysMarqueeTextView;

public class HourlyWeather {
    private AlwaysMarqueeTextView time;
    private int src_weather;
    private AlwaysMarqueeTextView temperature;

    public HourlyWeather(AlwaysMarqueeTextView time, int src_weather, AlwaysMarqueeTextView temperature) {
        this.time = time;
        this.src_weather = src_weather;
        this.temperature = temperature;
    }

    public AlwaysMarqueeTextView getTime() {
        return time;
    }

    public void setTime(AlwaysMarqueeTextView time) {
        this.time = time;
    }

    public int getSrc_weather() {
        return src_weather;
    }

    public void setSrc_weather(int src_weather) {
        this.src_weather = src_weather;
    }

    public AlwaysMarqueeTextView getTemperature() {
        return temperature;
    }

    public void setTemperature(AlwaysMarqueeTextView temperature) {
        this.temperature = temperature;
    }
}
