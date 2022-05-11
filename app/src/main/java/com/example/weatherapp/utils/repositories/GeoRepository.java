package com.example.weatherapp.utils.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.remote.apis.geo_api.GeoApiService;
import com.example.weatherapp.remote.model.GeoApiModel.GeoCodeFuzzy;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeoRepository {
    private static GeoRepository instance = null;
    private final GeoApiService geoApiService;

    private GeoRepository(GeoApiService geoApiService) {
        this.geoApiService = geoApiService;
    }

    public static GeoRepository getRepository(GeoApiService geoApiService){
        if(instance == null){
            instance = new GeoRepository(geoApiService);
        }
        return instance;
    }

    public LiveData<GeoCodeFuzzy> fetchGeoCode(String query, Map<String, String> params){
        MutableLiveData<GeoCodeFuzzy> geoCodeData = new MutableLiveData<>();
        Call<GeoCodeFuzzy> call = geoApiService.getLocations(query, params);
        call.enqueue(new Callback<GeoCodeFuzzy>() {
            @Override
            public void onResponse(Call<GeoCodeFuzzy> call, Response<GeoCodeFuzzy> response) {
                if(response.isSuccessful()){
                    geoCodeData.postValue(response.body());
                }else{
                    //error
                }
            }

            @Override
            public void onFailure(Call<GeoCodeFuzzy> call, Throwable t) {

            }
        });
        return geoCodeData;
    }
}
