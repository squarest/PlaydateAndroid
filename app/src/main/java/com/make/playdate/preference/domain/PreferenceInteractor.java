package com.make.playdate.preference.domain;

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toCompletable();
    }
}
