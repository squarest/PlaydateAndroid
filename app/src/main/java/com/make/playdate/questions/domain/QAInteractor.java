package com.make.playdate.questions.domain;

import com.make.playdate.entities.QAModel;
import com.make.playdate.entities.QuestionModel;
import com.make.playdate.questions.data.IQARepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dmitrijfomenko on 14.03.2018.
 */

public class QAInteractor implements IQAInteractor {
    private IQARepo repo;

    public QAInteractor(IQARepo repo) {
        this.repo = repo;
    }

    @Override
    public Single<ArrayList<QuestionModel>> loadQuestions() {
        return repo.getQuestions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable saveAnswers(String answers) {
        return repo.postAnswers(answers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toCompletable();
    }

    @Override
    public Single<ArrayList<QAModel>> getQAs() {
        return Single.zip(repo.getQuestions(), repo.getAnswers(), this::combineQA)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private ArrayList<QAModel> combineQA(List<QuestionModel> questions, String[] answers) {
        ArrayList<QAModel> qaModels = new ArrayList<>();
        for (QuestionModel question : questions) {
            QAModel qaModel = new QAModel();
            qaModel.question = question.getQuestions();
            List<String> a = Arrays.asList(answers);
            if (a.contains(String.valueOf(question.getId()))) qaModel.answer = "Yes";
            else qaModel.answer = "No";
            qaModels.add(qaModel);
        }
        return qaModels;
    }
}
