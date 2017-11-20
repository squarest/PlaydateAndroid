package com.prince.logan.playdate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.prince.logan.playdate.Model.UserModel;
import com.prince.logan.playdate.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by PRINCE on 11/19/2017.
 */

public class ChatListAdapter extends ArrayAdapter<UserModel> {
    private Context context;
    private int mResource;
    private ArrayList<UserModel> itemName;
    UserModel chatUser;

    public ChatListAdapter(Context context, int itemResourceId,
                               ArrayList<UserModel> list) {
        super(context, itemResourceId, list);
        this.context = context;
        this.mResource = itemResourceId;
        this.itemName = list;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = null;
        if (null == convertView) {
//            LayoutInflater inflater = context.getLayoutInflater();
            v =  LayoutInflater.from(context).inflate(R.layout.adapter_chat_list, null);

        } else {
            v = convertView;
        }
        chatUser = itemName.get(position);

        TextView userName = (TextView) v.findViewById(R.id.chat_person);
        ImageView imgPlaydate = (ImageView)v.findViewById(R.id.chat_image);

        String avatar_url = chatUser.get_user_avatar();
        String profileImage = avatar_url.replace("http://", "https://");

        Picasso.with(getContext())
                .load(profileImage)
                .placeholder(R.drawable.user) // optional
                .error(R.drawable.user)         // optional
                .into(imgPlaydate);

        userName.setText(chatUser.get_user_full_name());

        return v;
    }
}
