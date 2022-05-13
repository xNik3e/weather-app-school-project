package com.example.weatherapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.R;
import com.example.weatherapp.weather.HourForecastAdapter;
import com.example.weatherapp.weather.model.HourlyFragmentModel;

import java.util.ArrayList;
import java.util.List;

public class HourForecast extends Fragment {

    private HourForecastAdapter hourForecastAdapter;
    private RecyclerView recyclerView;
    private Context context;
    private static List<HourlyFragmentModel> hourlyWeatherList = new ArrayList<>();

    public HourForecast(List<HourlyFragmentModel> models){
        hourlyWeatherList.clear();
        this.hourlyWeatherList.addAll(models);
    }

    public HourForecast() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hour_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);
        hourForecastAdapter = new HourForecastAdapter(context, hourlyWeatherList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(hourForecastAdapter);

    }
}