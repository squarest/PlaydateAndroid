package com.make.playdate.chat.data;

import com.make.playdate.entities.ChatData;
import com.make.playdate.entities.PlaydateModel;
import com.make.playdate.entities.ResponseModel;
import com.make.playdate.entities.UserModel;

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

    Single<ResponseModel>flagUser(String flaggeeId);
}
