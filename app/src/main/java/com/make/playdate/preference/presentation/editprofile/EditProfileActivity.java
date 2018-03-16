package com.make.playdate.preference.presentation.editprofile;

import android.app.AlertDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.make.playdate.R;
import com.make.playdate.base.BaseActivity;
import com.make.playdate.databinding.ActivityEditProfileBinding;
import com.make.playdate.entities.UserModel;
import com.make.playdate.global.Constant;
import com.make.playdate.main.presentation.main.MainActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by PRINCE on 12/4/2017.
 */

public class EditProfileActivity extends BaseActivity implements EditProfileView {

    private ActivityEditProfileBinding binding;
    private UserModel user;
    @InjectPresenter
    public EditProfilePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        presenter.viewCreated();
        setEvents();
    }

    private void setEvents() {
        binding.backButton.setOnClickListener(view -> onBackPressed());
        binding.saveButton.setOnClickListener(view -> {
                    if (isValid()) presenter.saveButtonClicked(user);
                }
        );
        binding.ethnicity.setOnClickListener(view ->
        {
            AlertDialog.Builder builderEthnicity = new AlertDialog.Builder(this);
            builderEthnicity.setTitle("Select");
            builderEthnicity.setItems(Constant.arrayEthnicity, (dialog, item) -> {
                ((TextView) view).setText(Constant.arrayEthnicity[item]);
                user.setUser_ethnicity(item);

            });
            builderEthnicity.show();
        });
        binding.age.setOnClickListener(view -> {
            String[] ageList = getAgeList();
            AlertDialog.Builder builderAge = new AlertDialog.Builder(this);
            builderAge.setTitle("Select");
            builderAge.setItems(ageList, (dialog, item) -> {
                ((TextView) view).setText(ageList[item]);
                user.setUser_age(ageList[item]);

            });
            builderAge.show();
        });
        binding.gender.setOnClickListener(view -> {
            AlertDialog.Builder builderAge = new AlertDialog.Builder(this);
            builderAge.setTitle("Select");
            builderAge.setItems(Constant.genderArray, (dialog, item) -> {
                ((TextView) view).setText(Constant.genderArray[item]);
                user.setGender(Constant.genderArray[item]);

            });
            builderAge.show();
        });

        binding.switchPushNotification.setOnCheckedChangeListener((compoundButton, isChecked) ->
        {
            int isPushNotification = isChecked ? 1 : 0;
            user.setIs_pushnotification(isPushNotification);
        });
    }

    private String[] getAgeList() {
        int minAge = 18;
        int maxAge = 60;
        String[] list = new String[44];
        for (int i = minAge; i <= maxAge; i++) {
            list[i - minAge] = String.valueOf(i);
        }
        return list;

    }

    @Override
    public void setUser(UserModel userModel) {
        user = userModel;
        binding.setUser(userModel);
        String userEthnicity;
        if (userModel.getUser_ethnicity() == -1) userEthnicity = getString(R.string.none_selected);
        else userEthnicity = Constant.arrayEthnicity[userModel.getUser_ethnicity()];
        binding.ethnicity.setText(userEthnicity);
        boolean isPushEnabled = userModel.getIs_pushnotification() == 1;
        binding.switchPushNotification.setChecked(isPushEnabled);
        Picasso.with(this)
                .load(userModel.get_user_avatar())
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(binding.imgEditProfile);

    }

    private boolean isValid() {
        if (binding.age.getText().toString().equals(getString(R.string.none_selected))) {
            Toast.makeText(this, "Please enter your age", Toast.LENGTH_LONG).show();
            return false;
        }
        if (binding.ethnicity.getText().toString().equals(getString(R.string.none_selected))) {
            Toast.makeText(this, "Select Ethnicity", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getIntent().getBooleanExtra("isFirstStart", false)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else finish();
    }
}
