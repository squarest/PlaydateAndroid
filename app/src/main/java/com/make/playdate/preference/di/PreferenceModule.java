package com.make.playdate.preference.di;

import com.google.firebase.auth.FirebaseAuth;
import com.make.playdate.network.ApiClient;
import com.make.playdate.preference.data.IPreferenceRepo;
import com.make.playdate.preference.data.PreferenceRepo;
import com.make.playdate.preference.domain.IPreferenceInteractor;
import com.make.playdate.preference.domain.PreferenceInteractor;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dmitrijfomenko on 10.03.2018.
 */
@Module
public class PreferenceModule {
    @PreferenceScope
    @Provides
    public IPreferenceRepo providePreferenceRepo(ApiClient apiClient, FirebaseAuth firebaseAuth) {
        return new PreferenceRepo(apiClient, firebaseAuth);
    }

    @PreferenceScope
    @Provides
    public IPreferenceInteractor providePreferenceInteractor(IPreferenceRepo preferenceRepo) {
        return new PreferenceInteractor(preferenceRepo);
    }
}
