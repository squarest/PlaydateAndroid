package com.prince.logan.playdate.main.di;

import com.prince.logan.playdate.main.presentation.main.MainPresenter;
import com.prince.logan.playdate.main.presentation.menu.MenuPresenter;
import com.prince.logan.playdate.main.presentation.profile.ProfilePresenter;

/**
 * Created by dmitrijfomenko on 01.03.2018.
 */
@MainScope
public interface MainComponent {
    void inject(MainPresenter mainPresenter);

    void inject(ProfilePresenter profilePresenter);

    void inject(MenuPresenter menuPresenter);
}
