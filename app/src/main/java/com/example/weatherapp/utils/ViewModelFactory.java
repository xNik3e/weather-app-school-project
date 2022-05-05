package com.example.weatherapp.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.weatherapp.remote.GeoApiClient;
import com.example.weatherapp.remote.GeoApiService;
import com.example.weatherapp.remote.GeoRepository;
import com.example.weatherapp.search.CitySearchViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final GeoRepository geoRepository;

    public ViewModelFactory() {
        GeoApiService geoApiService = GeoApiClient.getRetrofit().create(GeoApiService.class);
        geoRepository = GeoRepository.getRepository(geoApiService);
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T>modelClass)
    {
        if(modelClass.isAssignableFrom(CitySearchViewModel.class)){
            return (T) new CitySearchViewModel(geoRepository);
        }

        throw new IllegalArgumentException("View Model not found");
    }
}