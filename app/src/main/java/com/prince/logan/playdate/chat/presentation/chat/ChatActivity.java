package com.prince.logan.playdate.chat.presentation.chat;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.prince.logan.playdate.Adapter.ChatAdapter;
import com.prince.logan.playdate.R;
import com.prince.logan.playdate.base.BaseActivity;
import com.prince.logan.playdate.entities.ChatData;

/**
 * Created by PRINCE on 11/20/2017.
 */

public class ChatActivity extends BaseActivity implements ChatView {

    private ListView mListView;
    private EditText mEdtMessage;
    private ChatAdapter mAdapter;


    @InjectPresenter
    public ChatPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        presenter.viewCreated();
        initViews();
    }

    private void initViews() {
        mEdtMessage = findViewById(R.id.edit_message);
        ImageView back = findViewById(R.id.img_chat_back);
        back.setOnClickListener(view -> finish());

        TextView sendButton = findViewById(R.id.btn_send);
        sendButton.setOnClickListener(view -> {
            String message = mEdtMessage.getText().toString();

            if (!TextUtils.isEmpty(message)) {
                mEdtMessage.setText("");
                ChatData chatData = new ChatData();
                chatData.text = message;
                presenter.sendButtonClicked(chatData);
            }
        });
    }

    @Override
    public void initList(String firebaseId) {
        mListView = findViewById(R.id.list_message);
        mAdapter = new ChatAdapter(this, 0, firebaseId);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void setMessage(ChatData chatData) {
        mAdapter.add(chatData);
        mListView.smoothScrollToPosition(mAdapter.getCount());
    }

    @Override
    public void setConversationName(String name) {
        TextView txtUserName = findViewById(R.id.txt_chat_user_name);
        txtUserName.setText(name);
    }


}