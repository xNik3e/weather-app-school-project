package com.example.weatherapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.R;
import com.example.weatherapp.remote.model.one_call_current_weather.Current;
import com.example.weatherapp.remote.model.one_call_current_weather.OneCallWeatherResponse;
import com.example.weatherapp.utils.WeatherUtils;
import com.example.weatherapp.utils.view_services.WeatherSingleInfoView;

import java.text.DecimalFormat;
import java.util.Map;

public class RealtimeWeatherDetail extends Fragment {

    private WeatherSingleInfoView precipitation_prob, precipitation, wind, temp, hum, visibility, uv, pressure;
    private static OneCallWeatherResponse current;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public RealtimeWeatherDetail() {
        // Required empty public constructor
    }

    public RealtimeWeatherDetail(OneCallWeatherResponse temp) {
        this.current = temp;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_realtime_weather_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        precipitation_prob = view.findViewById(R.id.single_precipitation_probability);
        precipitation = view.findViewById(R.id.single_precipitation);
        wind = view.findViewById(R.id.single_wind_view);
        temp = view.findViewById(R.id.single_bodytemp_view);
        hum = view.findViewById(R.id.single_hum);
        visibility = view.findViewById(R.id.single_visibility);
        uv = view.findViewById(R.id.single_uv);
        pressure = view.findViewById(R.id.single_pressure);

        precipitation_prob.setInfoLevel((current.getDaily().get(0).getPop() * 100) + "");
        precipitation_prob.setInfoUnit("%");

        Map<String, Double> params = WeatherUtils.convertPrecipitation(getContext(), (current.getDaily().get(0).getRain()));
        precipitation.setInfoLevel(df.format(WeatherUtils.getValue(params)));
        precipitation.setInfoUnit(WeatherUtils.getKey(params));

        wind.setInfoType(windType(current.getDaily().get(0).getWindDeg()));

        params = WeatherUtils.convertWind(getContext(), current.getDaily().get(0).getWindSpeed());
        wind.setInfoLevel(df.format(WeatherUtils.getValue(params)));
        wind.setInfoUnit(WeatherUtils.getKey(params));

        params = WeatherUtils.convertTemp(getContext(), current.getCurrent().getFeelsLike());
        temp.setInfoLevel((int)WeatherUtils.getValue(params) + "");
        temp.setInfoUnit(WeatherUtils.getKey(params));

        hum.setInfoLevel((int)current.getDaily().get(0).getHumidity()+"");
        hum.setInfoUnit("%");

        params = WeatherUtils.convertVisibility(getContext(), current.getCurrent().getVisibility()/1000);
        visibility.setInfoLevel(df.format(WeatherUtils.getValue(params)));
        visibility.setInfoUnit(WeatherUtils.getKey(params));

        uv.setInfoLevel(current.getDaily().get(0).getUvi()+"");

        pressure.setInfoLevel(df.format(current.getDaily().get(0).getPressure()));
        pressure.setInfoUnit("hPa");
    }

    public String windType(double windDeg) {
        if ((windDeg > 348.75 && windDeg <= 360) || (windDeg >= 0 && windDeg <= 11.25))
            return getString(R.string.api_wind_direction_n);
        else if (windDeg > 11.25 && windDeg <= 33.75)
            return getString(R.string.api_wind_direction_nne);
        else if (windDeg > 33.75 && windDeg <= 56.25)
            return getString(R.string.api_wind_direction_ne);
        else if (windDeg > 56.25 && windDeg <= 78.75)
            return getString(R.string.api_wind_direction_ene);
        else if (windDeg > 78.75 && windDeg <= 101.25)
            return getString(R.string.api_wind_direction_e);
        else if (windDeg > 101.25 && windDeg <= 123.75)
            return getString(R.string.api_wind_direction_ese);
        else if (windDeg > 123.75 && windDeg <= 146.25)
            return getString(R.string.api_wind_direction_se);
        else if(windDeg > 146.25 && windDeg <= 168.75)
            return getString(R.string.api_wind_direction_sse);
        else if(windDeg > 168.75 && windDeg <= 191.25)
            return getString(R.string.api_wind_direction_s);
        else if(windDeg > 191.25 && windDeg <= 213.75)
            return getString(R.string.api_wind_direction_ssw);
        else if(windDeg > 213.75 && windDeg <= 236.25)
            return getString(R.string.api_wind_direction_sw);
        else if(windDeg > 236.25 && windDeg <= 258.75)
            return getString(R.string.api_wind_direction_wsw);
        else if(windDeg > 258.75 && windDeg <= 281.25)
            return getString(R.string.api_wind_direction_w);
        else if(windDeg > 281.25 && windDeg <= 303.75)
            return getString(R.string.api_wind_direction_wnw);
        else if(windDeg > 303.75 && windDeg <= 326.25)
            return getString(R.string.api_wind_direction_nw);
        else
            return getString(R.string.api_wind_direction_nnw);

    }
}