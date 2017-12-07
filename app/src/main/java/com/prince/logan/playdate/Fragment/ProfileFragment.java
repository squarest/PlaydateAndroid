package com.prince.logan.playdate.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.prince.logan.playdate.Activity.ChatListActivity;
import com.prince.logan.playdate.Activity.LoginActivity;
import com.prince.logan.playdate.Activity.MainActivity;
import com.prince.logan.playdate.Activity.PlaydateListActivity;
import com.prince.logan.playdate.Activity.PreferencesActivity;
import com.prince.logan.playdate.Activity.QuestionActivity;
import com.prince.logan.playdate.Interface.ApiClient;
import com.prince.logan.playdate.Interface.ApiInterface;
import com.prince.logan.playdate.Model.QuestionModel;
import com.prince.logan.playdate.Model.RequestModel;
import com.prince.logan.playdate.Model.SubCateModel;
import com.prince.logan.playdate.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Adib on 13-Apr-17.
 */

public class ProfileFragment extends Fragment implements RippleView.OnRippleCompleteListener, View.OnClickListener{

    private View mRootView;

    @Bind(R.id.txt_profile_fullname)
    TextView txt_fullname;
    @Bind(R.id.txt_profile_playdate)
    TextView txt_playdate;
    @Bind(R.id.txt_profile_preference)
    TextView txt_preference;
    @Bind(R.id.img_profile)
    ImageView imgProfile;
    @Bind(R.id.txt_profile_want)
    TextView txtWant;
    @Bind(R.id.switch_receive_playdate)
    Switch switchPlaydateReceive;
    @Bind(R.id.lin_gender)
    LinearLayout linWant;
    @Bind(R.id.lin_border)
    LinearLayout linBorder;

    AlertDialog optionMenu;

    public static ArrayList<QuestionModel> cateList = new ArrayList<QuestionModel>();
    public ArrayList<SubCateModel> subCateList = new ArrayList<SubCateModel>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","HomeFragment");
            mRootView = inflater.inflate(R.layout.fragment_profile,container,false);
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

