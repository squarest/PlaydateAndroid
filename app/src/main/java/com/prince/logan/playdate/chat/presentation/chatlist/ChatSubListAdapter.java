package com.prince.logan.playdate.chat.presentation.chatlist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prince.logan.playdate.R;
import com.prince.logan.playdate.chat.presentation.chat.ChatActivity;
import com.prince.logan.playdate.entities.PlaydateModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dmitrijfomenko on 06.03.2018.
 */

public class ChatSubListAdapter extends RecyclerView.Adapter<ChatSubListAdapter.SimpleViewHolder> {
    private Context mContext;
    private List<PlaydateModel> users;


    static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public ImageView imgPlaydate;
        public View view;

        public SimpleViewHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.chat_person);
            imgPlaydate = view.findViewById(R.id.chat_image);
            this.view = view;


        }
    }

    public ChatSubListAdapter(List<PlaydateModel> data) {
        this.users = data;
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_sublist_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        PlaydateModel chatUser = users.get(position);
        String avatar_url = chatUser.avatarUrl;
        if (avatar_url != null) {
            String profileImage = avatar_url.replace("http://", "https://");

            Picasso.with(mContext)
                    .load(profileImage)
                    .placeholder(R.drawable.user)
                    .error(R.drawable.user)
                    .into(holder.imgPlaydate);
        }
        holder.userName.setText(chatUser.userFullName);

        holder.view.setOnClickListener(view ->
        {
            Intent intent = new Intent(mContext, ChatActivity.class);
            intent.putExtra("chatData", chatUser);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
