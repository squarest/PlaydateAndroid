package com.prince.logan.playdate.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.prince.logan.playdate.Activity.FAQActivity;
import com.prince.logan.playdate.Activity.QuestionActivity;
import com.prince.logan.playdate.Adapter.QaCateAdapter;
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
 * Created by Adib on 13-Apr-17.
 */

public class QAFragment extends Fragment{

    private View mRootView;
    public static ArrayList<QuestionModel> questionCateList = new ArrayList<QuestionModel>();
    QaCateAdapter qaCateAdapter;

    @Bind(R.id.list_qa_category)
    ListView listQaCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","MineFragment");
            mRootView = inflater.inflate(R.layout.fragment_qa,container,false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null){
            parent.removeView(mRootView);
        }

        ButterKnife.bind(this, mRootView);

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void initView() {
//        if (MainActivity.isFaq){
            gettingCategory();
//        }

        listQaCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                QuestionModel itemCate = questionCateList.get(i);
                Intent intentQuestion = new Intent(getContext(), QuestionActivity.class);
                intentQuestion.putExtra("cate_id", itemCate.getQa_cate_id());
                startActivity(intentQuestion);
            }
        });
    }

    private void gettingCategory() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<RequestModel> req = apiService.get_category();
        req.enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, retrofit2.Response<RequestModel> response) {
                RequestModel responseData = response.body();

                if (responseData.getResult() == 1){
                    questionCateList = responseData.getCateQuestion();
                    int resID = R.layout.adapter_qa_category;
                    qaCateAdapter = new QaCateAdapter(getActivity(), resID, questionCateList);
                    listQaCategory.setAdapter(qaCateAdapter);
                    qaCateAdapter.notifyDataSetChanged();
                }
                else{
                    showAlert("Alert", "Failed!");
                }
            }

            @Override
            public void onFailure(Call<RequestModel> call, Throwable t) {
                t.printStackTrace();
                showAlert("Alert", "Failed!");
            }
        });
    }

    public void showAlert(String title, String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

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
}
