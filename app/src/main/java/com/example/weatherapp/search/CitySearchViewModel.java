package com.example.weatherapp.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.remote.GeoRepository;
import com.example.weatherapp.remote.model.GeoApiModel.GeoCodeFuzzy;

import java.util.Map;

public class CitySearchViewModel extends ViewModel {
    private GeoRepository geoRepository;

    public CitySearchViewModel(GeoRepository geoRepository) {
        this.geoRepository = geoRepository;
    }

    public LiveData<GeoCodeFuzzy> getGeoLocation(String query, Map<String, String> params){
        return geoRepository.fetchGeoCode(query, params);
    }
}
