package com.make.playdate.auth.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.make.playdate.auth.domain.ILoginInteractor;
import com.make.playdate.base.App;
import com.make.playdate.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dmitrijfomenko on 27.02.2018.
 */
@InjectViewState
public class LoginPresenter extends BasePresenter<LoginView> {
    @Inject
    ILoginInteractor interactor;

    public LoginPresenter() {
        App.getLoginComponent().inject(this);
    }

    public void activityCreated() {
        Disposable disposable = interactor.checkUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userExist ->
                {
                    if (userExist) getViewState().showNextScreen();
                    else getViewState().initView();
                });
        putDisposable(disposable);
    }

    public void fbLoginSuccess() {
        Disposable disposable1 = interactor.login()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> getViewState().showLoading())
                .doFinally(() -> getViewState().dismissLoading())
                .subscribe(() -> getViewState().showNextScreen(), Throwable::printStackTrace);
        putDisposable(disposable1);
    }
}
