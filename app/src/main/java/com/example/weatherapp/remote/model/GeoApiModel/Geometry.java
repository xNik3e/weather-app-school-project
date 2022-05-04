package com.example.weatherapp.remote.model.GeoApiModel;

import com.google.gson.annotations.SerializedName;

public class Geometry{

	@SerializedName("id")
	private String id;

	public String getId(){
		return id;
	}
}