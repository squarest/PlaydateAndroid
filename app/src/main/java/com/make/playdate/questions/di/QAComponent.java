package com.make.playdate.questions.di;

import com.make.playdate.questions.presentation.answers.AnswersPresenter;
import com.make.playdate.questions.presentation.questions.QuestionsPresenter;

import dagger.Subcomponent;

/**
 * Created by dmitrijfomenko on 14.03.2018.
 */
@Subcomponent(modules = QAModule.class)
@QAScope
public interface QAComponent {
    void inject(AnswersPresenter answersPresenter);

    void inject(QuestionsPresenter questionsPresenter);
}
