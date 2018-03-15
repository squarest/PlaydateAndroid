package com.make.playdate.questions.di;


import com.google.firebase.auth.FirebaseAuth;
import com.make.playdate.network.ApiClient;
import com.make.playdate.questions.data.IQARepo;
import com.make.playdate.questions.data.QARepo;
import com.make.playdate.questions.domain.IQAInteractor;
import com.make.playdate.questions.domain.QAInteractor;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dmitrijfomenko on 14.03.2018.
 */
@Module
public class QAModule {
    @Provides
    @QAScope
    public IQARepo provideQARepo(ApiClient apiClient, FirebaseAuth firebaseAuth) {
        return new QARepo(apiClient, firebaseAuth);
    }

    @Provides
    @QAScope
    public IQAInteractor provideQAInteractor(IQARepo iqaRepo) {
        return new QAInteractor(iqaRepo);
    }
}
