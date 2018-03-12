package com.make.playdate.base;

import android.app.ProgressDialog;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.make.playdate.utils.DialogUtil;

/**
 * Created by dmitrijfomenko on 01.03.2018.
 */

public class BaseActivity extends MvpAppCompatActivity implements BaseView{
    private ProgressDialog loadingDialog;

    @Override
    public void showLoading() {
        if (loadingDialog == null) {
            DialogUtil dialogUtil = new DialogUtil(this);
            loadingDialog = dialogUtil.getProgressDialog();
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void dismissLoading() {
        if (loadingDialog != null) loadingDialog.dismiss();
    }
}
