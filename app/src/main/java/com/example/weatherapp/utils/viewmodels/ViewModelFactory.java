package com.example.weatherapp.utils.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.weatherapp.remote.apis.weather_api.ApiClient;
import com.example.weatherapp.remote.apis.weather_api.ApiService;
import com.example.weatherapp.remote.apis.geo_api.GeoApiClient;
import com.example.weatherapp.remote.apis.geo_api.GeoApiService;
import com.example.weatherapp.utils.repositories.GeoRepository;
import com.example.weatherapp.utils.repositories.Repository;

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
        else if(modelClass.isAssignableFrom(WeatherSearchViewModel.class))
            return (T) new WeatherSearchViewModel(repository);
        else if(modelClass.isAssignableFrom(CityWeatherViewModel.class))
            return (T) new CityWeatherViewModel();

        throw new IllegalArgumentException("View Model not found");
    }
}
