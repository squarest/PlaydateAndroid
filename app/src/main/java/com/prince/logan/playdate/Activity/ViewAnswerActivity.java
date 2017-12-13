package com.prince.logan.playdate.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.prince.logan.playdate.Adapter.AnswerAdapter;
import com.prince.logan.playdate.Adapter.SubCateAdapter;
import com.prince.logan.playdate.Interface.ApiClient;
import com.prince.logan.playdate.Interface.ApiInterface;
import com.prince.logan.playdate.Model.QuestionModel;
import com.prince.logan.playdate.Model.RequestModel;
import com.prince.logan.playdate.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by PRINCE on 11/28/2017.
 */

public class ViewAnswerActivity extends Activity implements View.OnClickListener{
    @Bind(R.id.img_answer_back)
    ImageView back;
    @Bind(R.id.list_answer_question)
    ListView listAnswerView;
    @Bind(R.id.txt_answer_title)
    TextView txtTitle;
    @Bind(R.id.btn_reanswer)
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
        gettingAnsweredQuestions();
    }

    private void gettingAnsweredQuestions() {
        final ProgressDialog loading = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        loading.setIndeterminate(true);
        loading.setMessage("Please wait...");
        loading.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<RequestModel> req = apiService.getting_answered_question(answerId);
        req.enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, retrofit2.Response<RequestModel> response) {
                RequestModel responseData = response.body();

                loading.dismiss();
                if (responseData.getResult() == 1){
                    arrayQuestion= responseData.getAnswers();

                    answerAdapter = new AnswerAdapter(getApplicationContext(), R.layout.adapter_playdate_detail, arrayQuestion);
                    listAnswerView.setAdapter(answerAdapter);
                    answerAdapter.notifyDataSetChanged();
                }
                else{
                    showAlert("Alert", "Failed!");
                }
            }

            @Override
            public void onFailure(Call<RequestModel> call, Throwable t) {
                t.printStackTrace();
                loading.dismiss();
                showAlert("Alert", "Failed!");
            }
        });
    }
    public void showAlert(String title, String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Dialog Title
        alertDialog.setTitle(title);
        // Dialog Message
        alertDialog.setMessage(msg);
        // on pressing cancel button
        alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
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
