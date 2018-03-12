package com.make.playdate.preference.presentation.editprofile;

import com.arellomobile.mvp.InjectViewState;
import com.make.playdate.base.App;
import com.make.playdate.base.BasePresenter;
import com.make.playdate.entities.UserModel;
import com.make.playdate.preference.domain.IPreferenceInteractor;

import javax.inject.Inject;

/**
 * Created by dmitrijfomenko on 10.03.2018.
 */
@InjectViewState
public class EditProfilePresenter extends BasePresenter<EditProfileView> {
    @Inject
    public IPreferenceInteractor interactor;

    public EditProfilePresenter() {
        App.getPreferenceComponent().inject(this);
    }

    public void viewCreated() {
        interactor.loadUser()
                .doOnSubscribe(disposable -> getViewState().showLoading())
                .doFinally(() -> getViewState().dismissLoading())
                .subscribe(userModel -> getViewState().setUser(userModel), Throwable::printStackTrace);
    }

    public void saveButtonClicked(UserModel userModel) {
        interactor.updateUser(userModel)
                .doOnSubscribe(disposable -> getViewState().showLoading())
                .doFinally(() -> getViewState().dismissLoading())
                .subscribe(() -> {
                }, Throwable::printStackTrace);

    }
}
