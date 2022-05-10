package com.example.weatherapp.remote.model.one_call_weather;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HourlyItem implements Serializable {

	@SerializedName("temp")
	private double temp;

	@SerializedName("visibility")
	private int visibility;

	@SerializedName("uvi")
	private double uvi;

	@SerializedName("pressure")
	private int pressure;

	@SerializedName("clouds")
	private int clouds;

	@SerializedName("feels_like")
	private double feelsLike;

	@SerializedName("wind_gust")
	private double windGust;

	@SerializedName("dt")
	private int dt;

	@SerializedName("pop")
	private int pop;

	@SerializedName("wind_deg")
	private int windDeg;

	@SerializedName("dew_point")
	private double dewPoint;

	@SerializedName("weather")
	private List<WeatherItem> weather;

	@SerializedName("humidity")
	private int humidity;

	@SerializedName("wind_speed")
	private double windSpeed;

	public void setTemp(double temp){
		this.temp = temp;
	}

	public double getTemp(){
		return temp;
	}

	public void setVisibility(int visibility){
		this.visibility = visibility;
	}

	public int getVisibility(){
		return visibility;
	}

	public void setUvi(double uvi){
		this.uvi = uvi;
	}

	public double getUvi(){
		return uvi;
	}

	public void setPressure(int pressure){
		this.pressure = pressure;
	}

	public int getPressure(){
		return pressure;
	}

	public void setClouds(int clouds){
		this.clouds = clouds;
	}

	public int getClouds(){
		return clouds;
	}

	public void setFeelsLike(double feelsLike){
		this.feelsLike = feelsLike;
	}

	public double getFeelsLike(){
		return feelsLike;
	}

	public void setWindGust(double windGust){
		this.windGust = windGust;
	}

	public double getWindGust(){
		return windGust;
	}

	public void setDt(int dt){
		this.dt = dt;
	}

	public int getDt(){
		return dt;
	}

	public void setPop(int pop){
		this.pop = pop;
	}

	public int getPop(){
		return pop;
	}

	public void setWindDeg(int windDeg){
		this.windDeg = windDeg;
	}

	public int getWindDeg(){
		return windDeg;
	}

	public void setDewPoint(double dewPoint){
		this.dewPoint = dewPoint;
	}

	public double getDewPoint(){
		return dewPoint;
	}

	public void setWeather(List<WeatherItem> weather){
		this.weather = weather;
	}

	public List<WeatherItem> getWeather(){
		return weather;
	}

	public void setHumidity(int humidity){
		this.humidity = humidity;
	}

	public int getHumidity(){
		return humidity;
	}

	public void setWindSpeed(double windSpeed){
		this.windSpeed = windSpeed;
	}

	public double getWindSpeed(){
		return windSpeed;
	}
}