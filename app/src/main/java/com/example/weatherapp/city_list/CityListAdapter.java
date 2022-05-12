package com.example.weatherapp.city_list;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.utils.EnumHelper;
import com.example.weatherapp.utils.WeatherUtils;
import com.example.weatherapp.weather.WeatherEnum;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {

    private Context context;
    private List<CityWeatherModel> models;
    private CityListInterface listInterface;
    private static final int MIN_BEFORE_OUTDATED = 60;

    public interface CityListInterface {
        void deleteItem(int position);

        void selectItem();
    }

    public CityListAdapter(Context context, List<CityWeatherModel> models) {
        this.context = context;
        this.models = models;
        this.listInterface = (CityListInterface) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.city_single_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CityWeatherModel model = models.get(position);
        if (model.getCityModel().isLocated()) {
            holder.currentLocation.setVisibility(View.VISIBLE);
        } else {
            holder.currentLocation.setVisibility(View.GONE);
        }

        holder.cityName.setText(model.getCityModel().getFreeFormAddress());

        //switch if is not Celcius

        Map<String, Double> temp = WeatherUtils.convertTemp(context, model.getCurrentWeather().getCurrent().getFeelsLike());

        holder.cityTemp.setText((int)WeatherUtils.getValue(temp) + WeatherUtils.getKey(temp));
        WeatherEnum weatherEnum = EnumHelper.getResourcesByWeatherId(model.getCurrentWeather().getCurrent().getWeather().get(0).getId());

        holder.weatherType.setText(context.getResources().getString(weatherEnum.getDescriptionID()));

        int drawableID = weatherEnum.getCityBackgroundWeatherResId(EnumHelper.isDay(
                model.getCurrentWeather().getCurrent().getSunset(),
                (int) model.getCurrentWeather().getCurrent().getDt(),
                model.getCurrentWeather().getCurrent().getSunrise()));

        Glide.with(context).load(context.getResources().getDrawable(drawableID)).into(holder.background);

        long ModelEpoch = model.getLastUpdate();
        long ThisEpoch = System.currentTimeMillis() / 1000;

        long diffSec = (ThisEpoch - ModelEpoch);

        if(diffSec > MIN_BEFORE_OUTDATED * 60){
            holder.background.setColorFilter(context.getColor(R.color.body_color_dark));
            holder.outdatedInfo.setVisibility(View.VISIBLE);

            holder.outdatedInfo.setText(WeatherUtils.getLastUpdatedString(ModelEpoch));
            Toast.makeText(context, "Weather is outdated", Toast.LENGTH_SHORT).show();
        }else{
            holder.outdatedInfo.setVisibility(View.GONE);
            holder.background.setColorFilter(context.getColor(R.color.transparent));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView background, currentLocation;
        TextView cityName, cityTemp, weatherType, delete, outdatedInfo;
        CardView wholeContainer;
        public ViewHolder(@NonNull View v) {
            super(v);

            background = v.findViewById(R.id.city_theme);
            currentLocation = v.findViewById(R.id.current_location);
            cityName = v.findViewById(R.id.city_name);
            cityTemp = v.findViewById(R.id.city_temp);
            weatherType = v.findViewById(R.id.weather_type);
            delete = v.findViewById(R.id.delete_city);
            wholeContainer = v.findViewById(R.id.whole_container);
            outdatedInfo = v.findViewById(R.id.outdated_info);

            wholeContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listInterface.selectItem();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listInterface.deleteItem(getAdapterPosition());
                }
            });
        }
    }
}
