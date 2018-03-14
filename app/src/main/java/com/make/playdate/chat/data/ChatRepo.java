package com.make.playdate.chat.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.make.playdate.entities.ChatData;
import com.make.playdate.entities.PlaydateModel;
import com.make.playdate.entities.ResponseModel;
import com.make.playdate.entities.UserModel;
import com.make.playdate.network.ApiClient;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by dmitrijfomenko on 03.03.2018.
 */

public class ChatRepo implements IChatRepo {
    private ApiClient apiClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;

    public ChatRepo(ApiClient apiClient, FirebaseDatabase firebaseDatabase, FirebaseAuth firebaseAuth) {
        this.apiClient = apiClient;
        this.mFirebaseDatabase = firebaseDatabase;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public String getUid() {
        return firebaseAuth.getUid();
    }

    @Override
    public Single<List<List<PlaydateModel>>> getAllChats() {
        return apiClient.getApi().getGroupedMatchedUsers(getUid())
                .map(ResponseModel::getGroupedPlaydates);
    }

    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private String roomId;

    @Override
    public Observable<ChatData> getMessages(String conversationId) {
        int compare = conversationId.compareTo(getUid());
        if (compare > 0) {
            roomId = getUid() + conversationId;
        } else {
            roomId = conversationId + getUid();
        }
        return Observable.create(e -> {
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mDatabaseReference = mFirebaseDatabase.getReference("chats/" + roomId + "/messages");

            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatData chatData = dataSnapshot.getValue(ChatData.class);
                    if (chatData != null) {
                        chatData.firebaseId = dataSnapshot.getKey();
                        e.onNext(chatData);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            mDatabaseReference.addChildEventListener(mChildEventListener);
        });
    }

    @Override
    public void pushMessage(ChatData chatData) {
        chatData.senderId = getUid();
        mDatabaseReference.push().setValue(chatData);
    }

    @Override
    public Single<ResponseModel> sendNotification(String conversationId, ChatData chatData) {
        return getUser().flatMap(userModel -> apiClient.getApi().send_notification(userModel.get_user_full_name(),
                userModel.get_firebase_id(), conversationId, chatData.text));
    }

    @Override
    public Single<UserModel> getUser() {
        return apiClient.getApi().login(getUid())
                .map(ResponseModel::getUser);
    }

    @Override
    public void removeChat() {
        mDatabaseReference.removeEventListener(mChildEventListener);
    }

    @Override
    public Single<ResponseModel> flagUser(String flaggeeId) {
        return apiClient.getApi().flagUser(getUid(), flaggeeId);
    }
}
