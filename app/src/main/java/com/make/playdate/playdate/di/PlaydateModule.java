package com.make.playdate.playdate.di;

import com.google.firebase.auth.FirebaseAuth;
import com.make.playdate.network.ApiClient;
import com.make.playdate.playdate.data.IPlaydateRepo;
import com.make.playdate.playdate.data.PLaydateRepo;
import com.make.playdate.playdate.domain.IPlaydateInteractor;
import com.make.playdate.playdate.domain.PlaydateInteractor;
import com.make.playdate.utils.LocationProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dmitrijfomenko on 07.03.2018.
 */
@Module
public class PlaydateModule {
    @PlaydateScope
    @Provides
    public IPlaydateRepo providePlaydateRepo(ApiClient apiClient, FirebaseAuth firebaseAuth, LocationProvider locationProvider) {
        return new PLaydateRepo(apiClient, firebaseAuth, locationProvider);
    }

    @PlaydateScope
    @Provides
    public IPlaydateInteractor providePlaydateINteractor(IPlaydateRepo playdateRepo) {
        return new PlaydateInteractor(playdateRepo);
    }
}
