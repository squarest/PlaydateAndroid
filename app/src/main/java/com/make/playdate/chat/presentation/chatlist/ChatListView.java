package com.make.playdate.chat.presentation.chatlist;

import com.make.playdate.base.BaseView;
import com.make.playdate.entities.PlaydateModel;

import java.util.List;

/**
 * Created by dmitrijfomenko on 03.03.2018.
 */

public interface ChatListView extends BaseView {
    void setChatList(List<List<PlaydateModel>> chats);

}
