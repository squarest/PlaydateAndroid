package com.prince.logan.playdate.auth.di;

import com.google.firebase.auth.FirebaseAuth;
import com.prince.logan.playdate.auth.data.ILoginRepo;
import com.prince.logan.playdate.auth.data.LoginRepo;
import com.prince.logan.playdate.auth.domain.ILoginInteractor;
import com.prince.logan.playdate.auth.domain.LoginInteractor;
import com.prince.logan.playdate.network.ApiClient;

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
