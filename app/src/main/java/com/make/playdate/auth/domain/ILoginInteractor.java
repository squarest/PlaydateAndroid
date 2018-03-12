package com.make.playdate.auth.domain;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 27.02.2018.
 */

public interface ILoginInteractor {
    Single<Boolean> checkUser();

    Completable login();
}
