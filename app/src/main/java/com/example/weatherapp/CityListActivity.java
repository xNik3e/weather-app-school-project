package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.city_list.CityListAdapter;
import com.example.weatherapp.city_list.CityWeatherModel;
import com.example.weatherapp.utils.viewmodels.CityWeatherViewModel;
import com.example.weatherapp.utils.viewmodels.ViewModelFactory;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CityListActivity extends AppCompatActivity implements CityListAdapter.CityListInterface{

    private MaterialToolbar toolbar;
    private FrameLayout iconContainer;
    private ImageView icBack;
    private RelativeLayout citiesLayout;
    private RecyclerView cities;
    private FloatingActionButton fab;
    private TextView hint;
    private CityWeatherViewModel cityWeatherViewModel;
    private List<CityWeatherModel> models = new ArrayList<>();
    private CityListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        toolbar = findViewById(R.id.toolbar);
        iconContainer = findViewById(R.id.toolbar_icon_container);
        icBack = findViewById(R.id.ic_back);
        citiesLayout = findViewById(R.id.cities_layout);
        cities = findViewById(R.id.cities);
        fab = findViewById(R.id.fab);
        hint = findViewById(R.id.no_city_view);

        cityWeatherViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(CityWeatherViewModel.class);

        cityWeatherViewModel.init(this);

        LiveData<List<CityWeatherModel>> liveDataList = cityWeatherViewModel.getCityWeatherModels();
        List<CityWeatherModel> liveDataListValue = liveDataList.getValue();

        if(liveDataListValue != null && !liveDataListValue.isEmpty()){
            models.clear();
            CityWeatherModel locatedModel = null;
            List<CityWeatherModel> tempList = new ArrayList<>();
            for(CityWeatherModel m: liveDataListValue){
                if(m.getCityModel().isLocated())
                    locatedModel = m;
                else{
                    tempList.add(m);
                }
            }
            if(locatedModel != null)
                models.add(locatedModel);
            models.addAll(tempList);
        }


        if (models.isEmpty()) {
            hint.setVisibility(View.VISIBLE);
            cities.setVisibility(View.GONE);
        }else{
            hint.setVisibility(View.GONE);
            cities.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        adapter = new CityListAdapter(this, models);

        cities.setLayoutManager(linearLayoutManager);
        cities.setAdapter(adapter);

        iconContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CityListActivity.this, SearchActivity.class));
            }
        });
    }

    @Override
    public void deleteItem() {
        Toast.makeText(this, "Usunięty wpis", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void selectItem() {
        Toast.makeText(this, "Kliknięto widok", Toast.LENGTH_SHORT).show();
    }
}