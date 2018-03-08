package com.prince.logan.playdate.playdate.data;

import com.prince.logan.playdate.entities.PlaydateModel;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 07.03.2018.
 */

public interface IPlaydateRepo {
    Single<PlaydateModel> postMakePlaydate();

    Single<List<PlaydateModel>> getMatchedUsers();
}
