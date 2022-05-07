package com.example.weatherapp.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.remote.Repository;
import com.example.weatherapp.remote.model.reverseGeoCode.ReverseGeoCodeResponse;
import com.example.weatherapp.remote.model.reverseGeoCode.ReverseGeoCodeResponseItem;

import java.util.List;
import java.util.Map;

public class ReverseCitySearchViewModel extends ViewModel {
    private Repository repository;

    public ReverseCitySearchViewModel(Repository repository){this.repository = repository;}
    public LiveData<List<ReverseGeoCodeResponseItem>> getReverseGeoCode(Map<String, String> params){
        return repository.fetchReverseGeoCode(params);
    }
}
