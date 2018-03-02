package com.prince.logan.playdate.main.domain;

import com.prince.logan.playdate.main.data.IMainRepo;

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
        mainRepo.getUserLocation().subscribeOn(Schedulers.io())
                .flatMapSingle(location -> mainRepo.updateLocation(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude())))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(requestModel -> {
                }, Throwable::printStackTrace);


    }
}
