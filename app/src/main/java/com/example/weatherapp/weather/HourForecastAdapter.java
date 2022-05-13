package com.example.weatherapp.weather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.utils.EnumHelper;
import com.example.weatherapp.utils.WeatherUtils;
import com.example.weatherapp.utils.view_services.AlwaysMarqueeTextView;
import com.example.weatherapp.weather.model.HourlyFragmentModel;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HourForecastAdapter extends RecyclerView.Adapter<HourForecastAdapter.ViewHolder> {

    private Context context;
    private List<HourlyFragmentModel> hourlyWeatherList;
    private static final int SUNRISE = 0;
    private static final int SUNSET = 1;
    private static final int NONE = -1;

    public HourForecastAdapter(Context context, List<HourlyFragmentModel> hourlyWeatherList) {
        this.context = context;
        this.hourlyWeatherList = hourlyWeatherList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forecast_hourly_weather, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HourlyFragmentModel hourlyWeather = hourlyWeatherList.get(position);
        String pattern = "HH:mm";
        Date dtime = new Date((long) hourlyWeather.getDt() * 1000);
        LocalTime time = LocalDateTime.ofInstant(dtime.toInstant(), ZoneId.systemDefault()).toLocalTime();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        holder.time.setText(dtf.format(time));
        if (hourlyWeather.getTYPE() == NONE) {
            Map<String, Double> params = WeatherUtils.convertTemp(context, hourlyWeather.getTemp());
            holder.temperature.setText((int)WeatherUtils.getValue(params)+WeatherUtils.getKey(params));
            WeatherEnum weatherEnum = EnumHelper.getResourcesByWeatherId(hourlyWeather.getId());
            int resID = weatherEnum.getWeatherIconResId();
            Glide.with(context).load(context.getDrawable(resID)).into(holder.weather_image);
        } else if (hourlyWeather.getTYPE() == SUNRISE) {
            Glide.with(context).load(context.getDrawable(R.drawable.ic_sunrise)).into(holder.weather_image);
            holder.temperature.setText("");
        }else{
            Glide.with(context).load(context.getDrawable(R.drawable.ic_sunset)).into(holder.weather_image);
            holder.temperature.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return hourlyWeatherList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AlwaysMarqueeTextView time, temperature;
        ImageView weather_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.weather_time);
            temperature = itemView.findViewById(R.id.weather_temperature);
            weather_image = itemView.findViewById(R.id.weather_icon);
        }
    }
}
