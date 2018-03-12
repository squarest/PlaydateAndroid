package com.make.playdate.preference.domain;

import com.make.playdate.entities.UserModel;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 10.03.2018.
 */

public interface IPreferenceInteractor {
    Single<UserModel> loadUser();
    Completable updateUser(UserModel userModel);
}
