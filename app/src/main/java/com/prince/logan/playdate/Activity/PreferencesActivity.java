package com.prince.logan.playdate.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.prince.logan.playdate.Global.Preferences;
import com.prince.logan.playdate.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * Created by PRINCE on 11/17/2017.
 */

public class PreferencesActivity extends Activity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener{

    @Bind(R.id.segmented_look_for)
    SegmentedGroup segmentLookFor;
    @Bind(R.id.txt_age_min)
    TextView txtAgeMin;
    @Bind(R.id.txt_age_max)
    TextView txtAgeMax;
    @Bind(R.id.rangeSeekbar_age)
    CrystalRangeSeekbar rangeAge;
    @Bind(R.id.txt_height_min)
    TextView txtHeightMin;
    @Bind(R.id.txt_height_max)
    TextView txtHeightMax;
    @Bind(R.id.rangeSeekbar_height)
    CrystalRangeSeekbar rangeHeight;
    @Bind(R.id.txt_distance_max)
    TextView txtDistMax;
    @Bind(R.id.rangeSeekbar_mile)
    CrystalSeekbar rangeMile;
    @Bind(R.id.img_preference_back)
    ImageView back;
    @Bind(R.id.txt_ethnicity)
    TextView txtEthnicity;
    @Bind(R.id.txt_religion)
    TextView txtReligion;
    @Bind(R.id.txt_education)
    TextView txtEducation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        ButterKnife.bind(this);

        setEvent();
    }

    private void setEvent() {
        segmentLookFor.setOnCheckedChangeListener(this);
        rangeAge.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                txtAgeMin.setText(String.valueOf(minValue));
                txtAgeMax.setText(String.valueOf(maxValue));
                Preferences.age_from = minValue.intValue();
                Preferences.age_to = maxValue.intValue();
            }
        });

        rangeHeight.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                txtHeightMax.setText(String.valueOf(maxValue)+"'");
                txtHeightMin.setText(String.valueOf(minValue)+"'");
                Preferences.height_from = (float) minValue;
                Preferences.height_to = (float) maxValue;
            }
        });

        rangeMile.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {

            @Override
            public void valueChanged(Number value) {
                txtDistMax.setText(String.valueOf(value)+"miles");
                Preferences.distance = value.intValue();
            }
        });

        back.setOnClickListener(this);
        txtEducation.setOnClickListener(this);
        txtReligion.setOnClickListener(this);
        txtEthnicity.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radio_woman:
                Preferences.looking = 1;
                break;
            case R.id.radio_man:
                Preferences.looking = 2;
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
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        txtAgeMin.setText(String.valueOf(Preferences.age_from));
        txtAgeMax.setText(String.valueOf(Preferences.age_to));
        rangeAge.setMinValue(Float.valueOf(Preferences.age_from));
        rangeAge.setMaxValue(Float.valueOf(Preferences.age_to));

        txtHeightMin.setText(String.valueOf(Preferences.height_from));
        txtHeightMax.setText(String.valueOf(Preferences.height_to));
        rangeHeight.setMinValue(Float.valueOf(Preferences.height_from));
        rangeHeight.setMaxValue(Float.valueOf(Preferences.height_to));

        txtDistMax.setText(String.valueOf(Preferences.distance));
        rangeMile.setMaxValue(Float.valueOf(Preferences.distance));

        if (Preferences.listEthnicity != null){
            int countEthnicity = 0;
            for (int i=0; i<Preferences.listEthnicity.size(); i++){
                if (Preferences.listEthnicity.get(i).getChecked() == 1){
                    countEthnicity++;
                }
            }
            if (countEthnicity == 9){
                txtEthnicity.setText("No Preference");
            }
            else{
                txtEthnicity.setText(String.valueOf(countEthnicity)+" ethnicities selected");
            }
        }
        if (Preferences.listReligion != null){
            int countReligion = 0;
            for (int i=0; i<Preferences.listReligion.size(); i++){
                if (Preferences.listReligion.get(i).getChecked() == 1){
                    countReligion++;
                }
            }
            if (countReligion == 10){
                txtReligion.setText("No Preference");
            }else{
                txtReligion.setText(String.valueOf(countReligion)+" religions selected");
            }
        }
        if (Preferences.listEducation != null){
            int countEducation = 0;
            for (int i=0; i<Preferences.listEducation.size(); i++){
                if (Preferences.listEducation.get(i).getChecked() == 1){
                    countEducation++;
                }
            }
            if (countEducation == 2){
                txtEducation.setText("No Preference");
            }
            else{
                txtEducation.setText(String.valueOf(countEducation)+" educations selected");
            }
        }
    }
}
