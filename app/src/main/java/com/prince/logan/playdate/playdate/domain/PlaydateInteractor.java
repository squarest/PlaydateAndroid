package com.prince.logan.playdate.playdate.domain;

import com.prince.logan.playdate.entities.PlaydateModel;
import com.prince.logan.playdate.playdate.data.IPlaydateRepo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dmitrijfomenko on 07.03.2018.
 */

public class PlaydateInteractor implements IPlaydateInteractor {
    private IPlaydateRepo playdateRepo;

    private PlaydateModel selectedPlaydate;

    public Single<PlaydateModel> getSelectedPlaydate() {
        return Single.just(selectedPlaydate);
    }

    public Completable setSelectedPlaydate(PlaydateModel selectedPlaydate) {
        this.selectedPlaydate = selectedPlaydate;
        return Completable.complete();
    }

    public PlaydateInteractor(IPlaydateRepo playdateRepo) {
        this.playdateRepo = playdateRepo;
    }

    @Override
    public Single<PlaydateModel> loadMatchUser() {
        return playdateRepo.postMakePlaydate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<PlaydateModel>> loadAllMatchedUsers() {
        return playdateRepo.getMatchedUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
