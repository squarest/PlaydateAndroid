package com.make.playdate.questions.presentation.questions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.make.playdate.R;
import com.make.playdate.entities.QuestionModel;

import java.util.ArrayList;

/**
 * Created by dmitrijfomenko on 15.03.2018.
 */

public class SwipeDeckAdapter extends ArrayAdapter<QuestionModel> {

    private ArrayList<QuestionModel> data;
    private QuestionModel questions;

    public SwipeDeckAdapter(Context context, int itemResourceId,
                            ArrayList<QuestionModel> list) {
        super(context, itemResourceId, list);
        this.data = list;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public QuestionModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            v = inflater.inflate(R.layout.card_item, parent, false);
        }

        questions = data.get(position);

        TextView txt_cate_question = v.findViewById(R.id.card_cate_question);
        TextView txt_question = v.findViewById(R.id.card_txt_question);
        String item = questions.getQuestions();
        txt_question.setText(item);
        int questionIndex = position + 1;
        txt_cate_question.setText("Question " + questionIndex);

        return v;
    }
}