package com.example.weatherapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.remote.model.one_call_current_weather.DailyItem;
import com.example.weatherapp.utils.EnumHelper;
import com.example.weatherapp.utils.view_services.AlwaysMarqueeTextView;
import com.example.weatherapp.weather.WeatherEnum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ForecastWeather extends Fragment {

    private static List<DailyItem> items = new ArrayList<>();
    private View day1, day2, day3, day4, day5, day6;
    private AlwaysMarqueeTextView _day1, _day2, _day3, _day4, _day5, _day6;
    private AlwaysMarqueeTextView _date1, _date2, _date3, _date4, _date5, _date6;
    private AlwaysMarqueeTextView _desc1, _desc2, _desc3, _desc4, _desc5, _desc6;
    private ImageView _ic1, _ic2, _ic3, _ic4, _ic5, _ic6;

    public ForecastWeather() {
        // Required empty public constructor
    }

    public ForecastWeather(List<DailyItem> dailyItems) {
        this.items.addAll(dailyItems);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forecast_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        day1 = view.findViewById(R.id.day1);
        day2 = view.findViewById(R.id.day2);
        day3 = view.findViewById(R.id.day3);
        day4 = view.findViewById(R.id.day4);
        day5 = view.findViewById(R.id.day5);
        day6 = view.findViewById(R.id.day6);

        List<AlwaysMarqueeTextView> days = new ArrayList<>();
        List<AlwaysMarqueeTextView> dates = new ArrayList<>();
        List<AlwaysMarqueeTextView> descriptions = new ArrayList<>();
        List<ImageView> icons = new ArrayList<>();

        _day1 = day1.findViewById(R.id.day);
        days.add(_day1);
        _date1 = day1.findViewById(R.id.day_date);
        dates.add(_date1);
        _desc1 = day1.findViewById(R.id.forecast_daily_weather_description);
        descriptions.add(_desc1);
        _ic1 = day1.findViewById(R.id.forecast_daily_weather_icon);
        icons.add(_ic1);

        _day2 = day2.findViewById(R.id.day);
        days.add(_day2);
        _date2 = day2.findViewById(R.id.day_date);
        dates.add(_date2);
        _desc2 = day2.findViewById(R.id.forecast_daily_weather_description);
        descriptions.add(_desc2);
        _ic2 = day2.findViewById(R.id.forecast_daily_weather_icon);
        icons.add(_ic2);

        _day3 = day3.findViewById(R.id.day);
        days.add(_day3);
        _date3 = day3.findViewById(R.id.day_date);
        dates.add(_date3);
        _desc3 = day3.findViewById(R.id.forecast_daily_weather_description);
        descriptions.add(_desc3);
        _ic3 = day3.findViewById(R.id.forecast_daily_weather_icon);
        icons.add(_ic3);

        _day4 = day4.findViewById(R.id.day);
        days.add(_day4);
        _date4 = day4.findViewById(R.id.day_date);
        dates.add(_date4);
        _desc4 = day4.findViewById(R.id.forecast_daily_weather_description);
        descriptions.add(_desc4);
        _ic4 = day4.findViewById(R.id.forecast_daily_weather_icon);
        icons.add(_ic4);


        _day5 = day5.findViewById(R.id.day);
        days.add(_day5);
        _date5 = day5.findViewById(R.id.day_date);
        dates.add(_date5);
        _desc5 = day5.findViewById(R.id.forecast_daily_weather_description);
        descriptions.add(_desc5);
        _ic5 = day5.findViewById(R.id.forecast_daily_weather_icon);
        icons.add(_ic5);

        _day6 = day6.findViewById(R.id.day);
        days.add(_day6);
        _date6 = day6.findViewById(R.id.day_date);
        dates.add(_date6);
        _desc6 = day6.findViewById(R.id.forecast_daily_weather_description);
        descriptions.add(_desc6);
        _ic6 = day6.findViewById(R.id.forecast_daily_weather_icon);
        icons.add(_ic6);


        //Handle updating views
        for (int i = 0; i < 6; i++) {
            setDate(dates.get(i), days.get(i), items.get(i).getDt());
            setDescription(descriptions.get(i), items.get(i).getWeather().get(0).getDescription());
            WeatherEnum weatherEnum = EnumHelper.getResourcesByWeatherId(items.get(i).getWeather().get(0).getId());
            int iconId = weatherEnum.getWeatherIconResId();
            setIcon(icons.get(i), iconId);
        }
    }

    public void setDay(AlwaysMarqueeTextView textView, String day) {
        textView.setText(day);
    }

    public void setDate(AlwaysMarqueeTextView textView_date, AlwaysMarqueeTextView textView_day, double dt) {
        Date date = new Date((long) dt * 1000);
        Calendar cal = Calendar.getInstance();
        String format = "dd MMM";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat(format);
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        textView_date.setText(df.format(date));

        String dayS = "";
        switch (day) {
            case 1:
                dayS = "Sun";
                break;
            case 2:
                dayS = "Mon";
                break;
            case 3:
                dayS = "Tue";
                break;
            case 4:
                dayS = "Wed";
                break;
            case 5:
                dayS = "Thu";
                break;
            case 6:
                dayS = "Fri";
                break;
            case 7:
                dayS = "Sat";
                break;
        }
        setDay(textView_day, dayS);
    }

    public void setDescription(AlwaysMarqueeTextView textView, String description) {
        String newDesc = description.substring(0, 1).toUpperCase() + description.substring(1);
        textView.setText(newDesc);
    }

    public void setIcon(ImageView source, int resId) {
        Glide.with(getContext()).load(getResources().getDrawable(resId)).into(source);
    }
}
