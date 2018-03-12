package com.make.playdate.chat.presentation.chat;

import com.make.playdate.base.BaseView;
import com.make.playdate.entities.ChatData;

/**
 * Created by dmitrijfomenko on 03.03.2018.
 */

interface ChatView extends BaseView {
    void initList(String firebaseId);

    void setMessage(ChatData chatData);

    void setConversationName(String name);

    void hideMessageInput();
}
