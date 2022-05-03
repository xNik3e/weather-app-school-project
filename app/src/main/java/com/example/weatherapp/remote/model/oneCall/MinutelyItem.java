package com.example.weatherapp.remote.model.oneCall;

import com.google.gson.annotations.SerializedName;

public class MinutelyItem{

	@SerializedName("dt")
	private int dt;

	@SerializedName("precipitation")
	private int precipitation;

	public void setDt(int dt){
		this.dt = dt;
	}

	public int getDt(){
		return dt;
	}

	public void setPrecipitation(int precipitation){
		this.precipitation = precipitation;
	}

	public int getPrecipitation(){
		return precipitation;
	}
}