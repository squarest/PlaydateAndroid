package com.prince.logan.playdate.chat.data;

import com.prince.logan.playdate.entities.ChatData;
import com.prince.logan.playdate.entities.PlaydateModel;
import com.prince.logan.playdate.entities.ResponseModel;
import com.prince.logan.playdate.entities.UserModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 03.03.2018.
 */

public interface IChatRepo {
    String getUid();

    Single<List<List<PlaydateModel>>> getAllChats();

    Observable<ChatData> getMessages(String conversationId);

    void pushMessage(ChatData chatData);

    Single<ResponseModel> sendNotification(String conversationId, ChatData chatData);

    Single<UserModel> getUser();

    void removeChat();
}
