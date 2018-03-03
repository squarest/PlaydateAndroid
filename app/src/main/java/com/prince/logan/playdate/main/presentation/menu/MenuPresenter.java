package com.prince.logan.playdate.main.presentation.menu;

import com.arellomobile.mvp.InjectViewState;
import com.prince.logan.playdate.base.App;
import com.prince.logan.playdate.base.BasePresenter;
import com.prince.logan.playdate.main.domain.MainInteractor;

import javax.inject.Inject;

/**
 * Created by dmitrijfomenko on 01.03.2018.
 */
@InjectViewState
public class MenuPresenter extends BasePresenter<MenuView> {
    @Inject
    public MainInteractor interactor;

    public MenuPresenter() {
        App.getMainComponent().inject(this);
    }

    public void logoutButtonClicked() {
        interactor.logout()
                .doOnSubscribe(disposable -> getViewState().showLoading())
                .doOnTerminate(() -> getViewState().dismissLoading())
                .subscribe(() -> getViewState().showLoginScreen());
    }

    public void deleteButtonClicked() {
        interactor.deleteUser()
                .doOnSubscribe(disposable -> getViewState().showLoading())
                .doOnTerminate(() -> getViewState().dismissLoading())
                .subscribe(() -> getViewState().showLoginScreen());
    }
}
