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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.Completable;
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
    public Completable setChat(String conversationId) {
        int compare = conversationId.compareTo(getUid());

        if (compare > 0) {
            roomId = getUid() + conversationId;
        } else {
            roomId = conversationId + getUid();
        }
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("chats/" + roomId + "/messages");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.US);
        String curDateTime = sdf.format(new Date(System.currentTimeMillis()));
        DatabaseReference last = mFirebaseDatabase.getReference().child("chats/" + roomId + "/last");
        last.child(getUid()).setValue(curDateTime);

        DatabaseReference users = mFirebaseDatabase.getReference().child("chats/" + roomId + "/users");
        users.child(getUid()).setValue(getUid());
        users.child(conversationId).setValue(conversationId);
        return Completable.complete();
    }

    @Override
    public Observable<ChatData> getMessages() {

        return Observable.create(e -> {
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

    @Override
    public void setUserTyping(boolean isTyping) {
        mFirebaseDatabase.getReference("chats/" + roomId + "/typingIndicator")
                .child(getUid()).setValue(isTyping);
    }

    private ChildEventListener childEventListener;

    @Override
    public Observable<Boolean> getTypingIndicator(String conversationId) {
        return Observable.create(e -> {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.getKey().equals(conversationId)) {
                        boolean isTyping = (boolean) dataSnapshot.getValue();
                        e.onNext(isTyping);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.getKey().equals(conversationId)) {
                        boolean isTyping = (boolean) dataSnapshot.getValue();
                        e.onNext(isTyping);
                    }
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
            mFirebaseDatabase.getReference("chats/" + roomId + "/typingIndicator")
                    .addChildEventListener(childEventListener);
        });

    }
}
