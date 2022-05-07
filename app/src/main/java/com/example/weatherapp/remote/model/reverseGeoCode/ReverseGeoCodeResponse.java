package com.example.weatherapp.remote.model.reverseGeoCode;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ReverseGeoCodeResponse{

	@SerializedName("ReverseGeoCodeResponse")
	private List<ReverseGeoCodeResponseItem> reverseGeoCodeResponse;

	public void setReverseGeoCodeResponse(List<ReverseGeoCodeResponseItem> reverseGeoCodeResponse){
		this.reverseGeoCodeResponse = reverseGeoCodeResponse;
	}

	public List<ReverseGeoCodeResponseItem> getReverseGeoCodeResponse(){
		return reverseGeoCodeResponse;
	}
}