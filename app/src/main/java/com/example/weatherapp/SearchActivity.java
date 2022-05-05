package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.weatherapp.remote.model.ApiParams;
import com.example.weatherapp.remote.model.GeoApiModel.GeoCodeFuzzy;
import com.example.weatherapp.remote.model.GeoApiModel.ResultsItem;
import com.example.weatherapp.search.CityModel;
import com.example.weatherapp.search.CitySearchAdapter;
import com.example.weatherapp.search.CitySearchViewModel;
import com.example.weatherapp.utils.ClearableEditText;
import com.example.weatherapp.utils.ViewModelFactory;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements TextWatcher {

    private MaterialToolbar toolbar;
    private FrameLayout iconContainer;
    private ImageView icBack;
    private ClearableEditText cEditText;
    private LinearLayout locatedCityContent;
    private TextView locatedCityTitle;
    private RelativeLayout relativeLayout;
    private RecyclerView searchedCities;
    private TextView hint;
    private List<CityModel> models;
    private CitySearchAdapter adapter;
    private CitySearchViewModel citySearchViewModel;
    private final ApiParams apiParams = new ApiParams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        citySearchViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(CitySearchViewModel.class);
        toolbar = findViewById(R.id.toolbar);
        iconContainer = findViewById(R.id.toolbar_icon_container);
        icBack = findViewById(R.id.ic_back);
        cEditText = findViewById(R.id.search_bar_input_field);
        locatedCityContent = findViewById(R.id.located_city_content);
        locatedCityTitle = findViewById(R.id.located_city_title);
        relativeLayout = findViewById(R.id.relative_layout);
        searchedCities = findViewById(R.id.searched_cities);
        hint = findViewById(R.id.hint_text_view);

        models = new ArrayList<>();
        adapter = new CitySearchAdapter(this, models);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        searchedCities.setLayoutManager(linearLayoutManager);
        searchedCities.setAdapter(adapter);
        iconContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.super.onBackPressed();
            }
        });


        cEditText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() >= 2) {
            setEverythingHidden(true);
            search(s.toString());
        } else {
            if (s.length() == 1) {
                locatedCityTitle.setVisibility(View.GONE);
                locatedCityContent.setVisibility(View.GONE);
            } else
                setEverythingVisible(true);

            hint.setText(getResources().getString(R.string.search_something));
            hint.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_search), null, null);
            models.clear();
            adapter.notifyDataSetChanged();
        }
    }

    private void search(String query) {
        citySearchViewModel.getGeoLocation(query, apiParams.getParams()).observe(SearchActivity.this, new Observer<GeoCodeFuzzy>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onChanged(GeoCodeFuzzy geoCodeFuzzy) {
                if (geoCodeFuzzy.getResults() != null && !geoCodeFuzzy.getResults().isEmpty()) {
                    setEverythingHidden(true);
                    models.clear();
                    for (ResultsItem r : geoCodeFuzzy.getResults()) {
                        CityModel cityModel = new CityModel();
                        cityModel.setAdded(false);
                        cityModel.setFreeFormAddress(r.getAddress().getFreeformAddress());
                        cityModel.setCountry(r.getAddress().getCountry());
                        cityModel.setCountrySubdivision(r.getAddress().getCountrySubdivision());
                        cityModel.setLat(r.getPosition().getLat());
                        cityModel.setLon(r.getPosition().getLon());
                        models.add(cityModel);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    setEverythingVisible(false);
                    hint.setText(getResources().getString(R.string.no_matches));
                    hint.setCompoundDrawablesWithIntrinsicBounds(null, SearchActivity.this.getResources().getDrawable(R.drawable.no_search_icon), null, null);
                }
            }
        });
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void setEverythingHidden(boolean isLocated) {
        hint.setVisibility(View.GONE);
        if (isLocated) {
            locatedCityTitle.setVisibility(View.GONE);
            locatedCityContent.setVisibility(View.GONE);
        }
        relativeLayout.setVisibility(View.VISIBLE);
    }

    private void setEverythingVisible(boolean isLocated) {
        hint.setVisibility(View.VISIBLE);
        if (isLocated) {
            locatedCityTitle.setVisibility(View.VISIBLE);
            locatedCityContent.setVisibility(View.VISIBLE);
        }
        relativeLayout.setVisibility(View.GONE);
    }
}