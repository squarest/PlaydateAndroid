package com.prince.logan.playdate.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.prince.logan.playdate.Global.Preferences;
import com.prince.logan.playdate.Interface.ApiClient;
import com.prince.logan.playdate.Interface.ApiInterface;
import com.prince.logan.playdate.Model.RequestModel;
import com.prince.logan.playdate.R;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBarDistance;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBarHeight;


import butterknife.Bind;
import butterknife.ButterKnife;
import info.hoang8f.android.segmented.SegmentedGroup;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by PRINCE on 11/17/2017.
 */

public class PreferencesActivity extends Activity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener{

    @Bind(R.id.segmented_look_for)
    SegmentedGroup segmentLookFor;

    @Bind(R.id.rangeSeekbar_age)
    RangeSeekBar rangeAge;

    @Bind(R.id.rangeSeekbar_height)
    RangeSeekBarHeight rangeHeight;

    @Bind(R.id.rangeSeekbar_mile)
    RangeSeekBarDistance rangeMile;
    @Bind(R.id.img_preference_back)
    ImageView back;
    @Bind(R.id.txt_ethnicity)
    TextView txtEthnicity;
    @Bind(R.id.txt_religion)
    TextView txtReligion;
    @Bind(R.id.txt_education)
    TextView txtEducation;
    @Bind(R.id.img_preference_save)
    ImageView imgPreferSave;
    @Bind(R.id.radio_man)
    RadioButton ra_man;
    @Bind(R.id.radio_woman)
    RadioButton ra_woman;
    @Bind(R.id.radio_not_binary)
    RadioButton ra_non;
    @Bind(R.id.radio_both)
    RadioButton ra_both;

    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        ButterKnife.bind(this);

        context=this;
    }

    private void setEvent() {
        segmentLookFor.setOnCheckedChangeListener(this);
        imgPreferSave.setOnClickListener(this);
        back.setOnClickListener(this);
        txtEducation.setOnClickListener(this);
        txtReligion.setOnClickListener(this);
        txtEthnicity.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radio_woman:
                Preferences.looking = 2;
                break;
            case R.id.radio_man:
                Preferences.looking = 1;
                break;
            case R.id.radio_both:
                Preferences.looking = 4;
                break;
            case R.id.radio_not_binary:
                Preferences.looking = 3;
                break;

            default:
                // Nothing to do
        }
    }

    private void savePreference() {
        Preferences.age_from = rangeAge.getSelectedMinValue().intValue();
        Preferences.age_to = rangeAge.getSelectedMaxValue().intValue();
        Preferences.height_from = rangeHeight.getSelectedMinValue().floatValue();
        Preferences.height_to = rangeHeight.getSelectedMaxValue().floatValue();
        Preferences.distance  = rangeMile.getSelectedMaxValue().intValue();

        final ProgressDialog loading = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        loading.setIndeterminate(true);
        loading.setMessage("Please wait...");
        loading.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<RequestModel> req = apiService.update_setting(MainActivity.userProfile.get_firebase_id(), Preferences.looking, Preferences.age_from,
                                Preferences.age_to, Preferences.height_from, Preferences.height_to, Preferences.distance, Preferences.listEthnicity,
                                Preferences.listReligion, Preferences.listEducation);
        req.enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, retrofit2.Response<RequestModel> response) {
                RequestModel responseData = response.body();
                if (responseData.getResult() == 1){
                    Toast.makeText(getApplicationContext(), "Update Success!", Toast.LENGTH_LONG).show();
                    loading.dismiss();
                    ((Activity)context).finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Update failed! Try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RequestModel> call, Throwable t) {
                t.printStackTrace();
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Update failed! Try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_preference_back:
                this.finish();
                break;
            case R.id.txt_religion:
                Intent religionIntent = new Intent(PreferencesActivity.this, PreferenceItemActivity.class);
                religionIntent.putExtra("item", 2);
                startActivity(religionIntent);
                break;
            case R.id.txt_education:
                Intent educationIntent = new Intent(PreferencesActivity.this, PreferenceItemActivity.class);
                educationIntent.putExtra("item", 3);
                startActivity(educationIntent);
                break;
            case R.id.txt_ethnicity:
                Intent ethnicityIntent = new Intent(PreferencesActivity.this, PreferenceItemActivity.class);
                ethnicityIntent.putExtra("item", 1);
                startActivity(ethnicityIntent);
                break;
            case R.id.img_preference_save:
                savePreference();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        setEvent();
        init_data();
    }

    private void init_data() {

        rangeAge.setRangeValues(0, 100);
        rangeHeight.setRangeValues(5.5, 6.9);
        rangeMile.setRangeValues(0,100);

//        txtAgeMin.setText(String.valueOf(Preferences.age_from));
//        txtAgeMax.setText(String.valueOf(Preferences.age_to));
        rangeAge.setSelectedMinValue(Preferences.age_from);
        rangeAge.setSelectedMaxValue(Preferences.age_to);

        switch (Preferences.looking){
            case 1:
                ra_man.setChecked(true);
                Preferences.looking = 1;
                break;
            case 2:
                ra_woman.setChecked(true);
                Preferences.looking = 2;
                break;
            case 3:
                ra_non.setChecked(true);
                Preferences.looking = 3;
                break;
            case 4:
                ra_both.setChecked(true);
                Preferences.looking = 4;
                break;
            default:
                ra_both.setChecked(true);
                Preferences.looking = 4;
                break;
        }

//        txtHeightMin.setText(Preferences.height_from+"'");
//        txtHeightMax.setText(Preferences.height_to+"'");
        rangeHeight.setSelectedMinValue(Preferences.height_from);
        rangeHeight.setSelectedMaxValue(Preferences.height_to);

//        txtDistMax.setText(String.valueOf(Preferences.distance)+"miles");

        rangeMile.setSelectedMaxValue(Preferences.distance);

        if (Preferences.listEthnicity.equals("-1")){
            txtEthnicity.setText("No Preference");
        }
        else{
            String[] ethnicity = Preferences.listEthnicity.split(",");
            txtEthnicity.setText(String.valueOf(ethnicity.length)+" enthnicities selected");
        }
        if (Preferences.listReligion.equals("-1")){
            txtReligion.setText("No Preference");
        }
        else{
            String[] religion = Preferences.listReligion.split(",");
            txtReligion.setText(String.valueOf(religion.length)+" religions selected");
        }
        if (Preferences.listEducation.equals("-1")){
            txtEducation.setText("No Preference");
        }
        else{
            String[] education = Preferences.listEducation.split(",");
            txtEducation.setText(String.valueOf(education.length)+" educations selected");
        }
    }
}
