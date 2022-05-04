package com.example.weatherapp.remote.model.GeoApiModel;

import com.google.gson.annotations.SerializedName;

public class DataSources{

	@SerializedName("geometry")
	private Geometry geometry;

	public Geometry getGeometry(){
		return geometry;
	}
}