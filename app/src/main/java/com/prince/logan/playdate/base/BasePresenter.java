package com.prince.logan.playdate.base;

import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by dmitrijfomenko on 27.02.2018.
 */


public class BasePresenter<T extends BaseView> extends MvpPresenter<T> {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void putDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void destroyView(T view) {
        compositeDisposable.dispose();
        super.destroyView(view);
    }
}

