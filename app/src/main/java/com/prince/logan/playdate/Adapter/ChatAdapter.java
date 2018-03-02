package com.prince.logan.playdate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.prince.logan.playdate.main.presentation.main.MainActivity;
import com.prince.logan.playdate.entities.ChatData;
import com.prince.logan.playdate.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ChatAdapter extends ArrayAdapter<ChatData> {

    private final static int TYPE_MY_SELF = 0;
    private final static int TYPE_ANOTHER = 1;

    public ChatAdapter(Context context, int resource) {
        super(context, resource);
    }

    private View setAnotherView(LayoutInflater inflater) {
        View convertView = inflater.inflate(R.layout.adapter_chat, null);
        ViewHolderAnother holder = new ViewHolderAnother();
        holder.bindView(convertView);
        convertView.setTag(holder);
        return convertView;
    }

    private View setMySelfView(LayoutInflater inflater) {
        View convertView = inflater.inflate(R.layout.adapter_chat_my, null);
        ViewHolderMySelf holder = new ViewHolderMySelf();
        holder.bindView(convertView);
        convertView.setTag(holder);
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (convertView == null) {
            if (viewType == TYPE_ANOTHER) {
                convertView = setAnotherView(inflater);
            } else {
                convertView = setMySelfView(inflater);
            }
        }

        if (convertView.getTag() instanceof ViewHolderAnother) {
            if (viewType != TYPE_ANOTHER) {
                convertView = setAnotherView(inflater);
            }
            ((ViewHolderAnother) convertView.getTag()).setData(position);
        } else {
            if (viewType != TYPE_MY_SELF) {
                convertView = setMySelfView(inflater);
            }
            ((ViewHolderMySelf) convertView.getTag()).setData(position);
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
         ChatData user = getItem(position);
        if (MainActivity.userFirebaseID.equals(user.senderId)) {
            return TYPE_MY_SELF;
        } else {
            return TYPE_ANOTHER;
        }
    }

    private class ViewHolderAnother {
        private ImageView mImgProfile;
        private TextView mTxtUserName;
        private TextView mTxtMessage;
        private TextView mTxtTime;

        private void bindView(View convertView) {
            mImgProfile = (ImageView) convertView.findViewById(R.id.img_profile);
            mTxtUserName = (TextView) convertView.findViewById(R.id.txt_userName);
            mTxtMessage = (TextView) convertView.findViewById(R.id.txt_message);
            mTxtTime = (TextView) convertView.findViewById(R.id.txt_time);
        }

        private void setData(int position) {
            ChatData chatData = getItem(position);

            mTxtMessage.setText(chatData.text);
            mTxtTime.setText(getDate(chatData.date));
        }
    }

    private class ViewHolderMySelf {
        private TextView mTxtMessage;
        private TextView mTxtTime;

        private void bindView(View convertView) {
            mTxtMessage = (TextView) convertView.findViewById(R.id.txt_message);
            mTxtTime = (TextView) convertView.findViewById(R.id.txt_time);
        }

        private void setData(int position) {
            ChatData chatData = getItem(position);

            mTxtMessage.setText(chatData.text);
            mTxtTime.setText(getDate(chatData.date));
        }
    }

    private String getDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        try {
            value = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
        dateFormatter.setTimeZone(TimeZone.getDefault());
        String dt = dateFormatter.format(value);

        return dt;
    }
}