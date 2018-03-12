package com.make.playdate.help.faq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.make.playdate.R;

import java.util.ArrayList;

/**
 * Created by PRINCE on 11/15/2017.
 */

public class FaqAdapter extends ArrayAdapter<String> {
    private Context context;
    private int mResource;
    private ArrayList<String> itemName;
    String faq;

    public FaqAdapter(Context context, int itemResourceId,
                        ArrayList<String> list) {
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
            v =  LayoutInflater.from(context).inflate(R.layout.adapter_faq, null);

        } else {
            v = convertView;
        }
        faq = itemName.get(position);
        TextView faqText = (TextView) v.findViewById(R.id.txt_adpater_faq_item);
        faqText.setText(faq);

        return v;
    }
}
