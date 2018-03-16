package com.make.playdate.main.domain;

import com.make.playdate.entities.UserModel;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 01.03.2018.
 */

public interface IMainInteractor {
    void startLocationTracking();

    Single<UserModel> loadUser();



    Completable deleteUser();

    Completable logout();
}
