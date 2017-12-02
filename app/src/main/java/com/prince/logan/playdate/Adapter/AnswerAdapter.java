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
 * Created by PRINCE on 11/28/2017.
 */

public class AnswerAdapter extends ArrayAdapter<QuestionModel> {
    private Context context;
    private int mResource;
    private ArrayList<QuestionModel> itemName;
    ImageView cateImage;
    QuestionModel questionCate;

    public AnswerAdapter(Context context, int itemResourceId,
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
            v =  LayoutInflater.from(context).inflate(R.layout.adapter_playdate_detail, null);

        } else {
            v = convertView;
        }
        questionCate = itemName.get(position);

        TextView txtQuestion = (TextView) v.findViewById(R.id.txt_faq_check_list);
        ImageView img_check = (ImageView)v.findViewById(R.id.img_faq_check_uncheck);

        if(questionCate.getIs_answer() == 0){
            img_check.setImageResource(R.drawable.uncheck);
        }
        else{
            img_check.setImageResource(R.drawable.check);
        }

        txtQuestion.setText(questionCate.getQuestions());

        return v;
    }

}
