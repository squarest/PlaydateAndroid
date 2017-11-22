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
import android.widget.ListView;

import com.andexert.library.RippleView;
import com.prince.logan.playdate.Activity.ChatListActivity;
import com.prince.logan.playdate.Activity.MainActivity;
import com.prince.logan.playdate.Activity.PlaydateListActivity;
import com.prince.logan.playdate.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Adib on 13-Apr-17.
 */

public class PlaydateFragment extends Fragment implements RippleView.OnRippleCompleteListener, View.OnClickListener{

    private View mRootView;
    @Bind(R.id.btn_playdate)
    RippleView btnPlaydate;
    @Bind(R.id.btn_chat)
    RippleView btnChat;
    @Bind(R.id.lin_playdate)
    LinearLayout linPlaydate;
    @Bind(R.id.lin_chat)
    LinearLayout linChat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","ReportFragment");
            mRootView = inflater.inflate(R.layout.fragment_playdate,container,false);
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

    private void setEvent(){
        btnChat.setOnRippleCompleteListener(this);
        btnPlaydate.setOnRippleCompleteListener(this);
        linChat.setOnClickListener(this);
        linPlaydate.setOnClickListener(this);
    }

    @Override
    public void onComplete(RippleView rippleView) {
        switch (rippleView.getId())
        {
            case R.id.btn_playdate:
                Intent playdateIntent = new Intent(getContext(), PlaydateListActivity.class);
                startActivity(playdateIntent);
                break;
            case R.id.btn_chat:
                Intent chatIntent = new Intent(getContext(), ChatListActivity.class);
                startActivity(chatIntent);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lin_playdate:
                Intent playdateIntent = new Intent(getContext(), PlaydateListActivity.class);
                startActivity(playdateIntent);
                break;
            case R.id.lin_chat:
                Intent chatIntent = new Intent(getContext(), ChatListActivity.class);
                startActivity(chatIntent);
                break;
        }
    }
}
