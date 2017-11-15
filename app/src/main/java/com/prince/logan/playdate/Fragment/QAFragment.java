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
import com.prince.logan.playdate.Activity.QuestionActivity;
import com.prince.logan.playdate.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Adib on 13-Apr-17.
 */

public class QAFragment extends Fragment implements RippleView.OnRippleCompleteListener{

    private View mRootView;

    @Bind(R.id.btn_qa_fitness)
    RippleView btn_fitness;
    @Bind(R.id.btn_qa_nightlife)
    RippleView btn_night;
    @Bind(R.id.btn_qa_spiritual)
    RippleView btn_spiritual;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","MineFragment");
            mRootView = inflater.inflate(R.layout.fragment_qa,container,false);
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
        btn_fitness.setOnRippleCompleteListener(this);
        btn_night.setOnRippleCompleteListener(this);
        btn_spiritual.setOnRippleCompleteListener(this);
    }

    @Override
    public void onComplete(RippleView rippleView) {
        switch (rippleView.getId())
        {
            case R.id.btn_qa_fitness:
                Intent qaFitnessIntent = new Intent(getContext(), QuestionActivity.class);
                startActivity(qaFitnessIntent);
                break;
            case R.id.btn_qa_nightlife:
                Intent qaNightIntent = new Intent(getContext(), QuestionActivity.class);
                startActivity(qaNightIntent);
                break;
            case R.id.btn_qa_spiritual:
                Intent qaSpiritualIntent = new Intent(getContext(), QuestionActivity.class);
                startActivity(qaSpiritualIntent);
                break;
        }
    }
}
