package com.example.weatherapp.remote;

import com.example.weatherapp.remote.model.SimpleWeatherCall.SimpleWeatherCall;

import com.example.weatherapp.remote.model.oneCall.OneCall;
import com.example.weatherapp.remote.model.reverseGeoCode.ReverseGeoCodeResponse;
import com.example.weatherapp.remote.model.reverseGeoCode.ReverseGeoCodeResponseItem;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("geo/1.0/reverse")
    Call<List<ReverseGeoCodeResponseItem>> getReverseLocation(@QueryMap Map<String, String> params);

    @GET("data/2.5/onecall")
    Call<OneCall> getCurrentWeatherData(@QueryMap Map<String, String> params);

    @GET("data/2.5/weather")
    Call<SimpleWeatherCall> getSimpleWeatherData(@QueryMap Map<String, String> params);
}
