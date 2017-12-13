package com.prince.logan.playdate.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

    Spinner pEthnicity;
    Spinner pEducation;
    Spinner pReligion;
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
    @Bind(R.id.pHeight)
    TextView txtPheight;

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
        initData();
        setEvent();
    }

    private void setEvent() {
        btnUpdate.setOnClickListener(this);
        back.setOnClickListener(this);
        txtPheight.setOnClickListener(this);
    }

    private void initView() {

        pEthnicity = (Spinner) findViewById(R.id.spinner_edit_ethnicity);
        pReligion = (Spinner) findViewById(R.id.spinner_edit_religion);
        pEducation = (Spinner) findViewById(R.id.spinner_edit_education);
        pName = (TextView)findViewById(R.id.txt_edit_Name);
        pSex = (TextView)findViewById(R.id.txt_edit_sex);
        pAge = (EditText)findViewById(R.id.ed_edit_age);
        pHeight = (EditText)findViewById(R.id.ed_edit_height);
    }

    private void initData() {

        context = this;
        indexHeight = 0;

        ArrayAdapter<String> educationAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayEducation);
        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pEducation.setAdapter(educationAdapter);

        ArrayAdapter<String> ethnicityAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayEthnicity);
        ethnicityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pEthnicity.setAdapter(ethnicityAdapter);

        ArrayAdapter<String> religionAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayReligion);
        religionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pReligion.setAdapter(religionAdapter);

        for (int i=0; i<arrayEthnicity.length; i++){
            if (arrayEthnicity[i].equals(MainActivity.userProfile.getUser_ethnicity())){
                pEthnicity.setSelection(i);
            }
        }

        for (int i=0; i<arrayReligion.length; i++){
            if (arrayReligion[i].equals(MainActivity.userProfile.getUser_religion())){
                pReligion.setSelection(i);
            }
        }

        for (int i=0; i<arrayEducation.length; i++){
            if (arrayEducation[i].equals(MainActivity.userProfile.getUser_education())){
                pEducation.setSelection(i);
            }
        }

        pName.setText(MainActivity.userProfile.get_user_full_name());
        pSex.setText(MainActivity.userProfile.get_user_gender());
        if (MainActivity.userProfile.getUser_age() == 0){
            pAge.setText("");
        }
        else{
            pAge.setText(String.valueOf(MainActivity.userProfile.getUser_age()));
        }
        if (MainActivity.userProfile.getHeight_option() == 0){
            txtPheight.setText("Feets");
            indexHeight = 0;
        }
        else{
            txtPheight.setText("Inches");
            indexHeight = 1;
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
        String user_ethnicity = pEthnicity.getSelectedItem().toString();
        String user_religion = pReligion.getSelectedItem().toString();
        String user_education = pEducation.getSelectedItem().toString();

        final ProgressDialog loading = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        loading.setIndeterminate(true);
        loading.setMessage("Please wait...");
        loading.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<RequestModel> req = apiService.edit_user(firebase_id, Integer.valueOf(user_age), user_height, user_ethnicity, user_religion, user_education, indexHeight);
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
        if (pEthnicity.getSelectedItem().toString().equals("No Preference")){
            Toast.makeText(this, "Select Ethnicity", Toast.LENGTH_LONG).show();
            return false;
        }
        if (pReligion.getSelectedItem().toString().equals("No Preference")){
            Toast.makeText(this, "Select Religion", Toast.LENGTH_LONG).show();
            return false;
        }
        if(pEducation.getSelectedItem().toString().equals("No Preference")){
            Toast.makeText(this, "Select Education", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
