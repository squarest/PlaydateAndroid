package com.prince.logan.playdate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.prince.logan.playdate.entities.QuestionModel;
import com.prince.logan.playdate.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by PRINCE on 11/16/2017.
 */

public class QaCateAdapter extends ArrayAdapter<QuestionModel> {
    private Context context;
    private int mResource;
    private ArrayList<QuestionModel> itemName;
    ImageView cateImage;
    QuestionModel questionCate;

    public QaCateAdapter(Context context, int itemResourceId,
                           ArrayList<QuestionModel> list) {
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
            v =  LayoutInflater.from(context).inflate(R.layout.adapter_qa_category, null);

        } else {
            v = convertView;
        }
        questionCate = itemName.get(position);

        TextView qaCateTitle = (TextView) v.findViewById(R.id.txt_qa_adap_category);
        ImageView imgCate = (ImageView)v.findViewById(R.id.qa_adap_image);

        Picasso.with(getContext())
                .load(questionCate.getImgCate())
                .placeholder(R.drawable.qa_placeholder) // optional
                .error(R.drawable.qa_placeholder)         // optional
                .into(imgCate);

        qaCateTitle.setText(questionCate.getCategory());

        return v;
    }

}
