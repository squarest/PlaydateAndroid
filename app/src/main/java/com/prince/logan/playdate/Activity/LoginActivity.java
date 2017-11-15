package com.prince.logan.playdate.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.prince.logan.playdate.R;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.btn_facebook_login)
    ImageView img_btn_facebook_login;
    @Bind(R.id.login_button)
    LoginButton btn_login;
    @Bind(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;
    @Bind(R.id.viewPager)
    ViewPager pager;

    CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

        initViews();
        setEvent();
    }

    private void setEvent() {
        img_btn_facebook_login.setOnClickListener(this);
    }

    @SuppressWarnings("ConstantConditions")
    private void initViews() {

        final HomeAdapter adapter = new HomeAdapter();
        adapter.setData(createPageList());

        pager.setAdapter(adapter);

        pageIndicatorView.setAnimationType(AnimationType.COLOR);
        pageIndicatorView.setViewPager(pager);

        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult)
                    {
                        setFacebookData(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Login Canceled!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, "Failed to connect to facebook!", Toast.LENGTH_SHORT).show();
                    }

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
        View view = new View(this);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        return view;
    }

    private void setFacebookData(final LoginResult loginResult)
    {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            Log.i("Response",response.toString());

//                            String email = response.getJSONObject().getString("email");
//                            String bday= response.getJSONObject().getString("birthday");
                            String first_name = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");

                            String name = first_name + " " + lastName;
                            String gender = response.getJSONObject().getString("gender");

//                            savePreferences(name, gender);
//                            ConnectServer();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, email, first_name, last_name, gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data
        );
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_facebook_login:
//                btn_login.performClick();
                Intent profileIntent = new Intent(this, MainActivity.class);
                startActivity(profileIntent);
                break;
        }
    }
}
