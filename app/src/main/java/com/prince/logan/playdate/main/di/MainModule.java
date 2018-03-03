package com.prince.logan.playdate.main.di;

import android.content.Context;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.prince.logan.playdate.main.data.IMainRepo;
import com.prince.logan.playdate.main.data.MainRepo;
import com.prince.logan.playdate.main.domain.IMainInteractor;
import com.prince.logan.playdate.main.domain.MainInteractor;
import com.prince.logan.playdate.network.ApiClient;
import com.prince.logan.playdate.utils.LocationProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dmitrijfomenko on 01.03.2018.
 */
@Module
public class MainModule {
    @Provides
    @MainScope
    public LocationProvider provideLocationProvider(Context context) {
        return new LocationProvider(context);
    }

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
