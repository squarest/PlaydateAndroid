package com.make.playdate.chat.domain;

import com.make.playdate.chat.data.IChatRepo;
import com.make.playdate.entities.ChatData;
import com.make.playdate.entities.PlaydateModel;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dmitrijfomenko on 03.03.2018.
 */

public class ChatInteractor implements IChatInteractor {
    private IChatRepo chatRepo;
    private String conversationId;
    private String conversationName;

    public ChatInteractor(IChatRepo chatRepo) {
        this.chatRepo = chatRepo;
    }

    @Override
    public Single<List<List<PlaydateModel>>> loadChats() {
        return chatRepo.getAllChats()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ChatData> loadMessages() {
        return chatRepo.getMessages(conversationId);
    }

    @Override
    public Completable sendMessage(ChatData chatData) {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("UTC"));
        String date = zonedDateTime.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm"));
        chatData.date = date;
        chatRepo.pushMessage(chatData);
        return chatRepo.sendNotification(conversationId, chatData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toCompletable();

    }

    @Override
    public Single<String> loadConversationName() {
        return Single.just(conversationName);
    }

    @Override
    public Completable destroyChat() {
        chatRepo.removeChat();
        return Completable.complete();
    }

    @Override
    public Completable setChatData(String userId, String userName) {
        conversationId = userId;
        conversationName = userName;
        return Completable.complete();
    }

    @Override
    public Single<String> loadUserId() {
        return Single.just(chatRepo.getUid());
    }

    @Override
    public Completable flagUser() {
        return chatRepo.flagUser(conversationId)
                .toCompletable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
