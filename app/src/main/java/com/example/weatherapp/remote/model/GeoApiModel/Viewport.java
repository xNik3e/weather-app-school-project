package com.example.weatherapp.remote.model.GeoApiModel;

import com.google.gson.annotations.SerializedName;

public class Viewport{

	@SerializedName("btmRightPoint")
	private BtmRightPoint btmRightPoint;

	@SerializedName("topLeftPoint")
	private TopLeftPoint topLeftPoint;

	public BtmRightPoint getBtmRightPoint(){
		return btmRightPoint;
	}

	public TopLeftPoint getTopLeftPoint(){
		return topLeftPoint;
	}
}