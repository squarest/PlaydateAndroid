package com.make.playdate.playdate.presentation.details;

import com.arellomobile.mvp.InjectViewState;
import com.make.playdate.base.App;
import com.make.playdate.base.BasePresenter;
import com.make.playdate.playdate.domain.IPlaydateInteractor;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by dmitrijfomenko on 07.03.2018.
 */
@InjectViewState
public class PlaydateDetailPresenter extends BasePresenter<PlaydateDetailView> {
    @Inject
    public IPlaydateInteractor interactor;
    private PlaydateDetailView view;

    public PlaydateDetailPresenter() {
        App.getPlaydateComponent().inject(this);
        view = getViewState();
    }

    public void viewCreated(String id) {
        Disposable disposable = interactor.setSelectedPlaydate(id)
                .doOnSubscribe(d -> view.showLoading())
                .subscribe(view::setUserData, throwable ->
                {
                    if (throwable instanceof HttpException && ((HttpException) throwable).code() == 500) {
                        Disposable d = interactor.getSelectedPlaydate()
                                .subscribe(view::setUserData, Throwable::printStackTrace);
                        putDisposable(d);
                    }
                });
        putDisposable(disposable);
    }


    public void chatButtonClicked() {
        Disposable disposable = interactor.getSelectedPlaydate()
                .subscribe(view::showChat);
        putDisposable(disposable);
    }
}
