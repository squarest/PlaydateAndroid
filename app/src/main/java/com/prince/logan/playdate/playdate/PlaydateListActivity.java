package com.prince.logan.playdate.playdate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.prince.logan.playdate.main.MainActivity;
import com.prince.logan.playdate.Adapter.PlaydateListAdapter;
import com.prince.logan.playdate.network.ApiClient;
import com.prince.logan.playdate.network.API;
import com.prince.logan.playdate.entities.RequestModel;
import com.prince.logan.playdate.entities.UserModel;
import com.prince.logan.playdate.R;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by PRINCE on 11/14/2017.
 */

public class PlaydateListActivity extends Activity implements View.OnClickListener{
    @Bind(R.id.list_playdate)
    ListView listPlaydate;
    @Bind(R.id.img_playdate_list_back)
    ImageView back;

    ArrayList<UserModel> arrayListUser = new ArrayList<UserModel>();
    PlaydateListAdapter playListAdapter;
    int subCateId;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playdate_list);
        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            subCateId = bundle.getInt("sub_cate_id");
        }

        context = this;
        setEvent();
        gettingMatchedUser();
    }

    private void setEvent() {
        back.setOnClickListener(this);
        listPlaydate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserModel chatUser = arrayListUser.get(i);
                Intent playdateDetailIntent = new Intent(PlaydateListActivity.this, PlaydateDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("userModel", (Serializable) chatUser);
                playdateDetailIntent.putExtras(bundle);
                startActivity(playdateDetailIntent);
            }
        });
    }

    private void gettingMatchedUser() {
        final ProgressDialog loading = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        loading.setIndeterminate(true);
        loading.setMessage("Please wait...");
        loading.show();

        API apiService = ApiClient.getApi().create(API.class);

        Call<RequestModel> req = apiService.get_matched_users(MainActivity.userFirebaseID, MainActivity.userProfile.getUser_playdate(), subCateId);
        req.enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, retrofit2.Response<RequestModel> response) {
                loading.dismiss();
                RequestModel responseData = response.body();

                if (responseData.getResult() == 1){

                    arrayListUser = responseData.getMatchedUser();

                    playListAdapter = new PlaydateListAdapter(getApplicationContext(), R.layout.adapter_playdate_list, arrayListUser);
                    listPlaydate.setAdapter(playListAdapter);
                    playListAdapter.notifyDataSetChanged();
                }
//                else{
//                    showAlert("Alert", responseData.getMsg());
//                }
            }

            @Override
            public void onFailure(Call<RequestModel> call, Throwable t) {
                t.printStackTrace();
                loading.dismiss();
                showAlert("Alert", "Failed!");
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
                ((Activity)context).finish();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_playdate_list_back:
                this.finish();
                break;
        }
    }
}
