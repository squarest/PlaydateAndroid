package com.prince.logan.playdate.chat.domain;

import com.prince.logan.playdate.entities.ChatData;
import com.prince.logan.playdate.entities.PlaydateModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 03.03.2018.
 */

public interface IChatInteractor {
    Single<List<List<PlaydateModel>>> loadChats();

    Observable<ChatData> loadMessages();

    Completable sendMessage(ChatData chatData);

    Single<String> loadConversationName();

    Completable destroyChat();

    Completable setChatData(String userId, String userName);

    Single<String> loadUserId();
}
