package com.prince.logan.playdate.chat.data;

import com.prince.logan.playdate.entities.RequestModel;
import com.prince.logan.playdate.network.ApiClient;

import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 03.03.2018.
 */

public class ChatRepo implements IChatRepo {
    private ApiClient apiClient;

    public ChatRepo(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public Single<RequestModel> getAllChats() {
        return null;
    }
}
