package com.make.playdate.playdate.presentation.list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pkmmte.view.CircularImageView;
import com.make.playdate.R;
import com.make.playdate.entities.PlaydateModel;
import com.make.playdate.playdate.presentation.details.PlaydateDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by dmitrijfomenko on 07.03.2018.
 */

public class PlaydateSliderAdapter extends RecyclerView.Adapter<PlaydateSliderAdapter.Holder> {
    private List<PlaydateModel> mData;
    private Context mContext;

    public PlaydateSliderAdapter(List<PlaydateModel> mData) {
        this.mData = mData;
    }

    public void addNew(PlaydateModel playdateModel) {
        Collections.reverse(mData);
        mData.add(playdateModel);
        Collections.reverse(mData);
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playdate_slider_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        PlaydateModel playdateModel = mData.get(position);
        String avatar_url = playdateModel.avatarUrl;
        if (avatar_url != null && !avatar_url.isEmpty()) {
            String profileImage = avatar_url.replace("http://", "https://");
            Picasso.with(mContext)
                    .load(profileImage)
                    .placeholder(R.drawable.user)
                    .error(R.drawable.user)
                    .into(holder.avatar);
        }
        holder.avatar.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, PlaydateDetailActivity.class);
            intent.putExtra("playdateId", playdateModel.firebaseId);
            mContext.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        CircularImageView avatar;

        public Holder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.playdate_user_image);
        }
    }
}
