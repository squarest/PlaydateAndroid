package com.prince.logan.playdate.base;

import com.arellomobile.mvp.MvpView;

/**
 * Created by dmitrijfomenko on 01.03.2018.
 */

public interface BaseView extends MvpView {
    void showLoading();

    void dismissLoading();
}
