package com.make.playdate.main.domain;

import com.make.playdate.entities.QuestionModel;
import com.make.playdate.entities.UserModel;
import com.make.playdate.main.data.IMainRepo;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dmitrijfomenko on 01.03.2018.
 */

public class MainInteractor implements IMainInteractor {
    private IMainRepo mainRepo;

    public MainInteractor(IMainRepo mainRepo) {
        this.mainRepo = mainRepo;
    }

    @Override
    public void startLocationTracking() {
        mainRepo.getUserLocation()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMapSingle(location -> mainRepo.updateLocation(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude())))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(requestModel -> {
                }, Throwable::printStackTrace);


    }

    @Override
    public Single<UserModel> loadUser() {
        return mainRepo.getUser();
    }

    @Override
    public Single<QuestionModel> loadQuestion() {
        return mainRepo.getQuestion();
    }

    @Override
    public Completable deleteUser() {
        return mainRepo.removeUserFromServer()
                .subscribeOn(Schedulers.io())
                .doOnSuccess(requestModel -> mainRepo.removeUserFromDevice())
                .observeOn(AndroidSchedulers.mainThread())
                .toCompletable();
    }

    @Override
    public Completable logout() {
        mainRepo.removeUserFromDevice();
        return Completable.complete();
    }

}
