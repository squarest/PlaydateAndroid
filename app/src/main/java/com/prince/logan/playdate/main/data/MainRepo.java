package com.prince.logan.playdate.main.data;

import android.location.Location;

import com.google.firebase.auth.FirebaseAuth;
import com.prince.logan.playdate.entities.RequestModel;
import com.prince.logan.playdate.network.ApiClient;
import com.prince.logan.playdate.utils.LocationProvider;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 01.03.2018.
 */

public class MainRepo implements IMainRepo {
    private ApiClient apiClient;
    private LocationProvider locationProvider;

    public MainRepo(ApiClient apiClient, LocationProvider locationProvider) {
        this.apiClient = apiClient;
        this.locationProvider = locationProvider;
    }


    @Override
    public Observable<Location> getUserLocation() {
        return locationProvider.getLocationObservable();
    }

    @Override
    public Single<RequestModel> updateLocation(String loti, String longi) {
        return apiClient.getApi().update_location(FirebaseAuth.getInstance().getUid(), loti, longi);
    }
}
