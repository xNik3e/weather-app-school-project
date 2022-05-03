package com.example.weatherapp.remote;

import com.example.weatherapp.remote.model.SimpleWeatherCall.SimpleWeatherCall;
import com.example.weatherapp.remote.model.geoModel.GeoResponse;
import com.example.weatherapp.remote.model.oneCall.OneCall;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {
    @GET("geo/1.0/direct")
    Call<GeoResponse> getLocation(@QueryMap Map<String, String> params);

    @GET("data/2.5/onecall")
    Call<OneCall> getCurrentWeatherData(@QueryMap Map<String, String> params);

    @GET("data/2.5/weather")
    Call<SimpleWeatherCall> getSimpleWeatherData(@QueryMap Map<String, String> params);
}
