package com.prince.logan.playdate.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.prince.logan.playdate.Interface.ApiClient;
import com.prince.logan.playdate.Interface.ApiInterface;
import com.prince.logan.playdate.Model.RequestModel;
import com.prince.logan.playdate.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by PRINCE on 12/4/2017.
 */

public class EditProfileActivity extends Activity implements View.OnClickListener{

    MaterialSpinner pEthnicity;
    MaterialSpinner pEducation;
    MaterialSpinner pReligion;
    EditText pAge;
    EditText pHeight;
    TextView pName;
    TextView pSex;
    @Bind(R.id.img_edit_profile_back)
    ImageView back;
    @Bind(R.id.img_edit_profile)
    ImageView imgProfileImage;
    @Bind(R.id.img_edit_profile_save)
    ImageView btnUpdate;

    String[] arrayEthnicity = {"No Preference","White/ Caucasian","Asian","South Asian","Hispanic/ Latino","Pacific Islander","Middle Eastern/ Arab","Black/ African-descent","Native American","Other"};
    String[] arrayReligion = {"No Preference","Christian","Catholic","Jewish","Muslim","Unitarian/ Universalist","Buddhist","Hindu","Agnostic","Atheist","Other"};
    String[] arrayEducation = {"No Preference","High Selective","Selective"};

    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        initView();
        initData();
        setEvent();
    }

    private void setEvent() {
        btnUpdate.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void initView() {

        pEthnicity = (MaterialSpinner) findViewById(R.id.spinner_edit_ethnicity);
        pReligion = (MaterialSpinner) findViewById(R.id.spinner_edit_religion);
        pEducation = (MaterialSpinner) findViewById(R.id.spinner_edit_education);
        pName = (TextView)findViewById(R.id.txt_edit_Name);
        pSex = (TextView)findViewById(R.id.txt_edit_sex);
        pAge = (EditText)findViewById(R.id.ed_edit_age);
        pHeight = (EditText)findViewById(R.id.ed_edit_height);
    }

    private void initData() {

        context = this;

        pEducation.setItems(arrayEducation);
        pReligion.setItems(arrayReligion);
        pEthnicity.setItems(arrayEthnicity);

        pEducation.setText(MainActivity.userProfile.getUser_education());
        pReligion.setText(MainActivity.userProfile.getUser_religion());
        pEthnicity.setText(MainActivity.userProfile.getUser_ethnicity());

        pName.setText(MainActivity.userProfile.get_user_full_name());
        pSex.setText(MainActivity.userProfile.get_user_gender());
        if (MainActivity.userProfile.getUser_age() == 0){
            pAge.setText("");
        }
        else{
            pAge.setText(String.valueOf(MainActivity.userProfile.getUser_age()));
        }
        pHeight.setText(String.valueOf(MainActivity.userProfile.getUser_height()));

        Picasso.with(this)
                .load(MainActivity.userProfile.get_user_avatar())
                .placeholder(R.drawable.user) // optional
                .error(R.drawable.user)         // optional
                .into(imgProfileImage);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_edit_profile_back:
                this.finish();
                break;
            case R.id.img_edit_profile_save:
                updateUser();
                break;
        }
    }

    private void updateUser() {
        if(!validate()){
            return;
        }
        updateUserProfile();
    }

    private void updateUserProfile() {
        String firebase_id = MainActivity.userProfile.get_firebase_id();
        String user_age = pAge.getText().toString();
        String user_height = pHeight.getText().toString();
        String user_ethnicity = pEthnicity.getText().toString();
        String user_religion = pReligion.getText().toString();
        String user_education = pEducation.getText().toString();

        final ProgressDialog loading = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        loading.setIndeterminate(true);
        loading.setMessage("Please wait...");
        loading.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<RequestModel> req = apiService.edit_user(firebase_id, Integer.valueOf(user_age), user_height, user_ethnicity, user_religion, user_education);
        req.enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, retrofit2.Response<RequestModel> response) {
                RequestModel responseData = response.body();
                loading.dismiss();
                if (responseData.getResult() == 1){
                    MainActivity.userProfile = responseData.getUser();
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                    ((Activity)context).finish();
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

    private boolean validate() {
        boolean valid = true;
        if (pAge.getText().toString().equals("0") || pAge.getText().toString().equals("")){
            Toast.makeText(this, "Please enter your age", Toast.LENGTH_LONG).show();
            return false;
        }
        if(pHeight.getText().toString().equals("0.0") || pHeight.getText().toString().equals("0") || pHeight.getText().toString().equals("")){
            Toast.makeText(this, "Please enter your height", Toast.LENGTH_LONG).show();
            return false;
        }
        if (pEthnicity.getText().toString().equals("No Preference")){
            Toast.makeText(this, "Select Ethnicity", Toast.LENGTH_LONG).show();
            return false;
        }
        if (pReligion.getText().toString().equals("No Preference")){
            Toast.makeText(this, "Select Religion", Toast.LENGTH_LONG).show();
            return false;
        }
        if(pEducation.getText().toString().equals("No Preference")){
            Toast.makeText(this, "Select Education", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
