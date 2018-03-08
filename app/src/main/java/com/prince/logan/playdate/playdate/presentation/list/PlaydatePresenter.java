package com.prince.logan.playdate.playdate.presentation.list;

import com.arellomobile.mvp.InjectViewState;
import com.prince.logan.playdate.base.App;
import com.prince.logan.playdate.base.BasePresenter;
import com.prince.logan.playdate.entities.PlaydateModel;
import com.prince.logan.playdate.playdate.domain.IPlaydateInteractor;

import javax.inject.Inject;

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
        interactor.loadAllMatchedUsers()
                .subscribe(playdateView::setPlaydates, Throwable::printStackTrace);
    }

    void makePlaydateButtonClicked() {
        interactor.loadMatchUser()
                .subscribe(playdateView::addNewPlaydate, Throwable::printStackTrace);
    }

    void userSelected(PlaydateModel playdateModel) {
        interactor.setSelectedPlaydate(playdateModel)
                .subscribe(() -> playdateView.showDetailedPlaydate());
    }
}
