package com.make.playdate.help;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.make.playdate.R;
import com.make.playdate.base.BaseActivity;
import com.make.playdate.utils.DialogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

/**
 * Created by PRINCE on 11/15/2017.
 */

public class AboutActivity extends BaseActivity implements ImageView.OnClickListener {

    @BindView(R.id.img_about_back)
    ImageView back;
    @BindView(R.id.txt_twitter)
    TextView txtTwitter;
    @BindView(R.id.lin_about_vision)
    LinearLayout linVision;
    @BindView(R.id.lin_about_knowledge)
    LinearLayout linKnowledge;
    private DialogUtil dialogUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        dialogUtil = new DialogUtil(this);
        back.setOnClickListener(this);
        linKnowledge.setOnClickListener(this);
        linVision.setOnClickListener(this);
        txtTwitter.setText("@makeplaydate");


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_about_back:
                finish();
                break;
            case R.id.lin_about_vision:
                dialogUtil.getAlertDialog(getString(R.string.vision_dialod_title),
                        getString(R.string.vision_dialod_descr)).show();
                break;
            case R.id.lin_about_knowledge:
                dialogUtil.getAlertDialog(getString(R.string.acknow_dialog_title),
                        getString(R.string.acknow_dialog_descr)).show();
                break;
        }
    }


}
