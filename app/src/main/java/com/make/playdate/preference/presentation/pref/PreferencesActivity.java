package com.make.playdate.preference.presentation.pref;

import android.app.AlertDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.make.playdate.R;
import com.make.playdate.base.BaseActivity;
import com.make.playdate.databinding.ActivityPreferencesBinding;
import com.make.playdate.entities.UserModel;
import com.make.playdate.global.Constant;

/**
 * Created by PRINCE on 11/17/2017.
 */

public class PreferencesActivity extends BaseActivity implements PreferenceView {

    private ActivityPreferencesBinding binding;
    @InjectPresenter
    public PreferencePresenter presenter;
    private UserModel user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preferences);
        presenter.viewCreated();
        setEvents();
    }

    private void setEvents() {
        binding.backButton.setOnClickListener(view -> finish());
        binding.saveButton.setOnClickListener(view -> presenter.savePreferences(getPreferences()));
        binding.ageRange.setOnRangeSeekbarChangeListener((minAge, maxAge) ->
        {
            binding.miAge.setText(minAge.toString());
            binding.maxAge.setText(maxAge.toString());
        });
        binding.distanceSeekBar.setOnSeekbarChangeListener(distance -> binding.distanceValue.setText(distance.toString()));
        binding.ethnicity.setOnClickListener(view ->
        {
            AlertDialog.Builder builderEthnicity = new AlertDialog.Builder(this);
            builderEthnicity.setTitle("Select");
            builderEthnicity.setItems(Constant.arrayEthnicity, (dialog, item) -> {
                ((TextView) view).setText(Constant.arrayEthnicity[item]);
                user.setEthnicity(String.valueOf(item));

            });
            builderEthnicity.show();
        });
        binding.segmentedLookFor.setOnCheckedChangeListener((radioGroup, i) ->
        {
            int lookingFor = -1;
            switch (i) {
                case R.id.all:
                    lookingFor = 0;
                    break;
                case R.id.radio_female:
                    lookingFor = 1;
                    break;
                case R.id.radio_male:
                    lookingFor = 2;
                    break;
                case R.id.radio_not_binary:
                    lookingFor = 3;
                    break;
            }
            user.setLooking_for(lookingFor);
        });
    }

    @Override
    public void setPreferences(UserModel userModel) {
        user = userModel;
        String ethnicity;
        if (user.getEthnicity().equals("-1")) ethnicity = getString(R.string.none_selected);
        else ethnicity = Constant.arrayEthnicity[Integer.valueOf(user.getEthnicity())];
        binding.ethnicity.setText(ethnicity);
        binding.distanceValue.setText(String.valueOf(userModel.getDistance()));
        binding.ageRange.setMinStartValue((float) userModel.getAge_from())
                .setMaxStartValue((float) userModel.getAge_to()).apply();
        binding.miAge.setText(String.valueOf(userModel.getAge_from()));
        binding.maxAge.setText(String.valueOf(userModel.getAge_to()));
        binding.distanceSeekBar.setMinStartValue((float) userModel.getDistance()).apply();
        switch (userModel.getLooking_for()) {
            case 1:
                binding.radioFemale.setChecked(true);
                break;
            case 2:
                binding.radioMale.setChecked(true);
                break;
            case 3:
                binding.radioNotBinary.setChecked(true);
                break;
            default:
                binding.radioAll.setChecked(true);
                break;
        }


    }

    private UserModel getPreferences() {
        user.setAge_from(binding.ageRange.getSelectedMinValue().intValue());
        user.setAge_to(binding.ageRange.getSelectedMaxValue().intValue());
        user.setDistance(Integer.valueOf(binding.distanceValue.getText().toString()));
        return user;
    }

}

