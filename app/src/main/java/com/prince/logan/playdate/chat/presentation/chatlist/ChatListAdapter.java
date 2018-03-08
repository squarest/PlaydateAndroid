package com.prince.logan.playdate.chat.presentation.chatlist;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prince.logan.playdate.R;
import com.prince.logan.playdate.entities.PlaydateModel;

import java.util.List;

/**
 * Created by dmitrijfomenko on 06.03.2018.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.Holder> {
    private List<List<PlaydateModel>> chats;
    private ChatListPresenter presenter;
    private Context mContext;

    public ChatListAdapter(List<List<PlaydateModel>> chats, ChatListPresenter presenter) {
        this.chats = chats;
        this.presenter = presenter;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(mContext).inflate(R.layout.chat_list_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        PlaydateModel chat = chats.get(position).get(0);
        String date = String.format("%s %s", chat.monthCreated, chat.dayCreated);
        holder.dateTitle.setText(date);
        holder.chats.setLayoutManager(new LinearLayoutManager(mContext));
        holder.chats.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        ChatSubListAdapter adapter = new ChatSubListAdapter(chats.get(position), presenter);
        holder.chats.setAdapter(adapter);
    }


    @Override
    public int getItemCount() {
        return chats.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView dateTitle;
        RecyclerView chats;

        Holder(View itemView) {
            super(itemView);
            dateTitle = itemView.findViewById(R.id.chat_date);
            chats = itemView.findViewById(R.id.chat_sublist);
        }
    }
}
