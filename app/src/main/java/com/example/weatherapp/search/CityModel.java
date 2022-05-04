package com.example.weatherapp.search;

import java.io.Serializable;

public class CityModel implements Serializable {
    private String freeFormAddress;
    private String countrySubdivision;
    private String country;
    private double lat;
    private double lon;
    private boolean isAdded;

    public boolean isAdded() {
        return isAdded;
    }

    public CityModel() {
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public CityModel(String freeFormAddress, String countrySubdivision, String country, double lat, double lon) {
        this.freeFormAddress = freeFormAddress;
        this.countrySubdivision = countrySubdivision;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
        this.isAdded = false;
    }

    public String getFreeFormAddress() {
        return freeFormAddress;
    }

    public void setFreeFormAddress(String freeFormAddress) {
        this.freeFormAddress = freeFormAddress;
    }

    public String getCountrySubdivision() {
        return countrySubdivision;
    }

    public void setCountrySubdivision(String countrySubdivision) {
        this.countrySubdivision = countrySubdivision;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
