package com.example.weatherapp.remote.model.GeoApiModel;

import com.google.gson.annotations.SerializedName;

public class TopLeftPoint{

	@SerializedName("lon")
	private double lon;

	@SerializedName("lat")
	private double lat;

	public double getLon(){
		return lon;
	}

	public double getLat(){
		return lat;
	}
}