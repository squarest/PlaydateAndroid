package com.prince.logan.playdate.playdate.di;

import com.google.firebase.auth.FirebaseAuth;
import com.prince.logan.playdate.network.ApiClient;
import com.prince.logan.playdate.playdate.data.IPlaydateRepo;
import com.prince.logan.playdate.playdate.data.PLaydateRepo;
import com.prince.logan.playdate.playdate.domain.IPlaydateInteractor;
import com.prince.logan.playdate.playdate.domain.PlaydateInteractor;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dmitrijfomenko on 07.03.2018.
 */
@Module
public class PlaydateModule {
    @PlaydateScope
    @Provides
    public IPlaydateRepo providePlaydateRepo(ApiClient apiClient, FirebaseAuth firebaseAuth) {
        return new PLaydateRepo(apiClient, firebaseAuth);
    }

    @PlaydateScope
    @Provides
    public IPlaydateInteractor providePlaydateINteractor(IPlaydateRepo playdateRepo) {
        return new PlaydateInteractor(playdateRepo);
    }
}
