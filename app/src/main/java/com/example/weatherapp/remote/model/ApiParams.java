package com.example.weatherapp.remote.model;

import java.util.HashMap;
import java.util.Map;

public class ApiParams {
    private Map<String, String> params;

    public ApiParams() {
        params = new HashMap<>();
        this.params.put("key", "E9P0WbV19ckPovEgDvb85AEkOVu6AgW8");
        this.params.put("typeahead", "true");
        this.params.put("language", "pl-PL");
        this.params.put("idxSet", "Geo");
    }

    public Map<String, String> getParams() {
        return params;
    }
}
