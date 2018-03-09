package com.prince.logan.playdate.playdate.domain;

import com.prince.logan.playdate.entities.PlaydateModel;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 07.03.2018.
 */

public interface IPlaydateInteractor {
    Single<PlaydateModel> loadMatchUser();

    Single<List<PlaydateModel>> loadAllMatchedUsers();

    Single<PlaydateModel> setSelectedPlaydate(String id);

    Single<PlaydateModel> getSelectedPlaydate();
}
