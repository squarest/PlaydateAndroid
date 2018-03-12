package com.make.playdate.main.presentation.menu;

import com.arellomobile.mvp.InjectViewState;
import com.make.playdate.base.App;
import com.make.playdate.base.BasePresenter;
import com.make.playdate.main.domain.IMainInteractor;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

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
        Disposable disposable1 = interactor.logout()
                .doOnSubscribe(disposable -> getViewState().showLoading())
                .doOnTerminate(() -> getViewState().dismissLoading())
                .subscribe(() -> getViewState().showLoginScreen());
        putDisposable(disposable1);
    }

    public void deleteButtonClicked() {
        Disposable disposable1 = interactor.deleteUser()
                .doOnSubscribe(disposable -> getViewState().showLoading())
                .doOnTerminate(() -> getViewState().dismissLoading())
                .subscribe(() -> getViewState().showLoginScreen());
        putDisposable(disposable1);
    }
}
