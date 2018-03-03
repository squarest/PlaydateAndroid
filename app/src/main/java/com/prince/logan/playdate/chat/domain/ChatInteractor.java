package com.prince.logan.playdate.chat.domain;

import com.prince.logan.playdate.chat.data.IChatRepo;

/**
 * Created by dmitrijfomenko on 03.03.2018.
 */

public class ChatInteractor implements IChatInteractor {
    private IChatRepo chatRepo;

    public ChatInteractor(IChatRepo chatRepo) {
        this.chatRepo = chatRepo;
    }
}
