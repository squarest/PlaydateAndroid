package com.make.playdate.auth.domain;

import com.make.playdate.auth.data.ILoginRepo;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dmitrijfomenko on 27.02.2018.
 */

public class LoginInteractor implements ILoginInteractor {
    private ILoginRepo loginRepo;

    public LoginInteractor(ILoginRepo loginRepo) {
        this.loginRepo = loginRepo;
    }

    @Override
    public Single<Boolean> checkUser() {

        return Single.just(loginRepo.isCurrentUserExist());
    }

    @Override
    public Completable login() {
        return loginRepo.getUserFromFacebook()
                .observeOn(Schedulers.io())
                .flatMap(loginRepo::signUpToServer)
                .flatMap(requestModel -> loginRepo.setTokenToServer())
                .toCompletable();
    }
}
