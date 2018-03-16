package com.make.playdate.preference.presentation.pref;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.make.playdate.R;
import com.make.playdate.global.Constant;

import java.util.List;

/**
 * Created by dmitrijfomenko on 16.03.2018.
 */

public class EthnicityAdapter extends RecyclerView.Adapter<EthnicityAdapter.Holder> {
    private List<String> selected;

    public EthnicityAdapter(List<String> selected) {
        this.selected = selected;
    }

    @Override
    public EthnicityAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ethnicity_list_item, parent, false);
        return new EthnicityAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(EthnicityAdapter.Holder holder, int position) {
        holder.ethnicity.setText(Constant.arrayEthnicity[position]);
        if (selected.contains(String.valueOf(position))) holder.checkBox.setChecked(true);
        holder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!b) selected.remove(selected.lastIndexOf(String.valueOf(position)));
            else selected.add(String.valueOf(position));
        });
    }

    @Override
    public int getItemCount() {
        return Constant.arrayEthnicity.length;
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView ethnicity;
        CheckBox checkBox;

        public Holder(View itemView) {
            super(itemView);
            ethnicity = itemView.findViewById(R.id.ethnicity);
            checkBox = itemView.findViewById(R.id.ethnicity_check);
        }
    }
}
