package com.prince.logan.playdate.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.prince.logan.playdate.Activity.ChatListActivity;
import com.prince.logan.playdate.Activity.MainActivity;
import com.prince.logan.playdate.Activity.PlaydateListActivity;
import com.prince.logan.playdate.Interface.ApiClient;
import com.prince.logan.playdate.Interface.ApiInterface;
import com.prince.logan.playdate.Model.QuestionModel;
import com.prince.logan.playdate.Model.RequestModel;
import com.prince.logan.playdate.Model.SubCateModel;
import com.prince.logan.playdate.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Adib on 13-Apr-17.
 */

public class PlaydateFragment extends Fragment implements RippleView.OnRippleCompleteListener, View.OnClickListener{

    private View mRootView;
    @Bind(R.id.btn_playdate)
    RippleView btnPlaydate;
    @Bind(R.id.btn_chat)
    RippleView btnChat;
    @Bind(R.id.lin_playdate)
    LinearLayout linPlaydate;
    @Bind(R.id.lin_chat)
    LinearLayout linChat;

    ArrayList<SubCateModel> listSubCategory = new ArrayList<SubCateModel>();
    android.support.v7.app.AlertDialog optionMenu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null){
            Log.e("666","ReportFragment");
            mRootView = inflater.inflate(R.layout.fragment_playdate,container,false);
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

        setEvent();
    }

    private void setEvent(){
        btnChat.setOnRippleCompleteListener(this);
        btnPlaydate.setOnRippleCompleteListener(this);
        linChat.setOnClickListener(this);
        linPlaydate.setOnClickListener(this);
    }

    @Override
    public void onComplete(RippleView rippleView) {
        switch (rippleView.getId())
        {
            case R.id.btn_playdate:
                Intent playdateIntent = new Intent(getContext(), PlaydateListActivity.class);
                startActivity(playdateIntent);
                break;
            case R.id.btn_chat:
                Intent chatIntent = new Intent(getContext(), ChatListActivity.class);
                startActivity(chatIntent);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lin_playdate:
                if(MainActivity.isPlaydate){
                    gettingSubCate();
                }
                else{
                    showAlert("Warning", "You can't receive playdates. Please enable playdates in order to receive the playdates");
                }
                break;
            case R.id.lin_chat:
                Intent chatIntent = new Intent(getContext(), ChatListActivity.class);
                startActivity(chatIntent);
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
                    listSubCategory = responseData.getSubCategory();
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
        final String[] option = new String[listSubCategory.size()];
        for (int i = 0; i<listSubCategory.size(); i++){
            option[i] = listSubCategory.get(i).getSub_cate();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, option);
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
        builder.setTitle("Select Sub Category");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent playdateIntent = new Intent(getContext(), PlaydateListActivity.class);
                playdateIntent.putExtra("sub_cate_id", listSubCategory.get(which).getSub_cate_id());
                startActivity(playdateIntent);
            } });
        optionMenu = builder.create();
        optionMenu.show();
    }

    public void showAlert(String title, String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

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
