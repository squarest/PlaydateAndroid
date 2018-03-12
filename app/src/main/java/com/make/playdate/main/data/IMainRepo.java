package com.make.playdate.main.data;

import android.location.Location;

import com.make.playdate.entities.QuestionModel;
import com.make.playdate.entities.ResponseModel;
import com.make.playdate.entities.UserModel;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 01.03.2018.
 */

public interface IMainRepo {
    Observable<Location> getUserLocation();

    Single<ResponseModel> updateLocation(String loti, String longi);

    Single<UserModel> getUser();

    Single<QuestionModel> getQuestion();

    void removeUserFromDevice();

    Single<ResponseModel> removeUserFromServer();
}
