package com.make.playdate.playdate.presentation.list;

import com.arellomobile.mvp.InjectViewState;
import com.make.playdate.R;
import com.make.playdate.base.App;
import com.make.playdate.base.BasePresenter;
import com.make.playdate.exceptions.PlaydateLimitReachedException;
import com.make.playdate.exceptions.RequestException;
import com.make.playdate.playdate.domain.IPlaydateInteractor;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by dmitrijfomenko on 07.03.2018.
 */

@InjectViewState
public class PlaydatePresenter extends BasePresenter<PlaydateView> {
    @Inject
    public IPlaydateInteractor interactor;
    private PlaydateView playdateView;

    public PlaydatePresenter() {
        App.getPlaydateComponent().inject(this);
        playdateView = getViewState();
    }

    void viewCreated() {
        Disposable disposable = interactor.loadAllMatchedUsers()
                .doOnSubscribe(disposable1 -> playdateView.showLoading())
                .doFinally(() -> playdateView.dismissLoading())
                .subscribe(playdateView::setPlaydates, throwable -> {
                    throwable.printStackTrace();
                    playdateView.setPlaydates(new ArrayList<>());
                });
        putDisposable(disposable);
    }

    void makePlaydateButtonClicked() {
        Disposable disposable = interactor.loadMatchUser()
                .doOnError(throwable -> playdateView.dismissLoading())
                .subscribe(playdateView::addNewPlaydate, throwable -> {
                    if (throwable instanceof PlaydateLimitReachedException)
                        playdateView.showPlaydateFailedDialog();
                    else if (throwable instanceof RequestException)
                        playdateView.showMessage(R.string.error);
                    else throwable.printStackTrace();
                });
        putDisposable(disposable);
    }
}
