package com.make.playdate.main.di;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.make.playdate.main.data.IMainRepo;
import com.make.playdate.main.data.MainRepo;
import com.make.playdate.main.domain.IMainInteractor;
import com.make.playdate.main.domain.MainInteractor;
import com.make.playdate.network.ApiClient;
import com.make.playdate.utils.LocationProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dmitrijfomenko on 01.03.2018.
 */
@Module
public class MainModule {
    @Provides
    @MainScope
    public LoginManager provideLoginManager() {
        return LoginManager.getInstance();
    }

    @Provides
    @MainScope
    public IMainRepo provideMainRepo(LocationProvider locationProvider, ApiClient apiClient,
                                     FirebaseAuth firebaseAuth, LoginManager loginManager) {
        return new MainRepo(apiClient, locationProvider, firebaseAuth, loginManager);
    }

    @Provides
    @MainScope
    public IMainInteractor provideMainInteractor(IMainRepo mainRepo) {
        return new MainInteractor(mainRepo);
    }
}
