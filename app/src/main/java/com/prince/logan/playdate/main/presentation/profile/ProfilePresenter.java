package com.prince.logan.playdate.main.presentation.profile;

import com.arellomobile.mvp.InjectViewState;
import com.prince.logan.playdate.base.App;
import com.prince.logan.playdate.base.BasePresenter;
import com.prince.logan.playdate.entities.QuestionModel;
import com.prince.logan.playdate.main.domain.IMainInteractor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
        Disposable d = interactor.loadUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> profileView.dismissLoading())
                .subscribe(userModel -> {
                    profileView.setProfilePhoto(userModel.get_user_avatar());
                    profileView.setName(userModel.get_user_first_name());
                }, Throwable::printStackTrace);
        putDisposable(d);
        Disposable disposable = interactor.loadQuestion()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(QuestionModel::getImgCate)
                .subscribe(profileView::setQuestionImage);
        putDisposable(disposable);
    }
}
