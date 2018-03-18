package com.make.playdate.chat.presentation.chatlist;

import com.arellomobile.mvp.InjectViewState;
import com.make.playdate.base.App;
import com.make.playdate.base.BasePresenter;
import com.make.playdate.chat.domain.IChatInteractor;

import javax.inject.Inject;

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
        interactor.loadChats()
                .doOnSubscribe(disposable -> chatListView.showLoading())
                .doFinally(() -> chatListView.dismissLoading())
                .subscribe(chatListView::setChatList, Throwable::printStackTrace);
    }

}
