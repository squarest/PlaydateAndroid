package com.make.playdate.chat.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.make.playdate.chat.data.ChatRepo;
import com.make.playdate.chat.data.IChatRepo;
import com.make.playdate.chat.domain.ChatInteractor;
import com.make.playdate.chat.domain.IChatInteractor;
import com.make.playdate.network.ApiClient;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dmitrijfomenko on 03.03.2018.
 */
@Module
public class ChatModule {
    @Provides
    @ChatScope
    public FirebaseDatabase provideFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

    @Provides
    @ChatScope
    public IChatRepo provideChatRepo(ApiClient apiClient, FirebaseDatabase firebaseDatabase, FirebaseAuth firebaseAuth) {
        return new ChatRepo(apiClient, firebaseDatabase, firebaseAuth);
    }

    @Provides
    @ChatScope
    public IChatInteractor provideChatInteractor(IChatRepo chatRepo) {
        return new ChatInteractor(chatRepo);
    }
}
