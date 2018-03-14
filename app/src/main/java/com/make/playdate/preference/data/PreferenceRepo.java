package com.make.playdate.preference.data;

import com.google.firebase.auth.FirebaseAuth;
import com.make.playdate.entities.ResponseModel;
import com.make.playdate.entities.UserModel;
import com.make.playdate.network.ApiClient;

import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 10.03.2018.
 */

public class PreferenceRepo implements IPreferenceRepo {
    private ApiClient apiClient;
    private FirebaseAuth firebaseAuth;

    public PreferenceRepo(ApiClient apiClient, FirebaseAuth firebaseAuth) {
        this.apiClient = apiClient;
        this.firebaseAuth = firebaseAuth;
    }

    private String getUid() {
        return firebaseAuth.getUid();
    }

    @Override
    public Single<UserModel> getUser() {
        return apiClient.getApi().login(getUid())
                .map(ResponseModel::getUser);
    }

    @Override
    public Single<ResponseModel> updateUser(UserModel userModel) {
        return apiClient.getApi().edit_user(userModel);
    }

    @Override
    public Single<ResponseModel> updateNotification(int isPushNotification) {
        return apiClient.getApi().update_notification(isPushNotification, getUid());
    }

    @Override
    public Single<ResponseModel> updatePreferences(UserModel userModel) {
        return apiClient.getApi().update_setting(userModel);
    }
}
