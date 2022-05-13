package com.example.weatherapp.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.CityListActivity;
import com.example.weatherapp.R;
import com.example.weatherapp.city_list.CityWeatherModel;
import com.example.weatherapp.utils.viewmodels.CityWeatherViewModel;
import com.example.weatherapp.utils.viewmodels.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class CitySearchAdapter extends RecyclerView.Adapter<CitySearchAdapter.ViewHolder> {

    private Context context;
    private List<CityModel> models;
    private SearchInterface searchInterface;
    private static List<CityModel> added = new ArrayList<>();
    private static CityWeatherViewModel cityWeatherViewModel;

    public CitySearchAdapter(Context context, List<CityModel> models) {
        this.context = context;
        this.models = models;
        searchInterface = (SearchInterface) context;
    }

    public interface SearchInterface{
        void search(CityModel model);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searched_city_layout, parent, false);
        cityWeatherViewModel = new ViewModelProvider((ViewModelStoreOwner) context, new ViewModelFactory()).get(CityWeatherViewModel.class);
        added.clear();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        List<CityModel> tadded = new ArrayList<>();
        if(cityWeatherViewModel.getCityWeatherModels().getValue() != null && !cityWeatherViewModel.getCityWeatherModels().getValue().isEmpty()){
            for(CityWeatherModel cwm : cityWeatherViewModel.getCityWeatherModels().getValue()){
                tadded.add(cwm.getCityModel());
            }
        }
        if(added.isEmpty())
            added.addAll(tadded);


        CityModel cityModel = models.get(position);
        holder.cityName.setText(cityModel.getFreeFormAddress());
        holder.region.setText(cityModel.getCountrySubdivision() + ", " + cityModel.getCountry());

        if(isAdded(cityModel)){
            holder.addedLabel.setVisibility(View.VISIBLE);
            cityModel.setAdded(true);
        }else{
            holder.addedLabel.setVisibility(View.GONE);
        }

    }

    public boolean isAdded(CityModel model){
        for(CityModel model1 : added){
            if((model.getLat() == model1.getLat() && model.getLon() == model1.getLon())
                    ||( model.getFreeFormAddress().equals(model1.getFreeFormAddress()) && model.getCountrySubdivision().equals(model1.getCountrySubdivision())))
                    return true;
        }
        return false;
    }
    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView cityName, addedLabel, region;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cityName = itemView.findViewById(R.id.city_name);
            addedLabel = itemView.findViewById(R.id.added_label);
            region = itemView.findViewById(R.id.region);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    CityModel temp = models.get(getAdapterPosition());
                    if(!temp.isAdded())
                        searchInterface.search(models.get(getAdapterPosition()));
                    else{
                        Toast.makeText(context, "City already added", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, CityListActivity.class));
                    }
                }
            });

        }
    }
}
