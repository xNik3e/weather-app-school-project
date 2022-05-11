package com.example.weatherapp.remote.model.one_call_current_weather;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OneCallWeatherResponse {
	public OneCallWeatherResponse() {
	}

	@SerializedName("current")
	private Current current;

	@SerializedName("timezone")
	private String timezone;

	@SerializedName("timezone_offset")
	private int timezoneOffset;

	@SerializedName("daily")
	private List<DailyItem> daily;

	@SerializedName("lon")
	private double lon;

	@SerializedName("hourly")
	private List<HourlyItem> hourly;

	@SerializedName("lat")
	private double lat;

	public Current getCurrent(){
		return current;
	}

	public String getTimezone(){
		return timezone;
	}

	public int getTimezoneOffset(){
		return timezoneOffset;
	}

	public List<DailyItem> getDaily(){
		return daily;
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