package com.prince.logan.playdate.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.prince.logan.playdate.Model.UserModel;
import com.prince.logan.playdate.R;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Arrays;
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

    UserModel userModel;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.prince.logan.playdate", PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.i("111111111", something);
                Log.e("111111111", something);
//                Toast.makeText(this, something , Toast.LENGTH_SHORT).show();
            }

        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

        initViews();
        initData();
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

        btn_login.setReadPermissions(Arrays.asList(
                "user_location", "public_profile", "email", "user_birthday", "user_friends"));

        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult)
                    {
                        String accessToken= loginResult.getAccessToken().getToken();
                        handleFacebookAccessToken(accessToken);
//                        setFacebookData(loginResult);
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

    private void initData() {
        userModel = new UserModel();
        mAuth = FirebaseAuth.getInstance();
    }

    private void handleFacebookAccessToken(String token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            String fbUser = user.getUid();
                            Toast.makeText(LoginActivity.this, "Authentication Success.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
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

                            String facebook_id = object.getString("id");
                            String firstName = object.getString("first_name");
                            String lastName = object.getString("last_name");
                            String email = object.getString("email");
                            String fullName = response.getJSONObject().getString("name");
                            String gender = response.getJSONObject().getString("gender");
                            String image_url = "http://graph.facebook.com/" + facebook_id + "/picture?type=large";

                            userModel.setUserModel(facebook_id, fullName, firstName, lastName, gender, image_url,email);

//                            savePreferences(userModel.getID(), userModel.getUser_name(), userModel.getUser_email());

                            Intent main = new Intent(LoginActivity.this, MainActivity.class);
                            main.putExtra("user", 0);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("userModel", (Serializable) userModel);
                            main.putExtras(bundle);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Failed to connect to facebook!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email, name, birthday, education, hometown, gender, inspirational_people, languages, first_name, last_name, relationship_status, friends,photos");
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
                Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intentMain);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    protected void savePreferences(int userID, String userName, String userMail) {
//        // Create or retrieve the shared preference object.
//        int mode = Activity.MODE_PRIVATE;
//        SharedPreferences mySharedPreferences = getSharedPreferences(Variables.MYREF,
//                mode);
//        // Retrieve an editor to modify the shared preferences.
//        SharedPreferences.Editor editor = mySharedPreferences.edit();
//        // Store new primitive types in the shared preferences object.
//        editor.putInt("user", userID);
//        editor.putString("user_name", userName);
//        editor.putString("user_mail", userMail);
//
//        // Commit the changes.
//        editor.commit();
    }
}
