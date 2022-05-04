package com.example.weatherapp.remote.model.GeoApiModel;

import com.google.gson.annotations.SerializedName;

public class Address{

	@SerializedName("country")
	private String country;

	@SerializedName("countryCodeISO3")
	private String countryCodeISO3;

	@SerializedName("countrySecondarySubdivision")
	private String countrySecondarySubdivision;

	@SerializedName("countryCode")
	private String countryCode;

	@SerializedName("municipality")
	private String municipality;

	@SerializedName("municipalitySubdivision")
	private String municipalitySubdivision;

	@SerializedName("freeformAddress")
	private String freeformAddress;

	@SerializedName("countrySubdivision")
	private String countrySubdivision;

	@SerializedName("countrySubdivisionCode")
	private String countrySubdivisionCode;

	public String getCountry(){
		return country;
	}

	public String getCountryCodeISO3(){
		return countryCodeISO3;
	}

	public String getCountrySecondarySubdivision(){
		return countrySecondarySubdivision;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public String getMunicipality(){
		return municipality;
	}

	public String getMunicipalitySubdivision(){
		return municipalitySubdivision;
	}

	public String getFreeformAddress(){
		return freeformAddress;
	}

	public String getCountrySubdivision(){
		return countrySubdivision;
	}

	public String getCountrySubdivisionCode(){
		return countrySubdivisionCode;
	}
}