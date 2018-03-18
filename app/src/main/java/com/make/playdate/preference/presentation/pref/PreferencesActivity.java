package com.make.playdate.preference.presentation.pref;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.make.playdate.R;
import com.make.playdate.base.BaseActivity;
import com.make.playdate.databinding.ActivityPreferencesBinding;
import com.make.playdate.entities.UserModel;
import com.make.playdate.questions.presentation.answers.AnswersActivity;
import com.make.playdate.questions.presentation.questions.QuestionActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

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
        binding.questionCard.setOnClickListener(view -> presenter.questionCardClicked());
        binding.backButton.setOnClickListener(view -> finish());
        binding.saveButton.setOnClickListener(view -> presenter.savePreferences(getPreferences()));
        binding.ageRange.setOnRangeSeekbarChangeListener((minAge, maxAge) ->
        {
            binding.miAge.setText(minAge.toString());
            binding.maxAge.setText(maxAge.toString());
        });
        binding.distanceSeekBar.setOnSeekbarChangeListener(distance -> binding.distanceValue.setText(distance.toString()));

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
    public void setQuestionImage(String imageUrl) {
        Picasso.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.qa_placeholder)
                .error(R.drawable.qa_placeholder)
                .into(binding.questionsImage);
    }

    @Override
    public void showQuestionScreen() {
        Intent intent = new Intent(this, QuestionActivity.class);
        startActivity(intent);
    }

    @Override
    public void showQADialog() {
        Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.qa_dialog, null);
        TextView answersButton = view.findViewById(R.id.answers_button);
        answersButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(this, AnswersActivity.class);
            startActivity(intent);
            dialog.dismiss();
        });
        TextView yesButton = view.findViewById(R.id.yes_button);
        yesButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(this, QuestionActivity.class);
            startActivity(intent);
            dialog.dismiss();
        });
        TextView noButton = view.findViewById(R.id.no_button);
        noButton.setOnClickListener(view1 -> dialog.dismiss());
        dialog.setContentView(view);
        dialog.show();

    }

    private ArrayList<String> selected;

    @Override
    public void setPreferences(UserModel userModel) {
        user = userModel;
        String ethnicity;
        if (user.getEthnicity().equals("-1")) ethnicity = getString(R.string.none_selected);
        else {
            String[] selected = user.getEthnicity().split(",");
            ethnicity = String.format("%d selected", selected.length);
        }
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
        String ethn = user.getEthnicity();

        if (ethn.equals("-1")) selected = new ArrayList<>();
        else selected = new ArrayList<>(Arrays.asList(ethn.split(",")));
        binding.ethnicity.setOnClickListener(view ->
        {
            Intent intent = new Intent(PreferencesActivity.this, EthnicityActivity.class);
            intent.putExtra("selected", selected);
            startActivityForResult(intent, 0);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK & data != null) {
            selected = data.getStringArrayListExtra("selected");
            if (selected.size() > 0) {
                binding.ethnicity.setText(String.format("%d selected", selected.size()));
                user.setEthnicity(TextUtils.join(",", selected));
            } else {
                binding.ethnicity.setText(getString(R.string.none_selected));
                user.setEthnicity("-1");
            }


        }
    }

    private UserModel getPreferences() {
        user.setAge_from(binding.ageRange.getSelectedMinValue().intValue());
        user.setAge_to(binding.ageRange.getSelectedMaxValue().intValue());
        user.setDistance(Integer.valueOf(binding.distanceValue.getText().toString()));
        return user;
    }

}

