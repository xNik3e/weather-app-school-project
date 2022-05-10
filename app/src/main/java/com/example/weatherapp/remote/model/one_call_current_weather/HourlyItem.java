package com.example.weatherapp.remote.model.one_call_current_weather;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HourlyItem{

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

	@SerializedName("wind_gust")
	private double windGust;

	@SerializedName("dt")
	private double dt;

	@SerializedName("pop")
	private double pop;

	@SerializedName("wind_deg")
	private double windDeg;

	@SerializedName("dew_point")
	private double dewPoint;

	@SerializedName("weather")
	private List<WeatherItem> weather;

	@SerializedName("humidity")
	private double humidity;

	@SerializedName("wind_speed")
	private double windSpeed;

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

	public double getWindGust(){
		return windGust;
	}

	public double getDt(){
		return dt;
	}

	public double getPop(){
		return pop;
	}

	public double getWindDeg(){
		return windDeg;
	}

	public double getDewPoint(){
		return dewPoint;
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