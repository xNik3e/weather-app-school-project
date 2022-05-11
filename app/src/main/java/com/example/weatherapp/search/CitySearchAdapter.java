package com.example.weatherapp.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.CityListActivity;
import com.example.weatherapp.R;

import java.util.List;

public class CitySearchAdapter extends RecyclerView.Adapter<CitySearchAdapter.ViewHolder> {

    private Context context;
    private List<CityModel> models;

    public CitySearchAdapter(Context context, List<CityModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searched_city_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CityModel cityModel = models.get(position);
        holder.cityName.setText(cityModel.getFreeFormAddress());
        holder.region.setText(cityModel.getCountrySubdivision() + ", " + cityModel.getCountry());
        if(cityModel.isAdded()){
            holder.addedLabel.setVisibility(View.VISIBLE);
        }else
            holder.addedLabel.setVisibility(View.GONE);
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
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("city", models.get(getAdapterPosition()));
                    context.startActivity(new Intent(context, CityListActivity.class).putExtras(bundle));
                }
            });

        }
    }
}
