package com.example.weatherapp.remote.model.one_call_current_weather;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DailyItem{

	@SerializedName("moonset")
	private int moonset;

	@SerializedName("sunrise")
	private int sunrise;

	@SerializedName("temp")
	private Temp temp;

	@SerializedName("moon_phase")
	private double moonPhase;

	@SerializedName("uvi")
	private double uvi;

	@SerializedName("moonrise")
	private int moonrise;

	@SerializedName("pressure")
	private int pressure;

	@SerializedName("clouds")
	private int clouds;

	@SerializedName("feels_like")
	private FeelsLike feelsLike;

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

	@SerializedName("sunset")
	private int sunset;

	@SerializedName("weather")
	private List<WeatherItem> weather;

	@SerializedName("humidity")
	private int humidity;

	@SerializedName("wind_speed")
	private double windSpeed;

	@SerializedName("rain")
	private double rain;

	public int getMoonset(){
		return moonset;
	}

	public int getSunrise(){
		return sunrise;
	}

	public Temp getTemp(){
		return temp;
	}

	public double getMoonPhase(){
		return moonPhase;
	}

	public double getUvi(){
		return uvi;
	}

	public int getMoonrise(){
		return moonrise;
	}

	public int getPressure(){
		return pressure;
	}

	public int getClouds(){
		return clouds;
	}

	public FeelsLike getFeelsLike(){
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

	public int getSunset(){
		return sunset;
	}

	public List<WeatherItem> getWeather(){
		return weather;
	}

	public int getHumidity(){
		return humidity;
	}

	public double getWindSpeed(){
		return windSpeed;
	}

	public double getRain(){
		return rain;
	}
}