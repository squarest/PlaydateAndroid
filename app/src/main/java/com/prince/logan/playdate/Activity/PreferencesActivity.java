package com.prince.logan.playdate.Activity;

import android.app.Activity;
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
            }
        });

        rangeHeight.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                txtHeightMax.setText(String.valueOf(maxValue)+"'");
                txtHeightMin.setText(String.valueOf(minValue)+"'");
            }
        });

        rangeMile.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {

            @Override
            public void valueChanged(Number value) {
                txtDistMax.setText(String.valueOf(value)+"miles");
            }
        });

        back.setOnClickListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radio_woman:
                Toast.makeText(this, "woman", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radio_man:
                Toast.makeText(this, "man", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radio_both:
                Toast.makeText(this, "both", Toast.LENGTH_SHORT).show();
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
        }
    }
}
