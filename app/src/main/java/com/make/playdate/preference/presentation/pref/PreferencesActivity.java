package com.make.playdate.preference.presentation.pref;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.make.playdate.R;
import com.make.playdate.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * Created by PRINCE on 11/17/2017.
 */

public class PreferencesActivity extends BaseActivity {

    @BindView(R.id.segmented_look_for)
    SegmentedGroup segmentLookFor;



//    @Bind(R.id.rangeSeekbar_height)
//    RangeSeekBarHeight rangeHeight;
//
//    @Bind(R.id.rangeSeekbar_mile)
//    RangeSeekBarDistance rangeMile;

    TextView txtEducation;
    @BindView(R.id.img_preference_save)
    ImageView imgPreferSave;
    @BindView(R.id.radio_man)
    RadioButton ra_man;
    @BindView(R.id.radio_woman)
    RadioButton ra_woman;
    @BindView(R.id.radio_not_binary)
    RadioButton ra_non;
    @BindView(R.id.radio_both)
    RadioButton ra_both;

    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        ButterKnife.bind(this);

        context=this;
    }




//        switch (checkedId) {
//            case R.id.radio_woman:
//                Preferences.looking = 2;
//                break;
//            case R.id.radio_man:
//                Preferences.looking = 1;
//                break;
//            case R.id.radio_both:
//                Preferences.looking = 4;
//                break;
//            case R.id.radio_not_binary:
//                Preferences.looking = 3;
//                break;
//
//            default:
//                // Nothing to do
//        }
    }

//    private void savePreference() {
//        Preferences.age_from = rangeAge.getSelectedMinValue().intValue();
//        Preferences.age_to = rangeAge.getSelectedMaxValue().intValue();
//        Preferences.height_from = rangeHeight.getSelectedMinValue().floatValue();
//        Preferences.height_to = rangeHeight.getSelectedMaxValue().floatValue();
//        Preferences.distance  = rangeMile.getSelectedMaxValue().intValue();
//
//        final ProgressDialog loading = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
//        loading.setIndeterminate(true);
//        loading.setMessage("Please wait...");
//        loading.show();
//
//        API apiService = ApiClient.getApi().create(API.class);
//
//        Call<RequestModel> req = apiService.update_setting(MainActivity.userProfile.get_firebase_id(), Preferences.looking, Preferences.age_from,
//                                Preferences.age_to, Preferences.height_from, Preferences.height_to, Preferences.distance, Preferences.listEthnicity,
//                                Preferences.listReligion, Preferences.listEducation);
//        req.enqueue(new Callback<RequestModel>() {
//            @Override
//            public void onResponse(Call<ResponseModel> call, retrofit2.Response<ResponseModel> response) {
//                RequestModel responseData = response.body();
//                if (responseData.getResult() == 1){
//                    Toast.makeText(getApplicationContext(), "Update Success!", Toast.LENGTH_LONG).show();
//                    loading.dismiss();
//                    ((Activity)context).finish();
//                }
//                else{
//                    Toast.makeText(getApplicationContext(), "Update failed! Try again", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RequestModel> call, Throwable t) {
//                t.printStackTrace();
//                loading.dismiss();
//                Toast.makeText(getApplicationContext(), "Update failed! Try again", Toast.LENGTH_LONG).show();
//            }
//        });
//    }





//    private void init_data() {
//
//        rangeAge.setRangeValues(0, 100);
//        rangeHeight.setRangeValues(5.5, 6.9);
//        rangeMile.setRangeValues(0,100);
//
////        txtAgeMin.setText(String.valueOf(Preferences.age_from));
////        txtAgeMax.setText(String.valueOf(Preferences.age_to));
//        rangeAge.setSelectedMinValue(Preferences.age_from);
//        rangeAge.setSelectedMaxValue(Preferences.age_to);
//
//        switch (Preferences.looking){
//            case 1:
//                ra_man.setChecked(true);
//                Preferences.looking = 1;
//                break;
//            case 2:
//                ra_woman.setChecked(true);
//                Preferences.looking = 2;
//                break;
//            case 3:
//                ra_non.setChecked(true);
//                Preferences.looking = 3;
//                break;
//            case 4:
//                ra_both.setChecked(true);
//                Preferences.looking = 4;
//                break;
//            default:
//                ra_both.setChecked(true);
//                Preferences.looking = 4;
//                break;
//        }
//
////        txtHeightMin.setText(Preferences.height_from+"'");
////        txtHeightMax.setText(Preferences.height_to+"'");
//        rangeHeight.setSelectedMinValue(Preferences.height_from);
//        rangeHeight.setSelectedMaxValue(Preferences.height_to);
//
////        txtDistMax.setText(String.valueOf(Preferences.distance)+"miles");
//
//        rangeMile.setSelectedMaxValue(Preferences.distance);
//
//        if (Preferences.listEthnicity.equals("-1")){
//            txtEthnicity.setText("No Preference");
//        }
//        else{
//            String[] ethnicity = Preferences.listEthnicity.split(",");
//            txtEthnicity.setText(String.valueOf(ethnicity.length)+" enthnicities selected");
//        }
//        if (Preferences.listReligion.equals("-1")){
//            txtReligion.setText("No Preference");
//        }
//        else{
//            String[] religion = Preferences.listReligion.split(",");
//            txtReligion.setText(String.valueOf(religion.length)+" religions selected");
//        }
//        if (Preferences.listEducation.equals("-1")){
//            txtEducation.setText("No Preference");
//        }
//        else{
//            String[] education = Preferences.listEducation.split(",");
//            txtEducation.setText(String.valueOf(education.length)+" educations selected");
//        }
//    }

