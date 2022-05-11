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
import com.example.weatherapp.fragments.HourForecast;
import com.example.weatherapp.utils.AlwaysMarqueeTextView;
import com.example.weatherapp.weather.model.HourlyWeather;

import java.util.List;

public class HourForecastAdapter extends RecyclerView.Adapter<HourForecastAdapter.ViewHolder> {

    private Context context;
    private List<HourlyWeather> hourlyWeatherList;

    public HourForecastAdapter(Context context, List<HourlyWeather> hourlyWeatherList) {
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
        HourlyWeather hourlyWeather = hourlyWeatherList.get(position);
        holder.time.setText((CharSequence) hourlyWeather.getTime());
        holder.temperature.setText((CharSequence) hourlyWeather.getTemperature());
        Glide.with(context).load(context.getDrawable(hourlyWeather.getSrc_weather())).into(holder.weather_image);
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
