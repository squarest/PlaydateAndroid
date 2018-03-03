package com.prince.logan.playdate.chat.presentation.chatlist;

import com.prince.logan.playdate.base.BaseView;
import com.prince.logan.playdate.entities.UserModel;

import java.util.ArrayList;

/**
 * Created by dmitrijfomenko on 03.03.2018.
 */

public interface ChatListView extends BaseView {
    void setChatList(ArrayList<UserModel> userModels);
}
