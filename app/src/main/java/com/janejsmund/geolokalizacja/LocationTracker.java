package com.janejsmund.geolokalizacja;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class LocationTracker extends Service implements LocationListener {

    private Context context;
    boolean isGPSOn = false;
    boolean isNetworkEnabled = false;
    boolean isLocationPermissionEnabled = false;

    private static final long MIN_DISTANCE_TO_REQUEST_LOCATION_UPDATE = 1; //w [m]
    private static final long MIN_TIME_FOR_UPDATES = 1000; //w [ms]

    Location location;
    double latitude, longitude;

    LocationManager locationManager;

    public LocationTracker(Context context) {
        this.context = context;
        checkIfLocationProviderIsAvaiable();
    }

    @SuppressLint("MissingPermission")
    public Location checkIfLocationProviderIsAvaiable() {
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            isGPSOn = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            if (!isGPSOn && !isNetworkEnabled) {
                Toast.makeText(context, "No Location Provider is Available", Toast.LENGTH_SHORT).show();
            } else {
                isLocationPermissionEnabled = true;

                if (isNetworkEnabled) {

                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_FOR_UPDATES, MIN_DISTANCE_TO_REQUEST_LOCATION_UPDATE, this);

                    if (locationManager != null) {

                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null) {

                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

                if (isGPSOn) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_FOR_UPDATES, MIN_DISTANCE_TO_REQUEST_LOCATION_UPDATE, this);

                    if (locationManager != null) {

                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        if (location != null) {

                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            }
        }
        catch (Exception e){

        }

        return location;
    }

    public void stopUsingLocation() {
        if (locationManager != null) {
            locationManager.removeUpdates(LocationTracker.this);
        }
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean isLocationPermissionEnabled() {
        return this.isLocationPermissionEnabled;
    }

    public void askToEnableLocationPermission() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        dialog.setTitle("Ustawienia");
        dialog.setMessage("Pozwolenie na dostęp do lokalizacji jest nieaktywne. Czy chcesz przejść do ustawień, żeby je aktywować?");
        dialog.setPositiveButton("Ustawienia", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        dialog.setNegativeButton("Wstecz", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
    }


    //Niezmienione
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
