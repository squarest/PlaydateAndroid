package com.prince.logan.playdate.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.prince.logan.playdate.Adapter.ChatListAdapter;
import com.prince.logan.playdate.Interface.ApiClient;
import com.prince.logan.playdate.Interface.ApiInterface;
import com.prince.logan.playdate.Model.RequestModel;
import com.prince.logan.playdate.Model.UserModel;
import com.prince.logan.playdate.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by PRINCE on 11/14/2017.
 */

public class ChatListActivity extends Activity implements View.OnClickListener{

    @Bind(R.id.list_chat)
    ListView listChat;
    @Bind(R.id.img_chatlist_back)
    ImageView back;

    ChatListAdapter chatListAdapter;
    ArrayList<UserModel> arryChatUsers = new ArrayList<UserModel>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        ButterKnife.bind(this);

        back.setOnClickListener(this);

        listChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserModel chatUser = arryChatUsers.get(i);
                Intent playdateDetailIntent = new Intent(ChatListActivity.this, ChatActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("userModel", (Serializable) chatUser);
//                playdateDetailIntent.putExtras(bundle);
                playdateDetailIntent.putExtra("user_id", chatUser.get_firebase_id());
                playdateDetailIntent.putExtra("user_name", chatUser.get_user_full_name());
                startActivity(playdateDetailIntent);
            }
        });

        gettingChatLists();
    }

    private void gettingChatLists() {
        final ProgressDialog loading = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        loading.setIndeterminate(true);
        loading.setMessage("Please wait...");
        loading.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<RequestModel> req = apiService.get_all_users(MainActivity.userFirebaseID);
        req.enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, retrofit2.Response<RequestModel> response) {
                loading.dismiss();
                RequestModel responseData = response.body();

                if (responseData.getResult() == 1){

                    arryChatUsers = responseData.getMatchedUser();

                    chatListAdapter = new ChatListAdapter(getApplicationContext(), R.layout.adapter_playdate_list, arryChatUsers);
                    listChat.setAdapter(chatListAdapter);
                    chatListAdapter.notifyDataSetChanged();
                }
                else{
                    showAlert("Alert", responseData.getMsg());
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_chatlist_back:
                this.finish();
                break;
        }
    }
}
