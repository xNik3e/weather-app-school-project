package com.example.weatherapp.utils.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.city_list.CityWeatherModel;
import com.example.weatherapp.utils.repositories.CityWeatherRepository;

import java.util.List;

public class CityWeatherViewModel extends ViewModel {
    private static MutableLiveData<List<CityWeatherModel>> cityWeatherModels;
    private MutableLiveData<Boolean> isUpdating = new MutableLiveData<>();

    private static CityWeatherRepository mRepo;

    public LiveData<List<CityWeatherModel>> getCityWeatherModels() {
        return cityWeatherModels;
    }

    public void init(Context context) {
        if (cityWeatherModels != null) {
            return;
        }
        mRepo = CityWeatherRepository.getInstance();
        cityWeatherModels = mRepo.getCityWeatherModel(context);
        Log.d("TAG", cityWeatherModels.toString());
    }

    public void addNewCity(final CityWeatherModel model) {
        List<CityWeatherModel> weatherModels = cityWeatherModels.getValue();
        weatherModels.add(model);
        cityWeatherModels.setValue(weatherModels);
    }

    public void replaceCityData(final List<CityWeatherModel> modelList) {
        cityWeatherModels.setValue(modelList);

    }

    public void saveCityData(Context context, List<CityWeatherModel> modelList) {
        isUpdating.postValue(true);
        boolean result = mRepo.saveCityWeatherModel(context, modelList);
        isUpdating.postValue(result);
    }

    public LiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }

}
