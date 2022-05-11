package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.city_list.CityListAdapter;
import com.example.weatherapp.city_list.CityWeatherModel;
import com.example.weatherapp.remote.apis.ApiParams;
import com.example.weatherapp.remote.model.one_call_current_weather.OneCallWeatherResponse;
import com.example.weatherapp.search.CityModel;
import com.example.weatherapp.utils.viewmodels.CityWeatherViewModel;
import com.example.weatherapp.utils.viewmodels.ViewModelFactory;
import com.example.weatherapp.utils.viewmodels.WeatherSearchViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class CityListActivity extends AppCompatActivity implements CityListAdapter.CityListInterface, SwipeRefreshLayout.OnRefreshListener {

    private MaterialToolbar toolbar;
    private FrameLayout iconContainer;
    private ImageView icBack;
    private RelativeLayout citiesLayout;
    private RecyclerView cities;
    private FloatingActionButton fab;
    private TextView hint;

    private CityWeatherViewModel cityWeatherViewModel;
    private WeatherSearchViewModel weatherSearchViewModel;
    private final ApiParams apiParams = new ApiParams();
    private ConnectivityManager connectivityManager;

    private List<CityWeatherModel> models = new ArrayList<>();
    private CityListAdapter adapter;
    private SwipeRefreshLayout swipe;
    private List<CityWeatherModel> updatedList = new ArrayList<>();
    private MutableLiveData<List<CityWeatherModel>> data = new MutableLiveData<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        toolbar = findViewById(R.id.toolbar);
        iconContainer = findViewById(R.id.toolbar_icon_container);
        icBack = findViewById(R.id.ic_back);
        citiesLayout = findViewById(R.id.cities_layout);
        cities = findViewById(R.id.cities);
        fab = findViewById(R.id.fab);
        hint = findViewById(R.id.no_city_view);
        swipe = findViewById(R.id.swipe_refresh_layout);
        connectivityManager = getSystemService(ConnectivityManager.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        cityWeatherViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(CityWeatherViewModel.class);
        weatherSearchViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(WeatherSearchViewModel.class);

        cityWeatherViewModel.init(this);

        LiveData<List<CityWeatherModel>> liveDataList = cityWeatherViewModel.getCityWeatherModels();
        List<CityWeatherModel> liveDataListValue = liveDataList.getValue();

        if (liveDataListValue != null && !liveDataListValue.isEmpty()) {
            models.clear();
            models.addAll(liveDataListValue);
        }
        if (models.isEmpty()) {
            hint.setVisibility(View.VISIBLE);
            cities.setVisibility(View.GONE);
        } else {
            hint.setVisibility(View.GONE);
            cities.setVisibility(View.VISIBLE);
        }

        adapter = new CityListAdapter(this, models);
        cities.setLayoutManager(linearLayoutManager);
        cities.setAdapter(adapter);

        iconContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CityListActivity.this, MainActivity.class));
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CityListActivity.this, SearchActivity.class));
            }
        });

        swipe.setOnRefreshListener(this);

        data.observe(this, new Observer<List<CityWeatherModel>>() {
            @Override
            public void onChanged(List<CityWeatherModel> modelList) {
                if (modelList != null && modelList.size() == models.size()) {
                    cityWeatherViewModel.replaceCityData(modelList);
                }
            }
        });

        cityWeatherViewModel.getCityWeatherModels().observe(this, new Observer<List<CityWeatherModel>>() {
            @Override
            public void onChanged(List<CityWeatherModel> modelList) {
                cityWeatherViewModel.saveCityData(CityListActivity.this, modelList);
                cityWeatherViewModel.getIsUpdating().observe(CityListActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean){
                            //maybe add progressbar
                        }else{
                            //add later
                            models.clear();
                            models.addAll(cityWeatherViewModel.getCityWeatherModels().getValue());
                            adapter.notifyDataSetChanged();
                            cities.scrollToPosition(0);
                            if(swipe.isRefreshing()){
                                swipe.setRefreshing(false);
                                Toast.makeText(CityListActivity.this, "Everything is up to date ^^", Toast.LENGTH_SHORT).show();
                            }
                            if (models.isEmpty()) {
                                hint.setVisibility(View.VISIBLE);
                                cities.setVisibility(View.GONE);
                            }
                        }
                    }
                });
            }
        });

    }


    @Override
    public void onRefresh() {
        NetworkInfo activeNetworkInfo = CityListActivity.this.connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
            Toast.makeText(this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        } else {
            if (models != null && models.isEmpty()) {
                swipe.setRefreshing(false);
            } else {
                swipe.setRefreshing(true);
                Toast.makeText(this, "Updating your data. Please wait...", Toast.LENGTH_LONG).show();
                if (!updatedList.isEmpty()) {
                    updatedList.clear();
                }
                for (CityWeatherModel m : models) {
                    getWeather(m.getCityModel());
                }
            }
        }
    }

    @Override
    public void deleteItem(int position) {
        //delete specyfic element and update adapter and LiveData
    }

    @Override
    public void selectItem() {
        //launch main activity with the position intent
    }

    private void getWeather(CityModel model) {
        OneCallWeatherResponse response = weatherSearchViewModel.getWeatherData(apiParams.getWeatherParams(model.getLat(), model.getLon()));
        if (response != null) {
            CityWeatherModel temp = new CityWeatherModel(model, response);
            temp.setLastUpdate(System.currentTimeMillis() / 1000);
            updatedList.add(temp);
            data.setValue(updatedList);
        }
    }
}