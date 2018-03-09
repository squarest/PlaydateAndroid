package com.prince.logan.playdate.chat.presentation.chat;

import com.arellomobile.mvp.InjectViewState;
import com.prince.logan.playdate.base.App;
import com.prince.logan.playdate.base.BasePresenter;
import com.prince.logan.playdate.chat.domain.IChatInteractor;
import com.prince.logan.playdate.entities.ChatData;
import com.prince.logan.playdate.entities.PlaydateModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by dmitrijfomenko on 03.03.2018.
 */
@InjectViewState
public class ChatPresenter extends BasePresenter<ChatView> {
    @Inject
    public IChatInteractor interactor;

    public ChatPresenter() {
        App.getChatComponent().inject(this);
    }

    public void viewCreated(PlaydateModel playdateModel) {
        Disposable disposable = interactor.setChatData(playdateModel.firebaseId, playdateModel.userFullName)
                .andThen(interactor.loadUserId())
                .subscribe(s -> {
                    getViewState().initList(s);
                    loadChat();
                }, Throwable::printStackTrace);
        putDisposable(disposable);
    }

    private void loadChat() {
        Disposable disposable = interactor.loadConversationName()
                .subscribe(s -> getViewState().setConversationName(s), Throwable::printStackTrace);
        putDisposable(disposable);
        Disposable disposable1 = interactor.loadMessages()
                .subscribe(chatData -> getViewState().setMessage(chatData), Throwable::printStackTrace);
        putDisposable(disposable1);
    }

    public void sendButtonClicked(ChatData chatData) {
        Disposable disposable = interactor.sendMessage(chatData).subscribe();
        putDisposable(disposable);
    }

    @Override
    public void onDestroy() {
        putDisposable(interactor.destroyChat().subscribe());
        super.onDestroy();
    }
}
