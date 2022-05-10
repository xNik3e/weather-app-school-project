package com.example.weatherapp.remote.model.one_call_hourly;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OneCallHourlyWeatherResponse{

	@SerializedName("timezone")
	private String timezone;

	@SerializedName("timezone_offset")
	private int timezoneOffset;

	@SerializedName("lon")
	private double lon;

	@SerializedName("hourly")
	private List<HourlyItem> hourly;

	@SerializedName("lat")
	private double lat;

	public String getTimezone(){
		return timezone;
	}

	public int getTimezoneOffset(){
		return timezoneOffset;
	}

	public double getLon(){
		return lon;
	}

	public List<HourlyItem> getHourly(){
		return hourly;
	}

	public double getLat(){
		return lat;
	}
}