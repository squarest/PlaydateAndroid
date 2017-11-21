package com.prince.logan.playdate.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.prince.logan.playdate.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PRINCE on 11/20/2017.
 */

public class FAQDetailActivity extends Activity {

    Context context;
    @Bind(R.id.img_faq_detail_back)
    ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_detail);
        ButterKnife.bind(this);

        context = this;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)context).finish();
            }
        });
    }
}