        gettingCategory();
        setEvent();
    }

    private void gettingCategory() {

        final ProgressDialog loading = new ProgressDialog(getContext(), R.style.AppTheme_Dark_Dialog);
        loading.setIndeterminate(true);
        loading.setMessage("Please wait...");
        loading.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<RequestModel> req = apiService.get_category();
        req.enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, retrofit2.Response<RequestModel> response) {
                RequestModel responseData = response.body();
                loading.dismiss();
                if (responseData.getResult() == 1){
                    cateList = responseData.getCateQuestion();
                    initView();
                }
                else{
                    Toast.makeText(getContext(), "There is no categories", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RequestModel> call, Throwable t) {
                t.printStackTrace();
                loading.dismiss();
                Toast.makeText(getContext(), "Server problem!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initView() {

        txt_fullname.setText("Welcome, " + MainActivity.userProfile.get_user_full_name());

        String avatar_url = MainActivity.userProfile.get_user_avatar();
        avatar_url = avatar_url.replace("http://", "https://");

        Picasso.with(getContext())
                .load(avatar_url)
                .placeholder(R.drawable.user) // optional
                .error(R.drawable.user)         // optional
                .into(imgProfile);

        final String[] option = new String[cateList.size()];
        for (int i = 0; i<cateList.size(); i++){
            if(cateList.get(i).getCate_id() == MainActivity.userProfile.getUser_playdate()){
                String cateName = cateList.get(i).getCategory();
                txtWant.setText(cateName);
                MainActivity.isPlaydate = true;
            }
            else if(MainActivity.userProfile.getUser_playdate() == -1){
                MainActivity.isPlaydate = false;
                txtWant.setText("Not selected");
            }
            option[i] = cateList.get(i).getCategory();
        }

        if(MainActivity.isPlaydate){
            linWant.setVisibility(View.VISIBLE);
            linBorder.setVisibility(View.VISIBLE);
            switchPlaydateReceive.setChecked(true);
        }
        else {
            linWant.setVisibility(View.INVISIBLE);
            linBorder.setVisibility(View.INVISIBLE);
            switchPlaydateReceive.setChecked(false);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, option);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Category");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                txtWant.setText(option[which]);
                for (int i=0; i<cateList.size(); i++){
                    if (cateList.get(i).getCategory().equals(option[which])){
                        setUserPlaydates(cateList.get(i).getCate_id());
                    }
                }
            } });
        optionMenu = builder.create();
    }

    private void setUserPlaydates(final int cate_id) {
        final ProgressDialog loading = new ProgressDialog(getContext(), R.style.AppTheme_Dark_Dialog);
        loading.setIndeterminate(true);
        loading.setMessage("Please wait...");
        loading.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<RequestModel> req = apiService.set_user_playdate(MainActivity.userFirebaseID, cate_id);
        req.enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, retrofit2.Response<RequestModel> response) {
                RequestModel responseData = response.body();
                loading.dismiss();
                if (responseData.getResult() == 1){
                    MainActivity.userProfile.setUser_playdate(cate_id);
                }
                else{
                    Toast.makeText(getContext(), responseData.getMsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RequestModel> call, Throwable t) {
                t.printStackTrace();
                loading.dismiss();
                Toast.makeText(getContext(), "Server problem!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setEvent(){
        txt_preference.setOnClickListener(this);
        txt_playdate.setOnClickListener(this);
        txtWant.setOnClickListener(this);

        switchPlaydateReceive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", ""+isChecked);
                if(isChecked){
                    MainActivity.isPlaydate = true;
                    linWant.setVisibility(View.VISIBLE);
                    linBorder.setVisibility(View.VISIBLE);
                }
                else{
                    MainActivity.isPlaydate = false;
                    linWant.setVisibility(View.INVISIBLE);
                    linBorder.setVisibility(View.INVISIBLE);
                }
            }

        });
    }

    @Override
    public void onComplete(RippleView rippleView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_profile_playdate:
                if (!MainActivity.isPlaydate){
                    showAlert("Warning", "You can't receive playdates. Please enable playdates in order to receive the playdates");
                }
                else{

//                    gettingSubCate();
//                    Intent intentPlaydate = new Intent(getContext(), PlaydateListActivity.class);
//                    startActivity(intentPlaydate);

                    MainActivity.mTabHost.setCurrentTab(2);
                }
                break;
            case R.id.txt_profile_preference:
                Intent intentPreference = new Intent(getContext(), PreferencesActivity.class);
                startActivity(intentPreference);
                break;
            case R.id.txt_profile_want:

                optionMenu.show();

                break;
        }
    }

    private void gettingSubCate() {
        final ProgressDialog loading = new ProgressDialog(getContext(), R.style.AppTheme_Dark_Dialog);
        loading.setIndeterminate(true);
        loading.setMessage("Please wait...");
        loading.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<RequestModel> req = apiService.get_sub_category(MainActivity.userProfile.getUser_playdate());
        req.enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, retrofit2.Response<RequestModel> response) {
                RequestModel responseData = response.body();
                loading.dismiss();
                if (responseData.getResult() == 1){
                    subCateList = responseData.getSubCategory();
                    showingSubCateDialog();
                }
                else{
                    Toast.makeText(getContext(), "There is no categories", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RequestModel> call, Throwable t) {
                t.printStackTrace();
                loading.dismiss();
                Toast.makeText(getContext(), "Server problem!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showingSubCateDialog() {
        final String[] option = new String[subCateList.size()];
        for (int i = 0; i<subCateList.size(); i++){
            option[i] = subCateList.get(i).getSub_cate();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, option);
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
        builder.setTitle("Select Sub Category");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent playdateIntent = new Intent(getContext(), PlaydateListActivity.class);
                playdateIntent.putExtra("sub_cate_id", subCateList.get(which).getSub_cate_id());
                startActivity(playdateIntent);
            } });
        optionMenu = builder.create();
        optionMenu.show();
    }

    public void showAlert(String title, String msg){
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getContext());

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
}
