package com.make.playdate.playdate.domain;

import android.location.Location;

import com.make.playdate.entities.PlaydateModel;
import com.make.playdate.entities.QAModel;
import com.make.playdate.entities.QuestionModel;
import com.make.playdate.exceptions.PlaydateLimitReachedException;
import com.make.playdate.exceptions.RequestException;
import com.make.playdate.global.Constant;
import com.make.playdate.playdate.data.IPlaydateRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dmitrijfomenko on 07.03.2018.
 */

public class PlaydateInteractor implements IPlaydateInteractor {
    private IPlaydateRepo playdateRepo;

    private PlaydateModel selectedPlaydate;


    public Single<PlaydateModel> setSelectedPlaydate(String id) {
        Single<PlaydateModel> single = playdateRepo.getMatchedUsers()
                .map(playdateModels ->
                {
                    for (PlaydateModel playdateModel : playdateModels) {
                        if (playdateModel.firebaseId.equals(id)) {
                            selectedPlaydate = playdateModel;
                            break;
                        }
                    }
                    return selectedPlaydate;
                });
        return Single.zip(single, playdateRepo.get_questions(), playdateRepo.getAnswers(id), this::combineQA)
                .map(playdateModel ->
                {
                    Location targetLocation = new Location("");
                    targetLocation.setLatitude(Double.valueOf(playdateModel.userLati));
                    targetLocation.setLongitude(Double.valueOf(playdateModel.userLongi));
                    playdateModel.distance = playdateRepo.getDistanceTo(targetLocation);
                    return playdateModel;
                })
                .map(playdateModel -> {
                    playdateModel.userEthnicity = Constant.arrayEthnicity[Integer.valueOf(playdateModel.userEthnicity)];
                    return playdateModel;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private PlaydateModel combineQA(PlaydateModel playdate, List<QuestionModel> questions, String[] answers) {
        List<QAModel> qaModels = new ArrayList<>();
        for (QuestionModel question : questions) {
            QAModel qaModel = new QAModel();
            qaModel.question = question.getQuestions();
            List<String> a = Arrays.asList(answers);
            if (a.contains(String.valueOf(question.getId()))) qaModel.answer = "Yes";
            else qaModel.answer = "No";
            qaModels.add(qaModel);
        }
        playdate.questions = qaModels;
        return playdate;
    }

    @Override
    public Single<PlaydateModel> getSelectedPlaydate() {

        return Single.just(selectedPlaydate);
    }

    public PlaydateInteractor(IPlaydateRepo playdateRepo) {
        this.playdateRepo = playdateRepo;
    }

    @Override
    public Single<PlaydateModel> loadMatchUser() {
        return playdateRepo.postMakePlaydate()
                .subscribeOn(Schedulers.io())
                .flatMap(responseModel ->
                {
                    switch (responseModel.getResult()) {
                        case 0:
                            return Single.error(new RequestException());

                        case 1:
                            return Single.just(responseModel.getPlaydate());

                        case 2:
                            return Single.error(new PlaydateLimitReachedException());

                        default:
                            return Single.never();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<PlaydateModel>> loadAllMatchedUsers() {
        return playdateRepo.getMatchedUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
