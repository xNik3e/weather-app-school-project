package com.example.weatherapp.remote.model.GeoApiModel;

import com.google.gson.annotations.SerializedName;

public class ResultsItem{

	@SerializedName("score")
	private double score;

	@SerializedName("boundingBox")
	private BoundingBox boundingBox;

	@SerializedName("address")
	private Address address;

	@SerializedName("viewport")
	private Viewport viewport;

	@SerializedName("entityType")
	private String entityType;

	@SerializedName("id")
	private String id;

	@SerializedName("position")
	private Position position;

	@SerializedName("type")
	private String type;

	@SerializedName("dataSources")
	private DataSources dataSources;

	public double getScore(){
		return score;
	}

	public BoundingBox getBoundingBox(){
		return boundingBox;
	}

	public Address getAddress(){
		return address;
	}

	public Viewport getViewport(){
		return viewport;
	}

	public String getEntityType(){
		return entityType;
	}

	public String getId(){
		return id;
	}

	public Position getPosition(){
		return position;
	}

	public String getType(){
		return type;
	}

	public DataSources getDataSources(){
		return dataSources;
	}
}