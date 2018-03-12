package com.make.playdate.preference.di;

import com.make.playdate.preference.presentation.editprofile.EditProfilePresenter;
import com.make.playdate.preference.presentation.pref.PreferencePresenter;

import dagger.Subcomponent;

/**
 * Created by dmitrijfomenko on 10.03.2018.
 */
@PreferenceScope
@Subcomponent(modules = PreferenceModule.class)
public interface PreferenceComponent {
    void inject(EditProfilePresenter editProfilePresenter);

    void inject(PreferencePresenter preferencePresenter);
}
