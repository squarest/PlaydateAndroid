package com.make.playdate.preference.presentation.pref;

import com.arellomobile.mvp.InjectViewState;
import com.make.playdate.base.App;
import com.make.playdate.base.BasePresenter;
import com.make.playdate.entities.QuestionModel;
import com.make.playdate.entities.UserModel;
import com.make.playdate.preference.domain.IPreferenceInteractor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
        Disposable disposable = interactor.loadQuestion()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(QuestionModel::getImgCate)
                .subscribe(s -> getViewState().setQuestionImage(s));
        putDisposable(disposable);
    }

    public void questionCardClicked() {
        Disposable disposable = interactor.checkAnswers()
                .doOnSubscribe(disposable1 -> getViewState().showLoading())
                .doFinally(() -> getViewState().dismissLoading())
                .subscribe(isAnswered -> {
                    if (isAnswered) getViewState().showQADialog();
                    else getViewState().showQuestionScreen();
                }, Throwable::printStackTrace);
        putDisposable(disposable);
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
