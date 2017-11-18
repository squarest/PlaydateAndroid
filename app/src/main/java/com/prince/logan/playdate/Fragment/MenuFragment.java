package com.prince.logan.playdate.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.prince.logan.playdate.Activity.AboutActivity;
import com.prince.logan.playdate.Activity.ChatListActivity;
import com.prince.logan.playdate.Activity.FAQActivity;
import com.prince.logan.playdate.Activity.PlaydateListActivity;
import com.prince.logan.playdate.Activity.PreferencesActivity;
import com.prince.logan.playdate.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Adib on 13-Apr-17.
 */

public class MenuFragment extends Fragment implements View.OnClickListener{

    private View mRootView;
    @Bind(R.id.lin_menu_about)
    LinearLayout btnAbout;
    @Bind(R.id.lin_menu_chats)
    LinearLayout btnChats;
    @Bind(R.id.lin_menu_faq)
    LinearLayout btnFaq;
    @Bind(R.id.lin_menu_feedback)
    LinearLayout btnFeedback;
    @Bind(R.id.lin_menu_playdate)
    LinearLayout btnPlaydate;
    @Bind(R.id.lin_menu_preference)
    LinearLayout btnPreference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","MessageFragment");
            mRootView = inflater.inflate(R.layout.fragment_menu,container,false);
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

        setEvent();
    }

    public void setEvent(){
        btnAbout.setOnClickListener(this);
        btnChats.setOnClickListener(this);
        btnFaq.setOnClickListener(this);
        btnPlaydate.setOnClickListener(this);
        btnPreference.setOnClickListener(this);
        btnFeedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lin_menu_about:
                Intent aboutIntent = new Intent(getContext(), AboutActivity.class);
                startActivity(aboutIntent);
                break;
            case R.id.lin_menu_faq:
                Intent faqIntent = new Intent(getContext(), FAQActivity.class);
                startActivity(faqIntent);
                break;
            case R.id.lin_menu_playdate:
                Intent playdateIntent = new Intent(getContext(), PlaydateListActivity.class);
                startActivity(playdateIntent);
                break;
            case R.id.lin_menu_chats:
                Intent chatIntent = new Intent(getContext(), ChatListActivity.class);
                startActivity(chatIntent);
                break;
            case R.id.lin_menu_feedback:

                break;
            case R.id.lin_menu_preference:
                Intent preferenceIntent = new Intent(getContext(), PreferencesActivity.class);
                startActivity(preferenceIntent);
                break;
        }
    }
}
