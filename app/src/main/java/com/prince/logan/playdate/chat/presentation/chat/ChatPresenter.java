package com.prince.logan.playdate.chat.presentation.chat;

import com.prince.logan.playdate.base.App;
import com.prince.logan.playdate.base.BasePresenter;
import com.prince.logan.playdate.chat.domain.IChatInteractor;

import javax.inject.Inject;

/**
 * Created by dmitrijfomenko on 03.03.2018.
 */

public class ChatPresenter extends BasePresenter<ChatView> {
    @Inject
    public IChatInteractor interactor;

    public ChatPresenter() {
        App.getChatComponent().inject(this);
    }
}
