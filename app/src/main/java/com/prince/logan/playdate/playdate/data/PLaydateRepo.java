package com.prince.logan.playdate.playdate.data;

import com.google.firebase.auth.FirebaseAuth;
import com.prince.logan.playdate.entities.PlaydateModel;
import com.prince.logan.playdate.entities.ResponseModel;
import com.prince.logan.playdate.network.ApiClient;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 07.03.2018.
 */

public class PLaydateRepo implements IPlaydateRepo {
    private ApiClient apiClient;
    private FirebaseAuth firebaseAuth;

    public PLaydateRepo(ApiClient apiClient, FirebaseAuth firebaseAuth) {
        this.apiClient = apiClient;
        this.firebaseAuth = firebaseAuth;
    }

    private String getUid() {
        return firebaseAuth.getUid();
    }

    @Override
    public Single<PlaydateModel> postMakePlaydate() {
        return apiClient.getApi().postMakePLaydate(getUid())
                .map(ResponseModel::getPlaydate);
    }

    @Override
    public Single<List<PlaydateModel>> getMatchedUsers() {
        return apiClient.getApi().getMatchedUsers(getUid())
                .map(ResponseModel::getMatchedUsers);
    }
}
