package com.make.playdate.playdate.di;

import com.make.playdate.playdate.presentation.details.PlaydateDetailPresenter;
import com.make.playdate.playdate.presentation.list.PlaydatePresenter;

import dagger.Subcomponent;

/**
 * Created by dmitrijfomenko on 07.03.2018.
 */
@Subcomponent(modules = PlaydateModule.class)
@PlaydateScope
public interface PlaydateComponent {
    void inject(PlaydatePresenter playdatePresenter);
    void inject(PlaydateDetailPresenter playdateDetailPresenter);
}
