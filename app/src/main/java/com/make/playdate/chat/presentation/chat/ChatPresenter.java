package com.make.playdate.chat.presentation.chat;

import com.arellomobile.mvp.InjectViewState;
import com.make.playdate.base.App;
import com.make.playdate.base.BasePresenter;
import com.make.playdate.chat.domain.IChatInteractor;
import com.make.playdate.entities.ChatData;
import com.make.playdate.entities.PlaydateModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dmitrijfomenko on 03.03.2018.
 */
@InjectViewState
public class ChatPresenter extends BasePresenter<ChatView> {
    @Inject
    public IChatInteractor interactor;
    private ChatView chatView;

    public ChatPresenter() {
        App.getChatComponent().inject(this);
        chatView = getViewState();
    }

    public void viewCreated(PlaydateModel playdateModel) {
        checkDate(playdateModel);
        Disposable disposable = interactor.setChatData(playdateModel.firebaseId, playdateModel.userFullName)
                .andThen(interactor.loadUserId())
                .subscribe(s -> {
                    chatView.initList(s);
                    loadChat();
                }, Throwable::printStackTrace);
        putDisposable(disposable);
    }

    private void checkDate(PlaydateModel model) {
        if (model.isActive.equals("0")) getViewState().hideMessageInput();
    }

    private void loadChat() {
        Disposable disposable = interactor.loadConversationName()
                .subscribe(s -> getViewState().setConversationName(s), Throwable::printStackTrace);
        putDisposable(disposable);
        Disposable disposable1 = interactor.loadMessages()
                .subscribe(chatView::setMessage, Throwable::printStackTrace);
        Disposable disposable2 = interactor.checkUserTyping()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(chatView::showUserTyping, Throwable::printStackTrace);
        putDisposable(disposable1);
        putDisposable(disposable2);

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

    public void reportButonClicked() {
        putDisposable(interactor.flagUser().subscribe());
    }

    public void userTyping(boolean isTyping) {
        Disposable disposable = interactor.setTyping(isTyping).subscribe();
        putDisposable(disposable);
    }

}
