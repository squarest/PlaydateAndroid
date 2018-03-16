package com.make.playdate.preference.domain;

import com.make.playdate.entities.QuestionModel;
import com.make.playdate.entities.UserModel;
import com.make.playdate.preference.data.IPreferenceRepo;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dmitrijfomenko on 10.03.2018.
 */

public class PreferenceInteractor implements IPreferenceInteractor {
    private IPreferenceRepo preferenceRepo;

    public PreferenceInteractor(IPreferenceRepo preferenceRepo) {
        this.preferenceRepo = preferenceRepo;
    }

    @Override
    public Single<UserModel> loadUser() {
        return preferenceRepo.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable updateUser(UserModel userModel) {
        return preferenceRepo.updateUser(userModel)
                .flatMap(responseModel -> preferenceRepo.updateNotification(userModel.getIs_pushnotification()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toCompletable();
    }

    @Override
    public Completable updatePreferences(UserModel userModel) {
        return preferenceRepo.updatePreferences(userModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toCompletable();
    }

    @Override
    public Single<QuestionModel> loadQuestion() {
        return preferenceRepo.getQuestion();
    }

    @Override
    public Single<Boolean> checkAnswers() {
        return preferenceRepo.checkAnswers()
                .map(responseModel -> responseModel.getResult() == 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
