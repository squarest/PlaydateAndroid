package com.prince.logan.playdate.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.prince.logan.playdate.Adapter.SubCateAdapter;
import com.prince.logan.playdate.Interface.ApiClient;
import com.prince.logan.playdate.Interface.ApiInterface;
import com.prince.logan.playdate.Model.RequestModel;
import com.prince.logan.playdate.Model.SubCateModel;
import com.prince.logan.playdate.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by PRINCE on 11/22/2017.
 */

public class QuestionSubCateActivity extends Activity{
    @Bind(R.id.img_sub_back)
    ImageView imgback;
    @Bind(R.id.txt_sub_cate)
    TextView txtSubTitle;
    @Bind(R.id.list_sub_cate)
    ListView listSubCate;

    Context context;
    int questionCateId;
    String questionTitle;
    ArrayList<SubCateModel> subCateList = new ArrayList<SubCateModel>();
    SubCateAdapter subAdapter;
    SubCateModel subCate = new SubCateModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cate);
        ButterKnife.bind(this);

        context = this;

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            questionCateId = bundle.getInt("cate_id");
            questionTitle = bundle.getString("cate_title");
            txtSubTitle.setText(questionTitle);
        }

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)context).finish();
            }
        });

        listSubCate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                subCate = subCateList.get(i);
                Intent questionIntent = new Intent(QuestionSubCateActivity.this, QuestionActivity.class);
                questionIntent.putExtra("cate_id", subCate.getSub_cate_id());
                startActivity(questionIntent);
            }
        });

        gettingSubCategories();
    }

    private void gettingSubCategories() {
        final ProgressDialog loading = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        loading.setIndeterminate(true);
        loading.setMessage("Please wait...");
        loading.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<RequestModel> req = apiService.get_sub_category(questionCateId);
        req.enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, retrofit2.Response<RequestModel> response) {
                RequestModel responseData = response.body();

                loading.dismiss();
                if (responseData.getResult() == 1){
                    subCateList= responseData.getSubCategory();

                    subAdapter = new SubCateAdapter(getApplicationContext(), R.layout.adapter_sub_cate, subCateList, questionTitle);
                    listSubCate.setAdapter(subAdapter);
                    subAdapter.notifyDataSetChanged();
                }
                else{
//                    showAlert("Alert", "Failed!");
                }
            }

            @Override
            public void onFailure(Call<RequestModel> call, Throwable t) {
                t.printStackTrace();
                loading.dismiss();
//                showAlert("Alert", "Failed!");
            }
        });
    }
}
