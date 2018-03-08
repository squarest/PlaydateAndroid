package com.prince.logan.playdate.auth.data;

import com.prince.logan.playdate.entities.ResponseModel;
import com.prince.logan.playdate.entities.UserModel;

import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 27.02.2018.
 */

public interface ILoginRepo {
    boolean isCurrentUserExist();

    Single<ResponseModel>setTokenToServer();

    Single<UserModel> getUserFromFacebook();

    Single<ResponseModel> signUpToServer(UserModel userModel);
}
