package com.make.playdate.chat.presentation.chatlist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.make.playdate.R;
import com.make.playdate.entities.PlaydateModel;
import com.make.playdate.main.presentation.main.MainView;

import java.util.List;

/**
 * Created by PRINCE on 11/14/2017.
 */

public class ChatListFragment extends MvpAppCompatFragment implements ChatListView {
    @InjectPresenter
    public ChatListPresenter presenter;
    private MainView mainView;
    private RecyclerView chatList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_chat_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chatList = view.findViewById(R.id.chatlist);
        mainView = (MainView) getActivity();
        presenter.viewCreated();
    }


    @Override
    public void showLoading() {
        mainView.showLoading();

    }

    @Override
    public void dismissLoading() {
        mainView.dismissLoading();
    }

    @Override
    public void setChatList(List<List<PlaydateModel>> chats) {
        chatList.setLayoutManager(new LinearLayoutManager(getContext()));
        chatList.setAdapter(new ChatListAdapter(chats));
    }

}
