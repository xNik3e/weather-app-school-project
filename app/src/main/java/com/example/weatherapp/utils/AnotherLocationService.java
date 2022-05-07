package com.example.weatherapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.example.weatherapp.BuildConfig;
import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;

public class AnotherLocationService{

    private Context context;
    private LocationManager mLocationManagerGPS;
    private LocationManager mLocationManagerNetwork;
    private LocationListener mLocationListenerGPS;
    private LocationListener mLocationListenerNetwork;
    private Location location;
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;

    AnotherLocationService.LocationServicesListener locationServicesListener;

    public AnotherLocationService(Context context) {
        this.context = context;
        locationServicesListener = (LocationServicesListener) context;
    }

    public interface LocationServicesListener {
        void onLocationChangeResult(Location location);
    }

    public Location getLocation(Context context){
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.isGPSEnabled =  manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        this.isNetworkEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled)
            Toast.makeText(context, "Enable either Network or GPS", Toast.LENGTH_SHORT).show();
        else if (isGPSEnabled){
            return getPositionGPS();
        }else{
            return getPositionNetwork();
        }
        return null;
    }

    public Location getPositionGPS() {
        mLocationManagerGPS = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        mLocationListenerGPS = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                locationServicesListener.onLocationChangeResult(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                LocationListener.super.onStatusChanged(provider, status, extras);
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                LocationListener.super.onProviderEnabled(provider);
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                LocationListener.super.onProviderDisabled(provider);
                showAlert(R.string.GPS_disabled);
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestLocationPermission();
            } else {
                mLocationManagerGPS.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 0, mLocationListenerGPS);
            }
        }
        this.location = mLocationManagerGPS.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return this.location;
    }

    public Location getPositionNetwork() {
        mLocationManagerNetwork = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        mLocationListenerNetwork = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                locationServicesListener.onLocationChangeResult(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                LocationListener.super.onStatusChanged(provider, status, extras);
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                LocationListener.super.onProviderEnabled(provider);
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                LocationListener.super.onProviderDisabled(provider);
                showAlert(R.string.Network_disabled);
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestLocationPermission();
            } else {
                mLocationManagerNetwork.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5, 0, mLocationListenerNetwork);
            }
        }
        this.location = mLocationManagerNetwork.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        return this.location;
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.GPS_permissions).setCancelable(false).setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }).show();
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.GPS_permissions).setCancelable(false).setPositiveButton(R.string.btn_watch_permissions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    context.startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + context.getPackageName())));
                }
            }).setNegativeButton(R.string.btn_close, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).show();
        }
    }

    private void showAlert(int messageId) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(messageId).setCancelable(false).setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton(R.string.btn_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void stopUsingGPS(){
        if(this.mLocationManagerGPS != null)
            mLocationManagerGPS.removeUpdates(this.mLocationListenerGPS);
        if(this.mLocationManagerNetwork != null)
            mLocationManagerNetwork.removeUpdates(this.mLocationListenerNetwork);
    }
}

