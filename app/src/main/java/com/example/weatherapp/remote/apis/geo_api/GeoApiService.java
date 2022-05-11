package com.example.weatherapp.remote.apis.geo_api;

import com.example.weatherapp.remote.model.GeoApiModel.GeoCodeFuzzy;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface GeoApiService {
    @GET("{query}.json/")
    Call<GeoCodeFuzzy> getLocations(@Path("query") String query, @QueryMap Map<String, String> params);
}
