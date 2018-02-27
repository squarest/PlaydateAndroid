package com.prince.logan.playdate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.prince.logan.playdate.Model.QuestionModel;
import com.prince.logan.playdate.Model.SubCateModel;
import com.prince.logan.playdate.R;

import java.util.ArrayList;

/**
 * Created by PRINCE on 11/22/2017.
 */

public class SubCateAdapter extends ArrayAdapter<SubCateModel> {
    private Context context;
    private int mResource;
    private ArrayList<SubCateModel> itemName;
    SubCateModel subCate;
    String categoryTitle;

    public SubCateAdapter(Context context, int itemResourceId,
                           ArrayList<SubCateModel> list, String cateTitle) {
        super(context, itemResourceId, list);
        this.context = context;
        this.mResource = itemResourceId;
        this.itemName = list;
        this.categoryTitle = cateTitle;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = null;
        if (null == convertView) {
//            LayoutInflater inflater = context.getLayoutInflater();
            v =  LayoutInflater.from(context).inflate(R.layout.adapter_sub_cate, null);

        } else {
            v = convertView;
        }
        subCate = itemName.get(position);

        TextView subTitle = (TextView) v.findViewById(R.id.txt_sub_cate_title);
        TextView cateTitle = (TextView) v.findViewById(R.id.txt_category_title);

        subTitle.setText(subCate.getSub_cate());
        cateTitle.setText("("+categoryTitle+")");

        return v;
    }

}