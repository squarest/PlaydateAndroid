package com.make.playdate.main.presentation.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.make.playdate.R;
import com.make.playdate.databinding.FragmentProfileBinding;
import com.make.playdate.global.Constant;
import com.make.playdate.main.presentation.main.MainView;
import com.make.playdate.preference.presentation.editprofile.EditProfileActivity;
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
            Intent intent = new Intent(getContext(), PreferenceActivity.class);
            startActivity(intent);
        });
        binding.editProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), EditProfileActivity.class);
            startActivity(intent);
        });
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
    public void showLoading() {
        mainView.showLoading();
    }

    @Override
    public void dismissLoading() {
        mainView.dismissLoading();
    }
}
