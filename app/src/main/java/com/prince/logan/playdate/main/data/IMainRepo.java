package com.prince.logan.playdate.main.data;

import android.location.Location;

import com.prince.logan.playdate.entities.RequestModel;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 01.03.2018.
 */

public interface IMainRepo {
    Observable<Location> getUserLocation();
    Single<RequestModel> updateLocation(String loti, String longi);
}
