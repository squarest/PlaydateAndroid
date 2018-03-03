package com.prince.logan.playdate.chat.di;

import com.prince.logan.playdate.chat.presentation.chat.ChatPresenter;
import com.prince.logan.playdate.chat.presentation.chatlist.ChatListPresenter;

import dagger.Subcomponent;

/**
 * Created by dmitrijfomenko on 03.03.2018.
 */
@ChatScope
@Subcomponent(modules = ChatModule.class)
public interface ChatComponent {
    void inject(ChatPresenter chatPresenter);

    void inject(ChatListPresenter chatListPresenter);
}
