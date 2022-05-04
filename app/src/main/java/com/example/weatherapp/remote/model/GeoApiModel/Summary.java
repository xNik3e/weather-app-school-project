package com.example.weatherapp.remote.model.GeoApiModel;

import com.google.gson.annotations.SerializedName;

public class Summary{

	@SerializedName("fuzzyLevel")
	private int fuzzyLevel;

	@SerializedName("totalResults")
	private int totalResults;

	@SerializedName("offset")
	private int offset;

	@SerializedName("query")
	private String query;

	@SerializedName("queryTime")
	private int queryTime;

	@SerializedName("numResults")
	private int numResults;

	@SerializedName("queryType")
	private String queryType;

	public int getFuzzyLevel(){
		return fuzzyLevel;
	}

	public int getTotalResults(){
		return totalResults;
	}

	public int getOffset(){
		return offset;
	}

	public String getQuery(){
		return query;
	}

	public int getQueryTime(){
		return queryTime;
	}

	public int getNumResults(){
		return numResults;
	}

	public String getQueryType(){
		return queryType;
	}
}