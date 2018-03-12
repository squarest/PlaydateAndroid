package com.make.playdate.auth.di;

import com.google.firebase.auth.FirebaseAuth;
import com.make.playdate.auth.data.ILoginRepo;
import com.make.playdate.auth.data.LoginRepo;
import com.make.playdate.auth.domain.ILoginInteractor;
import com.make.playdate.auth.domain.LoginInteractor;
import com.make.playdate.network.ApiClient;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dmitrijfomenko on 27.02.2018.
 */
@Module
public class LoginModule {
    @Provides
    @LoginScope
    public ILoginRepo provideLoginRepo(ApiClient apiClient, FirebaseAuth firebaseAuth) {
        return new LoginRepo(apiClient, firebaseAuth);
    }

    @Provides
    @LoginScope
    public ILoginInteractor provideLoginInteractor(ILoginRepo loginRepo) {
        return new LoginInteractor(loginRepo);
    }
}
