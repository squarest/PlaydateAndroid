package com.prince.logan.playdate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.prince.logan.playdate.Model.QuestionModel;
import com.prince.logan.playdate.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by PRINCE on 11/20/2017.
 */

public class QuestionAdapter extends ArrayAdapter<QuestionModel> {
    private Context context;
    private int mResource;
    private ArrayList<QuestionModel> itemName;
    ImageView cateImage;
    QuestionModel questionCate;

    public QuestionAdapter(Context context, int itemResourceId,
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

        TextView checkTitle = (TextView) v.findViewById(R.id.txt_faq_check_list);

        checkTitle.setText(questionCate.getCategory());

        return v;
    }

}
