package com.prince.logan.playdate.main.data;

import android.location.Location;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.prince.logan.playdate.entities.QuestionModel;
import com.prince.logan.playdate.entities.RequestModel;
import com.prince.logan.playdate.entities.UserModel;
import com.prince.logan.playdate.global.Constant;
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
    private FirebaseAuth firebaseAuth;
    private LoginManager loginManager;

    public MainRepo(ApiClient apiClient, LocationProvider locationProvider, FirebaseAuth firebaseAuth, LoginManager loginManager) {
        this.apiClient = apiClient;
        this.locationProvider = locationProvider;
        this.firebaseAuth = firebaseAuth;
        this.loginManager = loginManager;
    }

    private String getUserId() {
        return firebaseAuth.getUid();
    }

    @Override
    public Observable<Location> getUserLocation() {
        return locationProvider.getLocationObservable();
    }

    @Override
    public Single<RequestModel> updateLocation(String loti, String longi) {
        return apiClient.getApi().update_location(getUserId(), loti, longi);
    }

    @Override
    public Single<UserModel> getUser() {
        return apiClient.getApi().login(getUserId())
                .map(RequestModel::getUser);
    }

    @Override
    public Single<QuestionModel> getQuestion() {
        return apiClient.getApi().get_category()
                .map(requestModel -> requestModel.getCateQuestion().get(Constant.QUESTION_CATEGORY_ID));
    }

    @Override
    public void removeUserFromDevice() {
        loginManager.logOut();
        firebaseAuth.signOut();
    }

    @Override
    public Single<RequestModel> removeUserFromServer() {
        return apiClient.getApi().delete_user(getUserId());
    }

}
