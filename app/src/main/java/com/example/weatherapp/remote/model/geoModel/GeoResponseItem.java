package com.example.weatherapp.remote.model.geoModel;

import com.google.gson.annotations.SerializedName;

public class GeoResponseItem{

	@SerializedName("local_names")
	private LocalNames localNames;

	@SerializedName("country")
	private String country;

	@SerializedName("name")
	private String name;

	@SerializedName("lon")
	private double lon;

	@SerializedName("state")
	private String state;

	@SerializedName("lat")
	private double lat;

	public void setLocalNames(LocalNames localNames){
		this.localNames = localNames;
	}

	public LocalNames getLocalNames(){
		return localNames;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setLon(double lon){
		this.lon = lon;
	}

	public double getLon(){
		return lon;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setLat(double lat){
		this.lat = lat;
	}

	public double getLat(){
		return lat;
	}
}