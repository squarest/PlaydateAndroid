package com.prince.logan.playdate.chat.data;

import com.prince.logan.playdate.entities.RequestModel;

import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 03.03.2018.
 */

public interface IChatRepo {
    Single<RequestModel> getAllChats();
}
