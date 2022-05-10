package com.example.weatherapp.remote.model.one_call_current_weather;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Current {

	@SerializedName("sunrise")
	private int sunrise;

	@SerializedName("temp")
	private double temp;

	@SerializedName("visibility")
	private int visibility;

	@SerializedName("uvi")
	private double uvi;

	@SerializedName("pressure")
	private double pressure;

	@SerializedName("clouds")
	private int clouds;

	@SerializedName("feels_like")
	private double feelsLike;

	@SerializedName("dt")
	private double dt;

	@SerializedName("wind_deg")
	private double windDeg;

	@SerializedName("dew_point")
	private double dewPoint;

	@SerializedName("sunset")
	private int sunset;

	@SerializedName("weather")
	private List<WeatherItem> weather;

	@SerializedName("humidity")
	private double humidity;

	@SerializedName("wind_speed")
	private double windSpeed;

	public int getSunrise(){
		return sunrise;
	}

	public double getTemp(){
		return temp;
	}

	public int getVisibility(){
		return visibility;
	}

	public double getUvi(){
		return uvi;
	}

	public double getPressure(){
		return pressure;
	}

	public int getClouds(){
		return clouds;
	}

	public double getFeelsLike(){
		return feelsLike;
	}

	public double getDt(){
		return dt;
	}

	public double getWindDeg(){
		return windDeg;
	}

	public double getDewPoint(){
		return dewPoint;
	}

	public int getSunset(){
		return sunset;
	}

	public List<WeatherItem> getWeather(){
		return weather;
	}

	public double getHumidity(){
		return humidity;
	}

	public double getWindSpeed(){
		return windSpeed;
	}
}