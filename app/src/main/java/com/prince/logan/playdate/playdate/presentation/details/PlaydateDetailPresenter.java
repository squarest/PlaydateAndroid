package com.prince.logan.playdate.playdate.presentation.details;

import com.prince.logan.playdate.base.App;
import com.prince.logan.playdate.base.BasePresenter;
import com.prince.logan.playdate.playdate.domain.IPlaydateInteractor;

import javax.inject.Inject;

/**
 * Created by dmitrijfomenko on 07.03.2018.
 */

public class PlaydateDetailPresenter extends BasePresenter<PlaydateDetailView> {
    @Inject
    public IPlaydateInteractor interactor;

    public PlaydateDetailPresenter() {
        App.getPlaydateComponent().inject(this);
    }
}
