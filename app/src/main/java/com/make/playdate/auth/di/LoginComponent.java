package com.make.playdate.auth.di;

import com.make.playdate.auth.presentation.LoginPresenter;

import dagger.Subcomponent;

/**
 * Created by dmitrijfomenko on 27.02.2018.
 */
@LoginScope
@Subcomponent(modules = LoginModule.class)
public interface LoginComponent {
    void inject(LoginPresenter loginPresenter);
}
