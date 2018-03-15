package com.make.playdate.questions.presentation.answers;

import com.arellomobile.mvp.InjectViewState;
import com.make.playdate.base.App;
import com.make.playdate.base.BasePresenter;
import com.make.playdate.questions.domain.IQAInteractor;

import javax.inject.Inject;

/**
 * Created by dmitrijfomenko on 14.03.2018.
 */
@InjectViewState
public class AnswersPresenter extends BasePresenter<AnswersView> {
    @Inject
    public IQAInteractor interactor;

    public AnswersPresenter() {
        App.getQaComponent().inject(this);
    }

    public void viewCreated() {
        interactor.getQAs()
                .subscribe(qaModels -> getViewState().showAnswers(qaModels), Throwable::printStackTrace);
    }
}
