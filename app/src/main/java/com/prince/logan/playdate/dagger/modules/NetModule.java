package com.prince.logan.playdate.dagger.modules;

import com.prince.logan.playdate.network.ApiClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chudofom on 22.12.17.
 */
@Module
public class NetModule {
    @Provides
    @Singleton
    public ApiClient provideRestClient() {
        return new ApiClient();
    }
}
