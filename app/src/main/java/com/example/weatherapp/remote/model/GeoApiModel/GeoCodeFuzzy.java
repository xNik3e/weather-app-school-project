package com.example.weatherapp.remote.model.GeoApiModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GeoCodeFuzzy{

	@SerializedName("summary")
	private Summary summary;

	@SerializedName("results")
	private List<ResultsItem> results;

	public Summary getSummary(){
		return summary;
	}

	public List<ResultsItem> getResults(){
		return results;
	}
}