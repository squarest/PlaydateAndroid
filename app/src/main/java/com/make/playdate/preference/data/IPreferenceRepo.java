package com.make.playdate.preference.data;

import com.make.playdate.entities.QuestionModel;
import com.make.playdate.entities.ResponseModel;
import com.make.playdate.entities.UserModel;

import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 10.03.2018.
 */

public interface IPreferenceRepo {
    Single<UserModel> getUser();

    Single<ResponseModel> updateUser(UserModel userModel);

    Single<ResponseModel> updateNotification(int isPushNotification);

    Single<ResponseModel> updatePreferences(UserModel userModel);

    Single<ResponseModel> checkAnswers();

    Single<QuestionModel> getQuestion();
}
