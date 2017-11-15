package com.prince.logan.playdate.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andexert.library.RippleView;
import com.prince.logan.playdate.Activity.ChatListActivity;
import com.prince.logan.playdate.Activity.PlaydateListActivity;
import com.prince.logan.playdate.Activity.QuestionActivity;
import com.prince.logan.playdate.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Adib on 13-Apr-17.
 */

public class ProfileFragment extends Fragment implements RippleView.OnRippleCompleteListener{

    private View mRootView;

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

        setEvent();
    }

    private void setEvent(){

    }

    @Override
    public void onComplete(RippleView rippleView) {

    }

}
