package com.prince.logan.playdate.playdate.presentation.details;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.prince.logan.playdate.R;
import com.prince.logan.playdate.base.BaseActivity;
import com.prince.logan.playdate.chat.presentation.chat.ChatActivity;
import com.prince.logan.playdate.databinding.ActivityPlaydateDetailBinding;
import com.prince.logan.playdate.entities.PlaydateModel;
import com.squareup.picasso.Picasso;

/**
 * Created by PRINCE on 11/14/2017.
 */

public class PlaydateDetailActivity extends BaseActivity implements PlaydateDetailView {
    public ActivityPlaydateDetailBinding binding;
    @InjectPresenter
    PlaydateDetailPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_playdate_detail);
        presenter.viewCreated(getIntent().getStringExtra("playdateId"));
        setEvents();
    }

    private void setEvents() {
        binding.backButton.setOnClickListener(view -> finish());
        binding.chatButton.setOnClickListener(view -> presenter.chatButtonClicked());
    }

    @Override
    public void setUserData(PlaydateModel playdateModel) {
        binding.setUser(playdateModel);
        binding.userName.setText(playdateModel.userFullName);
        binding.questionsList.setLayoutManager(new LinearLayoutManager(this));
        binding.questionsList.setAdapter(new PlaydateAnswersAdapter(playdateModel.questions));
        String avatarImg = playdateModel.avatarUrl;
        avatarImg = avatarImg.replace("http://", "https://");
        Picasso.with(this)
                .load(avatarImg)
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(binding.playdateImage);
        dismissLoading();
    }

    @Override
    public void showChat(PlaydateModel playdateModel) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("chatData", playdateModel);
        startActivity(intent);
    }
}
