package com.make.playdate.main.presentation.profile;

import com.arellomobile.mvp.InjectViewState;
import com.make.playdate.base.App;
import com.make.playdate.base.BasePresenter;
import com.make.playdate.main.domain.IMainInteractor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dmitrijfomenko on 01.03.2018.
 */
@InjectViewState
public class ProfilePresenter extends BasePresenter<ProfileView> {
    private ProfileView profileView;
    @Inject
    public IMainInteractor interactor;

    public ProfilePresenter() {
        App.getMainComponent().inject(this);
        profileView = getViewState();
    }

    public void viewCreated() {
        profileView.showLoading();
        interactor.loadUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> profileView.dismissLoading())
                .subscribe(userModel -> {
                    String avatar = userModel.get_user_avatar();
                    if (avatar != null && !avatar.isEmpty())
                        profileView.setProfilePhoto(avatar);
                    profileView.setName(userModel.get_user_first_name());
                    profileView.setSwitch(userModel.getUser_playdate() == 1);
                }, Throwable::printStackTrace);
    }

    public void userPlaydateChanged(boolean isPlaydate) {
        interactor.changePlaydateStatus(isPlaydate)
                .subscribe(() -> {
                }, Throwable::printStackTrace);
    }
}
