package com.make.playdate.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import io.reactivex.Observable;

/**
 * Created by dmitrijfomenko on 05.02.2018.
 */

public class LocationProvider {
    private Context context;
    private long minTime = 1000 * 60 * 10; //10 minutes
    private long minDistance = 100; //100 meters
    private Location curLocation;


    public LocationProvider(Context context) {
        this.context = context;
    }


    public Observable<Location> getLocationObservable() {
        return Observable.create(e -> {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    & ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                e.onError(new Exception());
            }

            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    curLocation = location;
                    e.onNext(location);
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
            };

            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    curLocation = location;
                    e.onNext(location);
                } else
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, locationListener);
            } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    curLocation = location;
                    e.onNext(location);
                } else
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, locationListener);
            }

        });
    }

    public double getDistanceTo(Location location) {
        float distanceInMeters = curLocation.distanceTo(location);
        return distanceInMeters * 0.000621371192;

    }

}
