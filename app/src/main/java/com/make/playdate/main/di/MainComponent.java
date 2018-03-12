package com.make.playdate.main.di;

import com.make.playdate.main.presentation.main.MainPresenter;
import com.make.playdate.main.presentation.menu.MenuPresenter;
import com.make.playdate.main.presentation.profile.ProfilePresenter;

import dagger.Subcomponent;

/**
 * Created by dmitrijfomenko on 01.03.2018.
 */
@MainScope
@Subcomponent(modules = MainModule.class)
public interface MainComponent {
    void inject(MainPresenter mainPresenter);

    void inject(ProfilePresenter profilePresenter);

    void inject(MenuPresenter menuPresenter);
}
