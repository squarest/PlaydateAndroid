package com.make.playdate.auth.presentation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.make.playdate.R;
import com.make.playdate.base.BaseActivity;
import com.make.playdate.databinding.ActivityLoginBinding;
import com.make.playdate.main.presentation.main.MainActivity;
import com.rd.animation.type.AnimationType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends BaseActivity implements LoginView {

    @InjectPresenter
    public LoginPresenter presenter;
    private ActivityLoginBinding binding;


    CallbackManager mCallbackManager;


    public static String[] perms = {Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!hasPermissions(this, perms)) {
            ActivityCompat.requestPermissions(this, perms, 1);
        } else presenter.activityCreated();

    }

    //Check the permissions
    public boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED & grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            presenter.activityCreated();

        } else {

            Toast.makeText(this, R.string.failed_to_start_app, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initViewPager();
        initFacebookButton();
    }


    private void initViewPager() {
        final HomeAdapter adapter = new HomeAdapter();
        adapter.setData(createPageList());
        binding.viewPager.setAdapter(adapter);
        binding.pageIndicatorView.setAnimationType(AnimationType.COLOR);
        binding.pageIndicatorView.setViewPager(binding.viewPager);


    }

    private void initFacebookButton() {
        binding.loginButton.setOnClickListener(v ->
        {
            LoginManager fbLoginManager = LoginManager.getInstance();
            mCallbackManager = CallbackManager.Factory.create();
            fbLoginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    presenter.fbLoginSuccess();
                }

                @Override
                public void onCancel() {
                    Toast.makeText(LoginActivity.this, "Login Canceled!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Failed to connect to facebook!", Toast.LENGTH_SHORT).show();
                }
            });
            fbLoginManager.logInWithReadPermissions(this, Arrays.asList(
                    "user_location", "public_profile", "email", "user_birthday", "user_friends"));
        });
    }

    @NonNull
    private List<View> createPageList() {
        List<View> pageList = new ArrayList<>();

        pageList.add(createPageView(R.layout.indicate01));
        pageList.add(createPageView(R.layout.indicate02));
        pageList.add(createPageView(R.layout.indicate03));
        pageList.add(createPageView(R.layout.indicate04));

        return pageList;
    }

    @NonNull
    private View createPageView(int layout) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(layout, null);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showNextScreen() {
        Intent main = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(main);
        LoginActivity.this.overridePendingTransition(R.anim.slide_in_from_right,
                R.anim.slide_out_to_left);
        LoginActivity.this.finish();
    }


}
