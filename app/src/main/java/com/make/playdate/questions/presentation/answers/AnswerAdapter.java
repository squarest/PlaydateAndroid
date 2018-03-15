package com.make.playdate.questions.presentation.answers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.make.playdate.R;
import com.make.playdate.entities.QAModel;

import java.util.List;

/**
 * Created by PRINCE on 11/28/2017.
 */

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.Holder> {
    private List<QAModel> questions;
    private Context context;

    public AnswerAdapter(List<QAModel> questions) {
        this.questions = questions;
    }

    @Override
    public AnswerAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.view_answer_list_item, parent, false);
        return new AnswerAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(AnswerAdapter.Holder holder, int position) {
        String answer = questions.get(position).answer;
        if (answer.equals("Yes"))
            holder.answer.setImageDrawable(context.getResources().getDrawable(R.drawable.check));
        else holder.answer.setImageDrawable(context.getResources().getDrawable(R.drawable.uncheck));
        holder.question.setText(questions.get(position).question);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView question;
        ImageView answer;

        public Holder(View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            answer = itemView.findViewById(R.id.answer);
        }
    }

}
