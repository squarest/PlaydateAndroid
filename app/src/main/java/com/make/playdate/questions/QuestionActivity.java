package com.make.playdate.questions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daprlabs.cardstack.SwipeDeck;
import com.make.playdate.R;
import com.make.playdate.entities.QuestionModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PRINCE on 11/14/2017.
 */

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private SwipeDeck cardStack;
    private Context context = this;

    private SwipeDeckAdapter adapter;

    private int questionCateId;
    private int questionSubCateId;
    private int cardIndex;
    private int isAnswered;
    private ArrayList<QuestionModel> qaArrayList = new ArrayList<QuestionModel>();

    StringBuilder questions = new StringBuilder();

    @BindView(R.id.img_questions_back)
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);

        back.setOnClickListener(this);
        cardIndex = 0;
//        getQuestions();
    }

//    private void getQuestions() {
//        final ProgressDialog loading = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
//        loading.setIndeterminate(true);
//        loading.setMessage("Please wait...");
//        loading.show();
//
//        Intent intent = this.getIntent();
//        Bundle bundle = intent.getExtras();
//        if (bundle != null) {
//            questionCateId = bundle.getInt("cate_id");
//            questionSubCateId = bundle.getInt("sub_cate_id");
//            isAnswered = bundle.getInt("answer_id");
//        }
//
//        API apiService = ApiClient.getApi().create(API.class);
//
//        Call<RequestModel> req = apiService.get_questions(questionSubCateId);
//        req.enqueue(new Callback<RequestModel>() {
//            @Override
//            public void onResponse(Call<ResponseModel> call, retrofit2.Response<ResponseModel> response) {
//                RequestModel responseData = response.body();
//
//                loading.dismiss();
//                if (responseData.getResult() == 1){
//                    qaArrayList = responseData.getCateQuestion();
//                    initView();
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
        alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    private void initView(){
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        cardStack.setHardwareAccelerationEnabled(true);

        adapter = new SwipeDeckAdapter(this, R.layout.card_item, qaArrayList);
        cardStack.setAdapter(adapter);

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
                cardIndex++;
            }

            @Override
            public void cardSwipedRight(int position) {
                Log.i("MainActivity", "card was swiped right, position in adapter: " + position);

                questions.append(qaArrayList.get(position).getId()+",");
                cardIndex++;
            }

            @Override
            public void cardsDepleted() {
                StringBuilder questionString = questions;
                if(questionString.toString().equals("")){
                    showAlert("", "You've never answered! Please try again");
                    ((Activity)context).finish();
                }
                else{
                    showQuestionAlert("", "You've answered all questions. Will you save this answers?", questionString);
                }
            }

            @Override
            public void cardActionDown() {
                Log.i(TAG, "cardActionDown");
            }

            @Override
            public void cardActionUp() {
                Log.i(TAG, "cardActionUp");
            }

        });
        cardStack.setLeftImage(R.id.left_image);
        cardStack.setRightImage(R.id.right_image);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardLeft(180);
            }
        });
        Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopCardRight(180);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_swipe_deck, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_questions_back:
                this.finish();
                break;
        }
    }

    public void showQuestionAlert(String title, String msg, final StringBuilder answers){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Dialog Title
        alertDialog.setTitle(title);
        // Dialog Message
        alertDialog.setMessage(msg);
        // on pressing cancel button
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
//                if (isAnswered >0){
//                    updateAnswers(answers, isAnswered);
//                }
//                else{
//                    savingAnswers(answers);
//                }
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

//    private void updateAnswers(StringBuilder answers, int answer_id) {
//        final ProgressDialog loading = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
//        loading.setIndeterminate(true);
//        loading.setMessage("Updating answers...");
//        loading.show();
//
//        API apiService = ApiClient.getApi().create(API.class);
//
//        String strAnswers = answers.toString().substring(0, answers.toString().length() - 1);
//
//        Call<RequestModel> req = apiService.update_answers(answer_id, strAnswers);
//        req.enqueue(new Callback<RequestModel>() {
//            @Override
//            public void onResponse(Call<ResponseModel> call, retrofit2.Response<ResponseModel> response) {
//                RequestModel responseData = response.body();
//
//                loading.dismiss();
//                if (responseData.getResult() == 1){
//                    ((Activity)context).finish();
//                }
//                else{
//                    showAlert("Alert", responseData.getMsg());
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

//    private void savingAnswers(StringBuilder answers) {
//        final ProgressDialog loading = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
//        loading.setIndeterminate(true);
//        loading.setMessage("Saving answers...");
//        loading.show();
//
//        API apiService = ApiClient.getApi().create(API.class);
//        String user_id = MainActivity.userFirebaseID;
//        if(user_id.equals("") || user_id == null){
//            user_id = MainActivity.userProfile.get_firebase_id();
//        }
//
//        String strAnswers = answers.toString().substring(0, answers.toString().length() - 1);
//
//
//        Call<RequestModel> req = apiService.saving_answers(questionCateId, questionSubCateId, user_id, strAnswers);
//        req.enqueue(new Callback<RequestModel>() {
//            @Override
//            public void onResponse(Call<ResponseModel> call, retrofit2.Response<ResponseModel> response) {
//                RequestModel responseData = response.body();
//
//                loading.dismiss();
//                if (responseData.getResult() == 1){
//                    ((Activity)context).finish();
//                }
//                else{
//                    showAlert("Alert", responseData.getMsg());
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

    public class SwipeDeckAdapter extends ArrayAdapter<QuestionModel> {

        private ArrayList<QuestionModel> data;
        private Context context;
        private QuestionModel questions;
        private int mResource;

        public SwipeDeckAdapter(Context context, int itemResourceId,
                                ArrayList<QuestionModel> list) {
            super(context, itemResourceId, list);
            this.context = context;
            this.mResource = itemResourceId;
            this.data = list;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public QuestionModel getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                LayoutInflater inflater = getLayoutInflater();
                // normally use a viewholder
                v = inflater.inflate(R.layout.card_item, parent, false);
            }

            questions = data.get(position);

            TextView txt_cate_question = (TextView) v.findViewById(R.id.card_cate_question);
            TextView txt_question = (TextView) v.findViewById(R.id.card_txt_question);
            String item = questions.getQuestions();
            txt_question.setText(item);
            int questionIndex = position+1;
            txt_cate_question.setText(questions.getSub_cate()+"- Question "+questionIndex);

            return v;
        }
    }
}
