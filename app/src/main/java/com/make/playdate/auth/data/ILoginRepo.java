package com.make.playdate.auth.data;

import com.make.playdate.entities.ResponseModel;
import com.make.playdate.entities.UserModel;

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
