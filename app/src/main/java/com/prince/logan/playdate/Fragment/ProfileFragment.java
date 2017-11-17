package com.prince.logan.playdate.Fragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.prince.logan.playdate.Activity.ChatListActivity;
import com.prince.logan.playdate.Activity.LoginActivity;
import com.prince.logan.playdate.Activity.MainActivity;
import com.prince.logan.playdate.Activity.PlaydateListActivity;
import com.prince.logan.playdate.Activity.QuestionActivity;
import com.prince.logan.playdate.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Adib on 13-Apr-17.
 */

public class ProfileFragment extends Fragment implements RippleView.OnRippleCompleteListener, View.OnClickListener{

    private View mRootView;

    @Bind(R.id.txt_profile_firstname)
    TextView txt_firstname;
    @Bind(R.id.txt_profile_lastname)
    TextView txt_lastname;
    @Bind(R.id.txt_profile_fullname)
    TextView txt_fullname;
    @Bind(R.id.txt_profile_gender)
    TextView txt_gender;
    @Bind(R.id.txt_profile_mail)
    TextView txt_mail;
    @Bind(R.id.txt_profile_playdate)
    TextView txt_playdate;
    @Bind(R.id.txt_profile_preference)
    TextView txt_preference;
    @Bind(R.id.img_profile)
    ImageView imgProfile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","HomeFragment");
            mRootView = inflater.inflate(R.layout.fragment_profile,container,false);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null){
            parent.removeView(mRootView);
        }
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        setEvent();
    }

    private void initView() {
        txt_firstname.setText(MainActivity.userProfile.get_user_first_name());
        txt_lastname.setText(MainActivity.userProfile.get_user_last_name());
        txt_fullname.setText("Welcome, " + MainActivity.userProfile.get_user_full_name());
        txt_gender.setText(MainActivity.userProfile.get_user_gender());
        txt_mail.setText(MainActivity.userProfile.get_user_mail());

        String avatar_url = MainActivity.userProfile.get_user_avatar();
        avatar_url = avatar_url.replace("http://", "https://");

        Picasso.with(getContext())
                .load(avatar_url)
                .placeholder(R.drawable.user) // optional
                .error(R.drawable.user)         // optional
                .into(imgProfile);
    }

    private void setEvent(){
        txt_preference.setOnClickListener(this);
        txt_playdate.setOnClickListener(this);
    }

    @Override
    public void onComplete(RippleView rippleView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_profile_playdate:
                Intent main = new Intent(getContext(), PlaydateListActivity.class);
                startActivity(main);
                break;
            case R.id.txt_profile_preference:
                Toast.makeText(getContext(), "preference", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
