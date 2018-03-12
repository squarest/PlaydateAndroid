package com.make.playdate.playdate.data;

import android.location.Location;

import com.google.firebase.auth.FirebaseAuth;
import com.make.playdate.entities.PlaydateModel;
import com.make.playdate.entities.QuestionModel;
import com.make.playdate.entities.ResponseModel;
import com.make.playdate.network.ApiClient;
import com.make.playdate.utils.LocationProvider;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 07.03.2018.
 */

public class PLaydateRepo implements IPlaydateRepo {
    private ApiClient apiClient;
    private FirebaseAuth firebaseAuth;
    private LocationProvider locationProvider;

    public PLaydateRepo(ApiClient apiClient, FirebaseAuth firebaseAuth, LocationProvider locationProvider) {
        this.apiClient = apiClient;
        this.firebaseAuth = firebaseAuth;
        this.locationProvider = locationProvider;
    }

    private String getUid() {
        return firebaseAuth.getUid();
    }

    @Override
    public Single<ResponseModel> postMakePlaydate() {
        return apiClient.getApi().postMakePLaydate(getUid());
    }

    @Override
    public Single<List<PlaydateModel>> getMatchedUsers() {
        return apiClient.getApi().getMatchedUsers(getUid())
                .map(ResponseModel::getMatchedUsers);
    }

    @Override
    public Single<List<QuestionModel>> get_questions() {
        return apiClient.getApi().get_questions()
                .map(ResponseModel::getCateQuestion);
    }

    @Override
    public Single<String[]> getAnswers(String uId) {
        return apiClient.getApi().getAnswers(uId)
                .map(ResponseModel::getAnswer_list)
                .map(s -> s.split(","));
    }

    @Override
    public double getDistanceTo(Location location) {
        return locationProvider.getDistanceTo(location);
    }
}
