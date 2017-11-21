package com.prince.logan.playdate.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prince.logan.playdate.Adapter.ChatAdapter;
import com.prince.logan.playdate.Model.ChatData;
import com.prince.logan.playdate.Model.UserModel;
import com.prince.logan.playdate.R;

import java.util.Date;

/**
 * Created by PRINCE on 11/20/2017.
 */

public class ChatActivity extends Activity implements View.OnClickListener{
    private FirebaseDatabase mFirebaseDatabase;
//    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ChatAdapter mAdapter;
    private ListView mListView;
    private EditText mEdtMessage;
    private ImageView back;
    private TextView txtUserName;

    UserModel chatUserDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            chatUserDetail = (UserModel)bundle.getSerializable("userModel");
        }

        initViews();
        initFirebaseDatabase();
        initValues();
    }

    private void initValues() {
//        userName = "Guest" + new Random().nextInt(5000);
    }


    private void initViews() {
        mListView = (ListView) findViewById(R.id.list_message);
        mAdapter = new ChatAdapter(this, 0);
        mListView.setAdapter(mAdapter);

        mEdtMessage = (EditText) findViewById(R.id.edit_message);
        back = (ImageView)findViewById(R.id.img_chat_back);
        txtUserName = (TextView)findViewById(R.id.txt_chat_user_name);
        txtUserName.setText(chatUserDetail.get_user_full_name());
        back.setOnClickListener(this);
        findViewById(R.id.btn_send).setOnClickListener(this);
    }

    private void initFirebaseDatabase() {

        String roomId;

        int compare = chatUserDetail.get_firebase_id().compareTo(MainActivity.userFirebaseID);
        if (compare>0){
            roomId = MainActivity.userFirebaseID + chatUserDetail.get_firebase_id();
        }
        else{
            roomId = chatUserDetail.get_firebase_id() + MainActivity.userFirebaseID;
        }

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("chats/"+roomId+"/messages");

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);
                chatData.firebaseId = dataSnapshot.getKey();
                mAdapter.add(chatData);
                mListView.smoothScrollToPosition(mAdapter.getCount());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String firebaseKey = dataSnapshot.getKey();
                int count = mAdapter.getCount();
                for (int i = 0; i < count; i++) {
                    if (mAdapter.getItem(i).firebaseId.equals(firebaseKey)) {
                        mAdapter.remove(mAdapter.getItem(i));
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseReference.removeEventListener(mChildEventListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_chat_back:
                this.finish();
                break;
            case R.id.btn_send:
                String message = mEdtMessage.getText().toString();

                if (!TextUtils.isEmpty(message)) {
                    mEdtMessage.setText("");
                    ChatData chatData = new ChatData();
                    chatData.senderId = MainActivity.userFirebaseID;
                    chatData.text = message;
                    long time = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss xxxx");
                    chatData.date= String.valueOf(new Date(time));
                    mDatabaseReference.push().setValue(chatData);
                }
                break;
        }
    }
}