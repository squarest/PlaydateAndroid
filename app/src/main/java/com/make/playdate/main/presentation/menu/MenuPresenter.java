package com.make.playdate.main.presentation.menu;

import com.arellomobile.mvp.InjectViewState;
import com.make.playdate.base.App;
import com.make.playdate.base.BasePresenter;
import com.make.playdate.main.domain.IMainInteractor;

import javax.inject.Inject;

/**
 * Created by dmitrijfomenko on 01.03.2018.
 */
@InjectViewState
public class MenuPresenter extends BasePresenter<MenuView> {
    @Inject
    public IMainInteractor interactor;

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
