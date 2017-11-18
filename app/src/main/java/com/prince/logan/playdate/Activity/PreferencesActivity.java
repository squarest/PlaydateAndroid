package com.prince.logan.playdate.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

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

public class PreferencesActivity extends Activity {

    @Bind(R.id.segmented_look_for)
    SegmentedGroup segmentedGroup;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        ButterKnife.bind(this);

        setEvent();
    }

    private void setEvent() {
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
    }
}
