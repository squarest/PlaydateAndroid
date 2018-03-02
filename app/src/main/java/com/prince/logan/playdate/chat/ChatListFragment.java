package com.prince.logan.playdate.chat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.prince.logan.playdate.main.presentation.main.MainActivity;
import com.prince.logan.playdate.Adapter.ChatListAdapter;
import com.prince.logan.playdate.network.ApiClient;
import com.prince.logan.playdate.network.API;
import com.prince.logan.playdate.entities.RequestModel;
import com.prince.logan.playdate.entities.UserModel;
import com.prince.logan.playdate.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by PRINCE on 11/14/2017.
 */

public class ChatListFragment extends Fragment {

    @Bind(R.id.list_chat)
    ListView listChat;

    ChatListAdapter chatListAdapter;
    ArrayList<UserModel> arryChatUsers = new ArrayList<UserModel>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(view);


        listChat.setOnItemClickListener((adapterView, v, i, l) -> {
            UserModel chatUser = arryChatUsers.get(i);
            Intent playdateDetailIntent = new Intent(getContext(), ChatActivity.class);
            playdateDetailIntent.putExtra("user_id", chatUser.get_firebase_id());
            playdateDetailIntent.putExtra("user_name", chatUser.get_user_full_name());
            startActivity(playdateDetailIntent);
        });

        gettingChatLists();
    }



    private void gettingChatLists() {
        final ProgressDialog loading = new ProgressDialog(getContext(), R.style.AppTheme_Dark_Dialog);
        loading.setIndeterminate(true);
        loading.setMessage("Please wait...");
        loading.show();

        API apiService = ApiClient.getApi().create(API.class);

        Call<RequestModel> req = apiService.get_all_users(MainActivity.userFirebaseID);
        req.enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, retrofit2.Response<RequestModel> response) {
                loading.dismiss();
                RequestModel responseData = response.body();

                if (responseData.getResult() == 1) {

                    arryChatUsers = responseData.getMatchedUser();

                    chatListAdapter = new ChatListAdapter(getContext(), R.layout.adapter_playdate_list, arryChatUsers);
                    listChat.setAdapter(chatListAdapter);
                    chatListAdapter.notifyDataSetChanged();
                } else {
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

    public void showAlert(String title, String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        // Dialog Title
        alertDialog.setTitle(title);
        // Dialog Message
        alertDialog.setMessage(msg);
        // on pressing cancel button
        alertDialog.setNegativeButton("OK", (dialog, which) -> dialog.cancel());
        // Showing Alert Message
        alertDialog.show();
    }


}
