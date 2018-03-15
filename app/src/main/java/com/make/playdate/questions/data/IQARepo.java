package com.make.playdate.questions.data;

import com.make.playdate.entities.QuestionModel;
import com.make.playdate.entities.ResponseModel;

import java.util.ArrayList;

import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 14.03.2018.
 */

public interface IQARepo {
    Single<ArrayList<QuestionModel>> getQuestions();

    Single<ResponseModel> postAnswers(String answers);

    Single<String[]> getAnswers();

}
