package com.example.weatherapp.utils.viewmodels;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.city_list.CityWeatherModel;
import com.example.weatherapp.utils.repositories.CityWeatherRepository;

import java.util.List;

public class CityWeatherViewModel extends ViewModel {
    private MutableLiveData<List<CityWeatherModel>> cityWeatherModels;
    private CityWeatherRepository mRepo;

    public LiveData<List<CityWeatherModel>> getCityWeatherModels(){
        return cityWeatherModels;
    }

    public void init(Context context){
        if(cityWeatherModels != null){
            return;
        }
        mRepo = CityWeatherRepository.getInstance();
        cityWeatherModels = mRepo.getCityWeatherModel(context);
    }

    public void addNewCity(final CityWeatherModel model){
        List<CityWeatherModel> weatherModels = cityWeatherModels.getValue();
        weatherModels.add(model);
        cityWeatherModels.postValue(weatherModels);
    }

    public void replaceCityData(final List<CityWeatherModel> modelList){
        cityWeatherModels.setValue(modelList);
    }
    public void saveCityData(Context context){
        mRepo.saveCityWeatherModel(context, cityWeatherModels.getValue());
    }


}
