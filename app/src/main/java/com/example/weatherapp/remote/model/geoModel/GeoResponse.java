package com.example.weatherapp.remote.model.geoModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GeoResponse{

	@SerializedName("GeoResponse")
	private List<GeoResponseItem> geoResponse;

	public void setGeoResponse(List<GeoResponseItem> geoResponse){
		this.geoResponse = geoResponse;
	}

	public List<GeoResponseItem> getGeoResponse(){
		return geoResponse;
	}
}