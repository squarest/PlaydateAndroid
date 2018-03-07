package com.prince.logan.playdate.chat.presentation.chat;

import com.prince.logan.playdate.base.BaseView;
import com.prince.logan.playdate.entities.ChatData;

/**
 * Created by dmitrijfomenko on 03.03.2018.
 */

interface ChatView extends BaseView {
    void initList(String firebaseId);

    void setMessage(ChatData chatData);

    void setConversationName(String name);
}
