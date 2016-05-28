package com.apps.alvarobanofos.presentview.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.alvarobanofos.presentview.AnswersActivity;
import com.apps.alvarobanofos.presentview.R;

import java.util.ArrayList;

/**
 * Created by alvarobanofos on 21/5/16.
 */
public class QuestionsRecyclerAdapter extends RecyclerView.Adapter<QuestionsRecyclerAdapter.ViewHolder> {
    private ArrayList<String[]> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;
        public int questionId;
        public ViewHolder(View v) {
            super(v);
            this.view = v;
        }


    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public QuestionsRecyclerAdapter(ArrayList<String[]> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public QuestionsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {


        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_item, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final View view = holder.view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int questionId = Integer.valueOf(mDataset.get(holder.getAdapterPosition())[2]);
                Context context = view.getContext();
                Intent intent = new Intent(context, AnswersActivity.class);
                intent.putExtra("questionId", questionId);
                context.startActivity(intent);


            }
        });
        holder.questionId = Integer.valueOf(mDataset.get(position)[2]);
        TextView question_title = (TextView) view.findViewById(R.id.tv_question_item_title);
        TextView question_datetime = (TextView) view.findViewById(R.id.tv_question_item_datetime);
        question_title.setText(mDataset.get(position)[0]);
        question_datetime.setText(mDataset.get(position)[1]);
        boolean prize = Boolean.valueOf(mDataset.get(position)[3]);
        if(prize) {
            ImageView prize_iv = (ImageView) view.findViewById(R.id.iv_prize);
            prize_iv.setVisibility(View.VISIBLE);

        }



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}



