package com.make.playdate.questions.presentation.questions;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.daprlabs.cardstack.SwipeDeck;
import com.make.playdate.R;
import com.make.playdate.base.BaseActivity;
import com.make.playdate.entities.QuestionModel;
import com.make.playdate.main.presentation.main.MainActivity;
import com.make.playdate.utils.DialogUtil;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by PRINCE on 11/14/2017.
 */

public class QuestionActivity extends BaseActivity implements QuestionView {

    @InjectPresenter
    public QuestionsPresenter presenter;
    private String answers = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ImageView back = findViewById(R.id.back_button);
        back.setOnClickListener(view -> onBackPressed());
        presenter.viewCreated();
    }

    public void showQuestionAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("");
        alertDialog.setCancelable(false);
        alertDialog.setMessage("You've answered all questions. Will you save this answers?");
        alertDialog.setPositiveButton("Yes", (dialog, which) -> presenter.saveAnswersButtonClicked(answers));
        alertDialog.setNegativeButton("No", (dialog, i) -> {
            dialog.cancel();
            finish();
        });
        alertDialog.show();
    }

    @Override
    public void setQuestions(ArrayList<QuestionModel> qaArrayList) {
        SwipeDeck cardStack = findViewById(R.id.swipe_deck);
        cardStack.setHardwareAccelerationEnabled(true);
        Collections.shuffle(qaArrayList);
        SwipeDeckAdapter adapter = new SwipeDeckAdapter(this, R.layout.card_item, qaArrayList);
        cardStack.setAdapter(adapter);

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
            }

            @Override
            public void cardSwipedRight(int position) {
                answers += qaArrayList.get(position).getId() + ",";
            }

            @Override
            public void cardsDepleted() {
                if (answers.equals("")) {
                    DialogUtil dialogUtil = new DialogUtil(QuestionActivity.this);
                    dialogUtil.getAlertDialog("", "You've never answered! Please try again");
                    finish();
                } else {
                    showQuestionAlert();
                }
            }

            @Override
            public void cardActionDown() {
            }

            @Override
            public void cardActionUp() {

            }

        });
        cardStack.setLeftImage(R.id.left_image);
        cardStack.setRightImage(R.id.right_image);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(v -> cardStack.swipeTopCardLeft(180));
        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(v -> cardStack.swipeTopCardRight(180));

    }

    @Override
    public void snowMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
