package com.make.playdate.questions.data;

import com.google.firebase.auth.FirebaseAuth;
import com.make.playdate.entities.QuestionModel;
import com.make.playdate.entities.ResponseModel;
import com.make.playdate.network.ApiClient;

import java.util.ArrayList;

import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 14.03.2018.
 */

public class QARepo implements IQARepo {
    private ApiClient client;
    private FirebaseAuth firebaseAuth;

    public QARepo(ApiClient client, FirebaseAuth firebaseAuth) {
        this.client = client;
        this.firebaseAuth = firebaseAuth;
    }

    private String getUid() {
        return firebaseAuth.getUid();
    }

    @Override
    public Single<ArrayList<QuestionModel>> getQuestions() {
        return client.getApi().get_questions()
                .map(ResponseModel::getCateQuestion);
    }


    @Override
    public Single<ResponseModel> postAnswers(String answers) {
        return client.getApi().saving_answers(8, 25, getUid(), answers);
    }

    @Override
    public Single<String[]> getAnswers() {
        return client.getApi().getAnswers(getUid())
                .map(ResponseModel::getAnswer_list)
                .map(s -> s.split(","));
    }
}
