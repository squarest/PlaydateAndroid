package com.make.playdate.playdate.presentation.details;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.make.playdate.R;
import com.make.playdate.entities.QAModel;

import java.util.List;

/**
 * Created by dmitrijfomenko on 09.03.2018.
 */

public class PlaydateAnswersAdapter extends RecyclerView.Adapter<PlaydateAnswersAdapter.Holder> {
    private List<QAModel> questions;

    public PlaydateAnswersAdapter(List<QAModel> questions) {
        this.questions = questions;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_list_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.answer.setText(questions.get(position).answer);
        holder.question.setText(questions.get(position).question);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView question;
        TextView answer;

        public Holder(View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            answer = itemView.findViewById(R.id.answer);
        }
    }
}
