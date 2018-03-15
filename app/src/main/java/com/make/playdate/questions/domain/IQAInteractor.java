package com.make.playdate.questions.domain;

import com.make.playdate.entities.QAModel;
import com.make.playdate.entities.QuestionModel;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 14.03.2018.
 */

public interface IQAInteractor {
    Single<ArrayList<QuestionModel>> loadQuestions();

    Completable saveAnswers(String answers);

    Single<ArrayList<QAModel>> getQAs();
}
