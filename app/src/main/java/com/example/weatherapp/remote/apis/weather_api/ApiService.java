package com.example.weatherapp.remote.apis.weather_api;



import com.example.weatherapp.remote.model.one_call_current_weather.OneCallCurrentWeatherResponse;
import com.example.weatherapp.remote.model.reverseGeoCode.ReverseGeoCodeResponseItem;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("geo/1.0/reverse")
    Call<List<ReverseGeoCodeResponseItem>> getReverseLocation(@QueryMap Map<String, String> params);

    @GET("data/2.5/onecall")
    Call<OneCallCurrentWeatherResponse> getCurrentWeatherData(@QueryMap Map<String, String> params);



}
