package com.make.playdate.questions.presentation.questions;

import com.arellomobile.mvp.InjectViewState;
import com.make.playdate.base.App;
import com.make.playdate.base.BasePresenter;
import com.make.playdate.questions.domain.IQAInteractor;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by dmitrijfomenko on 14.03.2018.
 */
@InjectViewState
public class QuestionsPresenter extends BasePresenter<QuestionView> {
    @Inject
    public IQAInteractor interactor;

    public QuestionsPresenter() {
        App.getQaComponent().inject(this);
    }

    public void viewCreated() {
        Disposable disposable = interactor.loadQuestions()
                .doOnSubscribe(disposable1 -> getViewState().showLoading())
                .doFinally(() -> getViewState().dismissLoading())
                .subscribe(questions -> getViewState().setQuestions(questions), Throwable::printStackTrace);
        putDisposable(disposable);
    }

    public void saveAnswersButtonClicked(String answers) {
        Disposable disposable = interactor.saveAnswers(answers)
                .doOnSubscribe(disposable1 -> getViewState().showLoading())
                .doFinally(() -> getViewState().dismissLoading())
                .subscribe(() -> getViewState().snowMainScreen(), Throwable::printStackTrace);
        putDisposable(disposable);
    }

}
