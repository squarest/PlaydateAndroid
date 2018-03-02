package com.prince.logan.playdate.main.presentation.main;

import com.arellomobile.mvp.InjectViewState;
import com.prince.logan.playdate.base.App;
import com.prince.logan.playdate.base.BasePresenter;
import com.prince.logan.playdate.main.domain.IMainInteractor;

import javax.inject.Inject;

/**
 * Created by dmitrijfomenko on 01.03.2018.
 */
@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {
    @Inject
    public IMainInteractor mainInteractor;

    public MainPresenter() {
        App.getMainComponent().inject(this);
    }

    public void viewCreated() {
        mainInteractor.startLocationTracking();
    }
}
