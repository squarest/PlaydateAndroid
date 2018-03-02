package com.prince.logan.playdate.auth.domain;

import com.prince.logan.playdate.auth.data.ILoginRepo;

import io.reactivex.Completable;
import io.reactivex.Single;

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

        if (loginRepo.getUserId() != null) {
            return loginRepo.loginToServer()
                    .flatMap(requestModel -> loginRepo.setTokenToServer())
                    .map(requestModel -> true);
        } else return Single.just(false);
    }

    @Override
    public Completable login() {
        return loginRepo.getUserFromFacebook()
                .flatMap(loginRepo::signUpToServer)
                .flatMap(requestModel -> loginRepo.setTokenToServer())
                .toCompletable();
    }
}
