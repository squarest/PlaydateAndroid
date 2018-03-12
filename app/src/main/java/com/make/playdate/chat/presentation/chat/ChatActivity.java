package com.make.playdate.chat.presentation.chat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.make.playdate.R;
import com.make.playdate.base.BaseActivity;
import com.make.playdate.entities.ChatData;
import com.make.playdate.entities.PlaydateModel;
import com.make.playdate.playdate.presentation.details.PlaydateDetailActivity;

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
        presenter.viewCreated((PlaydateModel) getIntent().getSerializableExtra("chatData"));
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

    private void showReportDialog(String name) {
        Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.report_dialog, null);
        TextView description = view.findViewById(R.id.description);
        description.setText(String.format("We won't tell %s either way", name));
        dialog.setContentView(view);
        view.setOnClickListener(v ->
        {
            switch (v.getId()) {
                case R.id.spam_button: {
                    presenter.reportButonClicked(0);
                    break;
                }
                case R.id.behav_button: {
                    presenter.reportButonClicked(1);
                    break;
                }
            }
            dialog.dismiss();
        });
        dialog.show();

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
        txtUserName.setOnClickListener(view ->
                {
                    PlaydateModel playdateModel = (PlaydateModel) getIntent().getSerializableExtra("chatData");
                    Intent intent = new Intent(ChatActivity.this, PlaydateDetailActivity.class);
                    intent.putExtra("playdateId", playdateModel.firebaseId);
                    startActivity(intent);
                }
        );
        TextView reportButton = findViewById(R.id.report_button);
        reportButton.setOnClickListener(view -> showReportDialog(name));
    }

    @Override
    public void hideMessageInput() {
        LinearLayout linearLayout = findViewById(R.id.message_input);
        linearLayout.setVisibility(View.GONE);
    }


}