package com.example.weatherapp.remote.model.one_call_hourly;

import com.google.gson.annotations.SerializedName;

public class WeatherItem{

	@SerializedName("icon")
	private String icon;

	@SerializedName("description")
	private String description;

	@SerializedName("main")
	private String main;

	@SerializedName("id")
	private int id;

	public String getIcon(){
		return icon;
	}

	public String getDescription(){
		return description;
	}

	public String getMain(){
		return main;
	}

	public int getId(){
		return id;
	}
}