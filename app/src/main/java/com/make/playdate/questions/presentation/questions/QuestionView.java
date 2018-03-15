package com.make.playdate.questions.presentation.questions;

import com.make.playdate.base.BaseView;
import com.make.playdate.entities.QuestionModel;

import java.util.ArrayList;

/**
 * Created by dmitrijfomenko on 14.03.2018.
 */

public interface QuestionView extends BaseView {
    void setQuestions(ArrayList<QuestionModel> qaArrayList);

    void snowMainScreen();
}
