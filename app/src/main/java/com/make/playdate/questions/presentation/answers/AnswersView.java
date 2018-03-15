package com.make.playdate.questions.presentation.answers;

import com.make.playdate.base.BaseView;
import com.make.playdate.entities.QAModel;

import java.util.ArrayList;

/**
 * Created by dmitrijfomenko on 14.03.2018.
 */

public interface AnswersView extends BaseView {
    void showAnswers(ArrayList<QAModel> qaModels);
}
