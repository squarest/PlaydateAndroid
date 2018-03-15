package com.make.playdate.main.presentation.profile;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.make.playdate.R;
import com.make.playdate.databinding.FragmentProfileBinding;
import com.make.playdate.global.Constant;
import com.make.playdate.main.presentation.main.MainView;
import com.make.playdate.preference.presentation.editprofile.EditProfileActivity;
import com.make.playdate.preference.presentation.pref.PreferencesActivity;
import com.make.playdate.questions.presentation.answers.AnswersActivity;
import com.make.playdate.questions.presentation.questions.QuestionActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by Adib on 13-Apr-17.
 */

public class ProfileFragment extends MvpAppCompatFragment implements ProfileView {
    private FragmentProfileBinding binding;
    private MainView mainView;
    private SharedPreferences sharedPreferences;
    @InjectPresenter
    public ProfilePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainView = (MainView) getActivity();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        presenter.viewCreated();
        setEvents();
        setSwitch();
    }


    private void setSwitch() {
        boolean isPlaydate = sharedPreferences.getBoolean(Constant.IS_PLAYDATE_KEY, false);
        binding.switchReceivePlaydate.setChecked(isPlaydate);
    }


    private void setEvents() {
        binding.switchReceivePlaydate.setOnCheckedChangeListener((buttonView, isPlaydate) ->
                sharedPreferences.edit()
                        .putBoolean(Constant.IS_PLAYDATE_KEY, isPlaydate).apply());
        binding.preferenceButton.setOnClickListener(view ->
        {
            Intent intent = new Intent(getContext(), PreferencesActivity.class);
            startActivity(intent);
        });
        binding.editProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), EditProfileActivity.class);
            startActivity(intent);
        });
        binding.questionCard.setOnClickListener(view -> presenter.questionCardClicked());
    }

    @Override
    public void setName(String name) {
        binding.txtProfileFullname.setText(getString(R.string.welcome_title) + name);
    }

    @Override
    public void setProfilePhoto(String imageUrl) {
        Picasso.with(getContext())
                .load(imageUrl)
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(binding.imgProfile);
    }

    @Override
    public void setQuestionImage(String imageUrl) {
        Picasso.with(getContext())
                .load(imageUrl)
                .placeholder(R.drawable.qa_placeholder)
                .error(R.drawable.qa_placeholder)
                .into(binding.questionsImage);
    }

    @Override
    public void showQADialog() {
        Dialog dialog = new Dialog(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.qa_dialog, null);
        TextView answersButton = view.findViewById(R.id.answers_button);
        answersButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), AnswersActivity.class);
            startActivity(intent);
            dialog.dismiss();
        });
        TextView yesButton = view.findViewById(R.id.yes_button);
        yesButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), QuestionActivity.class);
            startActivity(intent);
            dialog.dismiss();
        });
        TextView noButton = view.findViewById(R.id.no_button);
        noButton.setOnClickListener(view1 -> dialog.dismiss());
        dialog.setContentView(view);
        dialog.show();

    }

    @Override
    public void showQuestionScreen() {
        Intent intent = new Intent(getContext(), QuestionActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoading() {
        mainView.showLoading();
    }

    @Override
    public void dismissLoading() {
        mainView.dismissLoading();
    }
}
