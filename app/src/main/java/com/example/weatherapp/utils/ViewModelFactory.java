package com.example.weatherapp.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.weatherapp.remote.ApiClient;
import com.example.weatherapp.remote.ApiService;
import com.example.weatherapp.remote.GeoApiClient;
import com.example.weatherapp.remote.GeoApiService;
import com.example.weatherapp.remote.GeoRepository;
import com.example.weatherapp.remote.Repository;
import com.example.weatherapp.search.CitySearchViewModel;
import com.example.weatherapp.search.ReverseCitySearchViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final GeoRepository geoRepository;
    private final Repository repository;

    public ViewModelFactory() {
        GeoApiService geoApiService = GeoApiClient.getRetrofit().create(GeoApiService.class);
        geoRepository = GeoRepository.getRepository(geoApiService);

        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        repository = Repository.getRepository(apiService);
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T>modelClass)
    {
        if(modelClass.isAssignableFrom(CitySearchViewModel.class)){
            return (T) new CitySearchViewModel(geoRepository);
        }else if(modelClass.isAssignableFrom(ReverseCitySearchViewModel.class))
            return (T) new ReverseCitySearchViewModel(repository);

        throw new IllegalArgumentException("View Model not found");
    }
}
