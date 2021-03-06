package com.prince.logan.playdate.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.prince.logan.playdate.Adapter.QaCateAdapter;
import com.prince.logan.playdate.Fragment.ProfileFragment;
import com.prince.logan.playdate.Fragment.MenuFragment;
import com.prince.logan.playdate.Fragment.QAFragment;
import com.prince.logan.playdate.Fragment.PlaydateFragment;
import com.prince.logan.playdate.Global.Preferences;
import com.prince.logan.playdate.Interface.ApiClient;
import com.prince.logan.playdate.Interface.ApiInterface;
import com.prince.logan.playdate.Model.QuestionModel;
import com.prince.logan.playdate.Model.RequestModel;
import com.prince.logan.playdate.Model.UserModel;
import com.prince.logan.playdate.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener {

    public static FragmentTabHost mTabHost;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private Class mClass[] = {ProfileFragment.class,QAFragment.class,PlaydateFragment.class,MenuFragment.class};
    private Fragment mFragment[] = {new ProfileFragment(),new QAFragment(),new PlaydateFragment(),new MenuFragment()};
    private String mTitles[] = {"Profile","Question & Answer","Playdate","Menu"};
    private int mImages[] = {
            R.drawable.tab_profile,
            R.drawable.tab_qa,
            R.drawable.tab_playdate,
            R.drawable.tab_menu
    };

    public static UserModel userProfile;
    public static String userFirebaseID;
    public static boolean isPlaydate;

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    String userLati;
    String userLongi;

    Context context;

    int isLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isLocation = 0;
        buildGoogleApiClient();
        init();
    }


    private void init() {
        context = this;
        userProfile = new UserModel();
        isPlaydate = true;
        gettingProfile();
    }



    private void gettingProfile() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            userFirebaseID = bundle.getString("user_id");
            if (userFirebaseID.equals("")) {
                userProfile = (UserModel) bundle.getSerializable("userModel");
                userFirebaseID = userProfile.get_firebase_id();
                signUpToServer();
            } else {
                loginToServer();
            }
        }
    }

    private void loginToServer() {

        final ProgressDialog loading = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        loading.setIndeterminate(true);
        loading.setMessage("Please wait...");
        loading.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<RequestModel> req = apiService.login(userFirebaseID);
        req.enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, retrofit2.Response<RequestModel> response) {

                loading.dismiss();
                RequestModel responseData = response.body();

                if (responseData.getResult() == 1){
                    userProfile = responseData.getUser();
                    isLocation=1;
                    initView();
                    initEvent();

                    setting_preference();
                }
                else{
                    Toast.makeText(getApplicationContext(), responseData.getMsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RequestModel> call, Throwable t) {
                t.printStackTrace();
                loading.dismiss();
                showAlert("Alert", "Failed!");
            }
        });
    }

    private void signUpToServer() {

        String firebase_id = userProfile.get_firebase_id();
        String user_full_name = userProfile.get_user_full_name();
        String user_first_name = userProfile.get_user_first_name();
        String user_last_name = userProfile.get_user_last_name();
        String gender = userProfile.get_user_gender();
        String avatar_url = userProfile.get_user_avatar();
        String user_mail = userProfile.get_user_mail();

        final ProgressDialog loading = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        loading.setIndeterminate(true);
        loading.setMessage("Please wait...");
        loading.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<RequestModel> req = apiService.signup(firebase_id, user_full_name, user_first_name, user_last_name, gender, avatar_url, user_mail);
        req.enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, retrofit2.Response<RequestModel> response) {
                RequestModel responseData = response.body();
                loading.dismiss();
                if (responseData.getResult() == 1){
                    userProfile = responseData.getUser();
                    isLocation=1;
                    initView();
                    initEvent();

                    setting_preference();

                    Intent profileIntent = new Intent(MainActivity.this, EditProfileActivity.class);
                    startActivity(profileIntent);
                    MainActivity.this.overridePendingTransition(R.anim.slide_in_from_right,
                            R.anim.slide_out_to_left);
                }
            }

            @Override
            public void onFailure(Call<RequestModel> call, Throwable t) {
                t.printStackTrace();
                loading.dismiss();
                showAlert("Alert", "Failed!");
            }
        });
    }

    private void setting_preference() {
        Preferences.looking = userProfile.getLooking_for();
        Preferences.age_from = userProfile.getAge_from();
        Preferences.age_to = userProfile.getAge_to();
        Preferences.height_from = userProfile.getHeight_from();
        Preferences.height_to = userProfile.getHeight_to();
        Preferences.distance = userProfile.getDistance();
        Preferences.listEthnicity = userProfile.getEthnicity();
        Preferences.listReligion = userProfile.getReligion();
        Preferences.listEducation = userProfile.getEducation();
    }

    private void initView() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mFragmentList = new ArrayList<Fragment>();

        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        mTabHost.getTabWidget().setDividerDrawable(null);

        for (int i = 0;i < mFragment.length;i++){
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTitles[i]).setIndicator(getTabView(i));
            mTabHost.addTab(tabSpec,mClass[i],null);
            mFragmentList.add(mFragment[i]);
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.WHITE);
        }

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });
    }

    private View getTabView(int index) {
        View view = new View(this);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.tab_item, null);

        ImageView image = (ImageView) view.findViewById(R.id.image);
        TextView title = (TextView) view.findViewById(R.id.title);

        image.setImageResource(mImages[index]);
        title.setText(mTitles[index]);

        return view;
    }

    private void initEvent() {

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                mViewPager.setCurrentItem(mTabHost.getCurrentTab());
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mTabHost.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendRegistrationToServer(final String token) {

        final ProgressDialog loading = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        loading.setIndeterminate(true);
        loading.setMessage("Please wait...");
        loading.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<RequestModel> req = apiService.register_token(token, 0, MainActivity.userProfile.get_firebase_id());
        req.enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, retrofit2.Response<RequestModel> response) {
                RequestModel responseData = response.body();
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<RequestModel> call, Throwable t) {
                t.printStackTrace();
                loading.dismiss();
            }
        });
    }

    public void showAlert(String title, String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Dialog Title
        alertDialog.setTitle(title);
        // Dialog Message
        alertDialog.setMessage(msg);
        // on pressing cancel button
        alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
    //Place current location marker
        String longitude, latitude;
        longitude = String.valueOf(location.getLongitude());
        latitude = String.valueOf(location.getLatitude());

        if(isLocation == 1){
            updateLocation(latitude, longitude);
        }

    }

    private void updateLocation(String latitude, String longitude) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<RequestModel> req = apiService.update_loaction(MainActivity.userProfile.get_firebase_id(), latitude, longitude);
        req.enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, retrofit2.Response<RequestModel> response) {
                RequestModel responseData = response.body();
            }

            @Override
            public void onFailure(Call<RequestModel> call, Throwable t) {
                t.printStackTrace();
                return;
//                Toast.makeText(getApplicationContext(), "Location update is failed!", Toast.LENGTH_LONG).show();
            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
}
