package com.prince.logan.playdate.main.di;

import com.prince.logan.playdate.main.presentation.main.MainPresenter;

/**
 * Created by dmitrijfomenko on 01.03.2018.
 */
@MainScope
public interface MainComponent {
    void inject(MainPresenter mainPresenter);
}
