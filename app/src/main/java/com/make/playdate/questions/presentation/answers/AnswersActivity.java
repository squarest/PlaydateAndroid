package com.make.playdate.questions.presentation.answers;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.make.playdate.R;
import com.make.playdate.base.BaseActivity;
import com.make.playdate.databinding.ActivityViewAnswersBinding;
import com.make.playdate.entities.QAModel;
import com.make.playdate.questions.presentation.questions.QuestionActivity;

import java.util.ArrayList;

/**
 * Created by PRINCE on 11/28/2017.
 */

public class AnswersActivity extends BaseActivity implements AnswersView {
    private ActivityViewAnswersBinding binding;
    @InjectPresenter
    public AnswersPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_answers);
        presenter.viewCreated();
        binding.imgAnswerBack.setOnClickListener(view -> onBackPressed());
        binding.btnReanswer.setOnClickListener(view ->
        {
            Intent questionIntent = new Intent(AnswersActivity.this, QuestionActivity.class);
            startActivity(questionIntent);
        });
    }

    @Override
    public void showAnswers(ArrayList<QAModel> qaModels) {
        AnswerAdapter answerAdapter = new AnswerAdapter(qaModels);
        binding.listAnswerQuestion.setLayoutManager(new LinearLayoutManager(this));
        binding.listAnswerQuestion.setAdapter(answerAdapter);
    }


}
