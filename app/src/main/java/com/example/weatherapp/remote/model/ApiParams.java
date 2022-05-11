package com.example.weatherapp.remote.model;

import java.util.HashMap;
import java.util.Map;

public class ApiParams {
    private Map<String, String> geoParams;
    private Map<String, String> locParams;

    public ApiParams() {
        geoParams = new HashMap<>();
        locParams = new HashMap<>();

        this.geoParams.put("key", "E9P0WbV19ckPovEgDvb85AEkOVu6AgW8");
        this.geoParams.put("typeahead", "true");
        this.geoParams.put("language", "pl-PL");
        this.geoParams.put("idxSet", "Geo");

        this.locParams.put("limit", "5");
        this.locParams.put("appid", "9da165e7a2d95c865045f4cc7a492a2c");
        this.locParams.put("lat", "0");
        this.locParams.put("lon", "0");

    }

    public Map<String, String> getGeoParams() {
        return geoParams;
    }

    public Map<String, String> getLocParams(double lat, double lon) {

        this.locParams.replace("lat", String.valueOf(lat));
        this.locParams.replace("lon", String.valueOf(lon));
        return locParams;
    }
}
