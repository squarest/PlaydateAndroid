package com.make.playdate.help;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;

import com.make.playdate.R;
import com.make.playdate.base.BaseActivity;
import com.make.playdate.databinding.ActivityAboutBinding;
import com.make.playdate.utils.DialogUtil;

import io.reactivex.annotations.Nullable;

/**
 * Created by PRINCE on 11/15/2017.
 */

public class AboutActivity extends BaseActivity {
    private DialogUtil dialogUtil;
    private ActivityAboutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about);
        dialogUtil = new DialogUtil(this);
        binding.backButton.setOnClickListener(view -> onBackPressed());
        binding.linAboutVision.setOnClickListener(view -> dialogUtil.getAlertDialog(getString(R.string.vision_dialod_title),
                getString(R.string.vision_dialod_descr)).show());
        binding.txtTwitter.setText("@makeplaydate");
        binding.websiteLin.setOnClickListener(view -> showScreen("https://www.makeplaydate.com"));
        binding.txtTwitter.setOnClickListener(view -> showScreen("https://twitter.com/@makeplaydate"));
        binding.rateButton.setOnClickListener(view -> showScreen("https://play.google.com/store/apps/details?id=com.make.playdate"));
    }

    private void showScreen(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
