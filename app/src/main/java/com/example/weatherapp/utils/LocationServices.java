package com.example.weatherapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.List;

public class LocationServices implements LocationListener {

    private Context context;
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;
    private boolean canGetLocation = false;
    private int flag = 0;
    private Location location;
    private double lat;
    private double lon;
    private LocationManager locationManager;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 10;
    private static final long MIN_DISTANCE_FOR_UPDATES = 10000; // 10km



    public interface LocationServicesListener{
        void onLocationChangeResult(Location location);
    }

    LocationServicesListener locationServicesListener;

    public LocationServices(Context context) {
        this.context = context;
        locationServicesListener = (LocationServicesListener) context;
    }

    public Location getLocation(Context context) {
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 123);

            }
            this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            this.isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            this.isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled)
                Toast.makeText(context, "Enable either Network or GPS", Toast.LENGTH_SHORT).show();
            else {
                this.canGetLocation = true;
                if (isGPSEnabled) {

                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            this.lon = location.getLongitude();
                            this.lat = location.getLatitude();
                        }
                    }
                }
                if (isNetworkEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            this.location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                this.lat = location.getLatitude();
                                this.lon = location.getLongitude();
                            }
                        }
                    }
                }

            }

        } catch (Exception e) {
            ///
        }
        return location;
    }

    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(LocationServices.this);
        }
    }

    public double getLongitude() {
        if (location != null) {
            this.lon = location.getLongitude();
        }
        return lon;
    }

    public double getLatitude() {
        if (location != null) {
            this.lat = location.getLongitude();
        }
        return lon;
    }

    public boolean isCanGetLocation() {
        return canGetLocation;
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        locationServicesListener.onLocationChangeResult(location);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }



}