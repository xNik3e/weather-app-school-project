package com.example.weatherapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.city_list.CityWeatherModel;
import com.example.weatherapp.remote.model.one_call_current_weather.DailyItem;
import com.example.weatherapp.remote.model.one_call_current_weather.HourlyItem;
import com.example.weatherapp.utils.EnumHelper;
import com.example.weatherapp.utils.WeatherUtils;
import com.example.weatherapp.utils.view_services.AlwaysMarqueeTextView;
import com.example.weatherapp.utils.view_services.MyWeatherTemperatureView;
import com.example.weatherapp.utils.viewmodels.CityWeatherViewModel;
import com.example.weatherapp.utils.viewmodels.ViewModelFactory;
import com.example.weatherapp.weather.WeatherEnum;
import com.example.weatherapp.weather.WeatherType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class WeatherInfo extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout relativeLayoutInfo;
    private ScrollView weatherScrollView;
    private Space blankSpace;
    private LinearLayout realtimeTemperature;
    private TextView currentTemperature;
    private AlwaysMarqueeTextView currentWeatherType;
    private TextView currentHighTemperature;
    private TextView currentLowTemperature;
    private FrameLayout forecastWeatherFrameLayout;
    private LinearLayout weatherTempViewLayout;
    private MyWeatherTemperatureView weatherTempView;
    private LinearLayout fifteenDayForecastBox;
    private TextView fifteenDayForecast;
    private FrameLayout hourlyForecastFrameLayout;
    private FrameLayout realtimeWeatherDetailsFrameLayout;
    private FragmentManager fragmentManager;
    private Context context;
    private ForecastWeather forecastWeather;
    private HourForecast hourForecast;
    private RealtimeWeatherDetail realtimeWeatherDetail;
    private int tabPosition = 0;
    private CityWeatherViewModel cityWeatherViewModel;
    private CityWeatherModel finalModel;
    private NotifyMainActivity notifyMainActivity;


    public WeatherInfo(int position) {
        this.tabPosition = position;
    }

    public WeatherInfo(){
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        notifyMainActivity = (NotifyMainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cityWeatherViewModel = new ViewModelProvider(getActivity(), new ViewModelFactory()).get(CityWeatherViewModel.class);

        LiveData<List<CityWeatherModel>> liveData = cityWeatherViewModel.getCityWeatherModels();
        List<CityWeatherModel> cityWeatherModelList = liveData.getValue();

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        relativeLayoutInfo = view.findViewById(R.id.relative_layout_info);
        weatherScrollView = view.findViewById(R.id.weather_scroll_view);
        blankSpace = view.findViewById(R.id.blank_area);
        realtimeTemperature = view.findViewById(R.id.realtime_temperature);
        currentTemperature = view.findViewById(R.id.current_temperature);
        currentWeatherType = view.findViewById(R.id.current_weather_type);
        currentHighTemperature = view.findViewById(R.id.current_high_temperature);
        currentLowTemperature = view.findViewById(R.id.current_low_temperature);
        forecastWeatherFrameLayout = view.findViewById(R.id.forecast_weather);
        weatherTempViewLayout = view.findViewById(R.id.weather_temp_view_layout);
        weatherTempView = view.findViewById(R.id.weather_temp_view);
        fifteenDayForecastBox = view.findViewById(R.id.fifteen_day_forecast_box);
        fifteenDayForecast = view.findViewById(R.id.fifteen_day_forecast);
        hourlyForecastFrameLayout = view.findViewById(R.id.hourly_forecast);
        realtimeWeatherDetailsFrameLayout = view.findViewById(R.id.realtime_weather_detail);

        this.finalModel = cityWeatherModelList.get(tabPosition);
        notifyMainActivity.sendData(finalModel.getCityModel().getFreeFormAddress(), WeatherUtils.getLastUpdatedString(finalModel.getLastUpdate()), finalModel.getCityModel().isLocated());

        WeatherEnum weatherEnum = EnumHelper.getResourcesByWeatherId(finalModel.getCurrentWeather().getCurrent().getWeather().get(0).getId());

        boolean isDay = EnumHelper.isDay(
                finalModel.getCurrentWeather().getCurrent().getSunset(),
                (int) finalModel.getCurrentWeather().getCurrent().getDt(),
                finalModel.getCurrentWeather().getCurrent().getSunrise());
        int drawableID = weatherEnum.getCityBackgroundWeatherResId(isDay);
        int colorId = weatherEnum.getTypes().getWeatherColorResId(isDay);
        notifyMainActivity.changeBackground(drawableID,colorId);

        Map<String, Double> params = WeatherUtils.convertTemp(getContext(), finalModel.getCurrentWeather().getCurrent().getFeelsLike());
        currentTemperature.setText("Feels like\n" + (int)WeatherUtils.getValue(params)+WeatherUtils.getKey(params));
        currentWeatherType.setText(finalModel.getCurrentWeather().getCurrent().getWeather().get(0).getMain());
        params = WeatherUtils.convertTemp(getContext(), finalModel.getCurrentWeather().getDaily().get(0).getTemp().getDay());
        currentHighTemperature.setText((int)WeatherUtils.getValue(params)+"");


        params = WeatherUtils.convertTemp(getContext(), finalModel.getCurrentWeather().getDaily().get(0).getTemp().getNight());
        currentLowTemperature.setText((int)WeatherUtils.getValue(params)+ WeatherUtils.getKey(params));


        List<DailyItem> dailyItems = new ArrayList<>();
        dailyItems.addAll(finalModel.getCurrentWeather().getDaily());

        forecastWeather = new ForecastWeather(dailyItems);

        List<Integer> dayTemp = new ArrayList<>();
        List<Integer> nightTemp = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            dayTemp.add((int)dailyItems.get(i).getTemp().getDay());
            nightTemp.add((int)dailyItems.get(i).getTemp().getNight());
        }
        weatherTempView.addData(dayTemp, nightTemp);

        //Add this lastly
        hourForecast = new HourForecast();


        realtimeWeatherDetail = new RealtimeWeatherDetail(finalModel.getCurrentWeather());

        fragmentManager = getChildFragmentManager();

        setFragment(R.id.forecast_weather, forecastWeather, fragmentManager);

        //change this fragment

        setFragment(R.id.hourly_forecast, hourForecast, fragmentManager);
        setFragment(R.id.realtime_weather_detail, realtimeWeatherDetail, fragmentManager);
    }

    private void setFragment(int container, Fragment fragment, FragmentManager fm) {
        fm.beginTransaction().replace(container, fragment).commit();
    }

    public interface NotifyMainActivity {
        void sendData(String title, String updated, boolean isLocated);
        void changeBackground(int resId, int colorId);
    }
}