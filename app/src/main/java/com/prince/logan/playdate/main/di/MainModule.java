package com.prince.logan.playdate.main.di;

import android.content.Context;

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
    public IMainRepo provideMainRepo(LocationProvider locationProvider, ApiClient apiClient) {
        return new MainRepo(apiClient, locationProvider);
    }

    @Provides
    @MainScope
    public IMainInteractor provideMainInteractor(IMainRepo mainRepo) {
        return new MainInteractor(mainRepo);
    }
}
