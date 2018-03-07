package com.prince.logan.playdate.preference;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.prince.logan.playdate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PRINCE on 12/4/2017.
 */

public class EditProfileActivity extends Activity implements View.OnClickListener{

    TextView pEthnicity;
    TextView pEducation;
    TextView pReligion;
    EditText pAge;
    EditText pHeight;
    TextView pName;
    TextView pSex;
    @BindView(R.id.img_edit_profile_back)
    ImageView back;
    @BindView(R.id.img_edit_profile)
    ImageView imgProfileImage;
    @BindView(R.id.img_edit_profile_save)
    ImageView btnUpdate;
    @BindView(R.id.pHeight)
    TextView txtPheight;

    int indexEthnicity;
    int indexEducation;
    int indexReligion;

    int indexHeight;

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
//        initData();
        setEvent();
    }

    private void setEvent() {
        btnUpdate.setOnClickListener(this);
        back.setOnClickListener(this);
        txtPheight.setOnClickListener(this);
        pEducation.setOnClickListener(this);
        pEthnicity.setOnClickListener(this);
        pReligion.setOnClickListener(this);
    }

    private void initView() {

        pEthnicity = findViewById(R.id.spinner_edit_ethnicity);
        pReligion = findViewById(R.id.spinner_edit_religion);
        pEducation = findViewById(R.id.spinner_edit_education);
        pName = findViewById(R.id.txt_edit_Name);
        pSex = findViewById(R.id.txt_edit_sex);
        pAge = findViewById(R.id.ed_edit_age);
        pHeight = findViewById(R.id.ed_edit_height);
    }

//    private void initData() {
//
//        context = this;
//        indexHeight = 0;
//
//        if(MainActivity.userProfile.getUser_ethnicity() == -1){
//            pEthnicity.setText("None Selected");
//            indexEthnicity = -1;
//        }
//        else{
//            pEthnicity.setText(arrayEthnicity[MainActivity.userProfile.getUser_ethnicity()]);
//            indexEthnicity = MainActivity.userProfile.getUser_ethnicity();
//        }
//
//        if(MainActivity.userProfile.getUser_education() == -1){
//            pEducation.setText("None Selected");
//            indexEducation = -1;
//        }
//        else{
//            pEducation.setText(arrayEthnicity[MainActivity.userProfile.getUser_education()]);
//            indexEducation = MainActivity.userProfile.getUser_education();
//        }
//
//        if(MainActivity.userProfile.getUser_religion() == -1){
//            pReligion.setText("None Selected");
//            indexReligion = -1;
//        }
//        else{
//            pReligion.setText(arrayEthnicity[MainActivity.userProfile.getUser_religion()]);
//            indexReligion = MainActivity.userProfile.getUser_religion();
//        }
//
//        pName.setText(MainActivity.userProfile.get_user_full_name());
//        pSex.setText(MainActivity.userProfile.get_user_gender());
//        if (MainActivity.userProfile.getUser_age() == 0){
//            pAge.setText("");
//        }
//        else{
//            pAge.setText(String.valueOf(MainActivity.userProfile.getUser_age()));
//        }
//        if (MainActivity.userProfile.getHeight_option() == 0){
//            txtPheight.setText("Feets");
//            indexHeight = 0;
//        }
//        else{
//            txtPheight.setText("Inches");
//            indexHeight = 1;
//        }
//        pHeight.setText(String.valueOf(MainActivity.userProfile.getUser_height()));
//
//        Picasso.with(this)
//                .load(MainActivity.userProfile.get_user_avatar())
//                .placeholder(R.drawable.user) // optional
//                .error(R.drawable.user)         // optional
//                .into(imgProfileImage);
//
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_edit_profile_back:
                this.finish();
                break;
            case R.id.img_edit_profile_save:
                updateUser();
                break;
            case R.id.pHeight:
                final CharSequence[] items = {"Feets", "Inches"};

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setTitle("Select");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            indexHeight=0;
                            txtPheight.setText(items[item]);
                        } else if (item == 1) {
                            indexHeight=1;
                            txtPheight.setText(items[item]);
                        }
                    }
                });
                builder.show();
                break;

            case R.id.spinner_edit_ethnicity:
                android.app.AlertDialog.Builder builderEthnicity = new android.app.AlertDialog.Builder(this);
                builderEthnicity.setTitle("Select");
                builderEthnicity.setItems(arrayEthnicity, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        pEthnicity.setText(arrayEthnicity[item]);
                        indexEthnicity = item;
                    }
                });
                builderEthnicity.show();
                break;

            case R.id.spinner_edit_education:
                android.app.AlertDialog.Builder builderEducation = new android.app.AlertDialog.Builder(this);
                builderEducation.setTitle("Select");
                builderEducation.setItems(arrayEducation, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        pEducation.setText(arrayEducation[item]);
                        indexEducation = item;
                    }
                });
                builderEducation.show();
                break;

            case R.id.spinner_edit_religion:
                android.app.AlertDialog.Builder builderReligion = new android.app.AlertDialog.Builder(this);
                builderReligion.setTitle("Select");
                builderReligion.setItems(arrayReligion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        pReligion.setText(arrayReligion[item]);
                        indexReligion = item;
                    }
                });
                builderReligion.show();
                break;
        }
    }

    private void updateUser() {
        if(!validate()){
            return;
        }
//        updateUserProfile();
    }

//    private void updateUserProfile() {
//        String firebase_id = MainActivity.userProfile.get_firebase_id();
//        String user_age = pAge.getText().toString();
//        String user_height = pHeight.getText().toString();
//        int user_ethnicity = indexEthnicity;
//        int user_religion = indexReligion;
//        int user_education =indexEducation;
//
//        final ProgressDialog loading = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
//        loading.setIndeterminate(true);
//        loading.setMessage("Please wait...");
//        loading.show();
//
//        API apiService = ApiClient.getApi().create(API.class);
//
//        Call<RequestModel> req = apiService.edit_user(firebase_id, Integer.valueOf(user_age), user_height, user_ethnicity, user_religion, user_education, indexHeight);
//        req.enqueue(new Callback<RequestModel>() {
//            @Override
//            public void onResponse(Call<RequestModel> call, retrofit2.Response<RequestModel> response) {
//                RequestModel responseData = response.body();
//                loading.dismiss();
//                if (responseData.getResult() == 1){
//                    MainActivity.userProfile = responseData.getUser();
//                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
//                    ((Activity)context).finish();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RequestModel> call, Throwable t) {
//                t.printStackTrace();
//                loading.dismiss();
////                showAlert("Alert", "Failed!");
//            }
//        });
//    }

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
        if (pEthnicity.getText().toString().equals("None Selected")){
            Toast.makeText(this, "Select Ethnicity", Toast.LENGTH_LONG).show();
            return false;
        }
        if (pReligion.getText().toString().equals("None Selected")){
            Toast.makeText(this, "Select Religion", Toast.LENGTH_LONG).show();
            return false;
        }
        if(pEducation.getText().toString().equals("None Selected")){
            Toast.makeText(this, "Select Education", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
