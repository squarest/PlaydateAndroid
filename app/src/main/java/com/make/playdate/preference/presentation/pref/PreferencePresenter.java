package com.make.playdate.preference.presentation.pref;

import com.arellomobile.mvp.InjectViewState;
import com.make.playdate.base.App;
import com.make.playdate.base.BasePresenter;
import com.make.playdate.entities.UserModel;
import com.make.playdate.preference.domain.IPreferenceInteractor;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by dmitrijfomenko on 10.03.2018.
 */
@InjectViewState
public class PreferencePresenter extends BasePresenter<PreferenceView> {
    @Inject
    IPreferenceInteractor interactor;

    public PreferencePresenter() {
        App.getPreferenceComponent().inject(this);
    }

    public void viewCreated() {
        Disposable d = interactor.loadUser().
                doOnSubscribe(disposable -> getViewState().showLoading())
                .doFinally(() -> getViewState().dismissLoading())
                .subscribe(userModel -> getViewState().setPreferences(userModel), Throwable::printStackTrace);
        putDisposable(d);
    }

    public void savePreferences(UserModel userModel) {
        Disposable d = interactor.updatePreferences(userModel)
                .doOnSubscribe(disposable -> getViewState().showLoading())
                .doFinally(() -> getViewState().dismissLoading())
                .subscribe(() -> {
                }, Throwable::printStackTrace);
        putDisposable(d);
    }
}
