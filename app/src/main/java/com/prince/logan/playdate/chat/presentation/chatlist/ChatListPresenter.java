package com.prince.logan.playdate.chat.presentation.chatlist;

import com.prince.logan.playdate.base.App;
import com.prince.logan.playdate.base.BasePresenter;
import com.prince.logan.playdate.chat.domain.IChatInteractor;

import javax.inject.Inject;

/**
 * Created by dmitrijfomenko on 03.03.2018.
 */

public class ChatListPresenter extends BasePresenter<ChatListView> {
    @Inject
    public IChatInteractor interactor;
    public ChatListPresenter() {
        App.getChatComponent().inject(this);
    }
    void viewCreated(){

    }
}
