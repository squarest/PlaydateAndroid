package com.prince.logan.playdate.preference;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.prince.logan.playdate.preference.PreferenceItemActivity;
import com.prince.logan.playdate.entities.PreferenceItemModel;
import com.prince.logan.playdate.R;

import java.util.ArrayList;

/**
 * Created by PRINCE on 12/6/2017.
 */

public class PreferenceItemAdapter extends ArrayAdapter<PreferenceItemModel> {
    private Context context;
    private int mResource;
    private ArrayList<PreferenceItemModel> itemName;
    PreferenceItemModel preItem;

    public PreferenceItemAdapter(Context context, int itemResourceId,
                               ArrayList<PreferenceItemModel> list) {
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
            v =  LayoutInflater.from(context).inflate(R.layout.adapter_preference_item, null);

        } else {
            v = convertView;
        }
        preItem = itemName.get(position);

        TextView title = (TextView) v.findViewById(R.id.adap_pItem_text);
        CheckBox itemChecked = (CheckBox)v.findViewById(R.id.adap_check_item);

        title.setText(preItem.getTitle());
        if (preItem.getChecked()==1){
            itemChecked.setChecked(true);
        }
        else{
            itemChecked.setChecked(false);
        }

        itemChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    PreferenceItemActivity.arrayPlist.get(position).setChecked(1);
                }
                else{
                    PreferenceItemActivity.arrayPlist.get(position).setChecked(0);
                }

            }
        });
        return v;
    }
}
