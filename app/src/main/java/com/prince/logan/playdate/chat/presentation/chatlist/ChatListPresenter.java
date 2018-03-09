package com.prince.logan.playdate.chat.presentation.chatlist;

import com.arellomobile.mvp.InjectViewState;
import com.prince.logan.playdate.base.App;
import com.prince.logan.playdate.base.BasePresenter;
import com.prince.logan.playdate.chat.domain.IChatInteractor;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by dmitrijfomenko on 03.03.2018.
 */
@InjectViewState
public class ChatListPresenter extends BasePresenter<ChatListView> {
    @Inject
    public IChatInteractor interactor;
    private ChatListView chatListView;

    public ChatListPresenter() {
        App.getChatComponent().inject(this);
        chatListView = getViewState();
    }

    void viewCreated() {
        Disposable disposable1 = interactor.loadChats()
                .doOnSubscribe(disposable -> chatListView.showLoading())
                .doFinally(() -> chatListView.dismissLoading())
                .subscribe(chatListView::setChatList, Throwable::printStackTrace);
        putDisposable(disposable1);
    }

}
