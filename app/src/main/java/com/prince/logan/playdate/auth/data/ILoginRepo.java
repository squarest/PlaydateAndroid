package com.prince.logan.playdate.auth.data;

import com.prince.logan.playdate.entities.RequestModel;
import com.prince.logan.playdate.entities.UserModel;

import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 27.02.2018.
 */

public interface ILoginRepo {
    String getUserId();

    Single<UserModel> getUserFromFacebook();

    Single<RequestModel> loginToServer();

    Single<RequestModel> signUpToServer(UserModel userModel);
}
