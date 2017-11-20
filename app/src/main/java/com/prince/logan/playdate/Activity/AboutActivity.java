package com.prince.logan.playdate.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.prince.logan.playdate.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PRINCE on 11/15/2017.
 */

public class AboutActivity extends Activity implements ImageView.OnClickListener{
    @Bind(R.id.img_about_back)
    ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_about_back:
                this.finish();
                break;
        }
    }
}
