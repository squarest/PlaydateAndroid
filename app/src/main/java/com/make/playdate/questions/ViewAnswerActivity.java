package com.make.playdate.questions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.make.playdate.R;
import com.make.playdate.entities.QuestionModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PRINCE on 11/28/2017.
 */

public class ViewAnswerActivity extends Activity implements View.OnClickListener{
    @BindView(R.id.img_answer_back)
    ImageView back;
    @BindView(R.id.list_answer_question)
    ListView listAnswerView;
    @BindView(R.id.txt_answer_title)
    TextView txtTitle;
    @BindView(R.id.btn_reanswer)
    Button btnReanswer;

    int answerId;
    int cateId;
    int subCateId;
    ArrayList<QuestionModel> arrayQuestion = new ArrayList<QuestionModel>();
    AnswerAdapter answerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_answers);
        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            answerId = bundle.getInt("answer_id");

            txtTitle.setText(bundle.getString("sub_title"));
            cateId = bundle.getInt("cate_id");
            subCateId = bundle.getInt("sub_cate_id");
        }

        back.setOnClickListener(this);
        btnReanswer.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
//        gettingAnsweredQuestions();
    }

//    private void gettingAnsweredQuestions() {
//        final ProgressDialog loading = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
//        loading.setIndeterminate(true);
//        loading.setMessage("Please wait...");
//        loading.show();
//
//        API apiService = ApiClient.getApi().create(API.class);
//
//        Call<RequestModel> req = apiService.getting_answered_question(answerId);
//        req.enqueue(new Callback<RequestModel>() {
//            @Override
//            public void onResponse(Call<ResponseModel> call, retrofit2.Response<ResponseModel> response) {
//                RequestModel responseData = response.body();
//
//                loading.dismiss();
//                if (responseData.getResult() == 1){
//                    arrayQuestion= responseData.getAnswers();
//
//                    answerAdapter = new AnswerAdapter(getApplicationContext(), R.layout.adapter_playdate_detail, arrayQuestion);
//                    listAnswerView.setAdapter(answerAdapter);
//                    answerAdapter.notifyDataSetChanged();
//                }
//                else{
//                    showAlert("Alert", "Failed!");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RequestModel> call, Throwable t) {
//                t.printStackTrace();
//                loading.dismiss();
//                showAlert("Alert", "Failed!");
//            }
//        });
//    }
    public void showAlert(String title, String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Dialog Title
        alertDialog.setTitle(title);
        // Dialog Message
        alertDialog.setMessage(msg);
        // on pressing cancel button
        alertDialog.setNegativeButton("OK", (dialog, which) -> dialog.cancel());
        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_answer_back:
                this.finish();
                break;
            case R.id.btn_reanswer:
                Intent questionIntent = new Intent(ViewAnswerActivity.this, QuestionActivity.class);
                questionIntent.putExtra("sub_cate_id", subCateId);
                questionIntent.putExtra("cate_id", cateId);
                questionIntent.putExtra("answer_id", answerId);
                startActivity(questionIntent);
                break;
        }
    }
}
