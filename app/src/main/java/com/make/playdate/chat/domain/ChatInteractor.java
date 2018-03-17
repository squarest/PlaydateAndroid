package com.make.playdate.chat.domain;

import com.make.playdate.chat.data.IChatRepo;
import com.make.playdate.entities.ChatData;
import com.make.playdate.entities.PlaydateModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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
        return chatRepo.getMessages(conversationId)
                .map(chatData -> {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.US);
                    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                    Date value = null;
                    try {
                        value = formatter.parse(chatData.date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
                    dateFormatter.setTimeZone(TimeZone.getDefault());
                    chatData.date = dateFormatter.format(value);
                    return chatData;

                });
    }

    @Override
    public Completable sendMessage(ChatData chatData) {
        long time = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm",Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        chatData.date = sdf.format(time);
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
