package com.example.weatherapp.remote.model.one_call_current_weather;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Temp {

	@SerializedName("min")
	private double min;

	@SerializedName("max")
	private double max;

	@SerializedName("eve")
	private double eve;

	@SerializedName("night")
	private double night;

	@SerializedName("day")
	private double day;

	@SerializedName("morn")
	private double morn;

	public double getMin(){
		return min;
	}

	public double getMax(){
		return max;
	}

	public double getEve(){
		return eve;
	}

	public double getNight(){
		return night;
	}

	public double getDay(){
		return day;
	}

	public double getMorn(){
		return morn;
	}
}