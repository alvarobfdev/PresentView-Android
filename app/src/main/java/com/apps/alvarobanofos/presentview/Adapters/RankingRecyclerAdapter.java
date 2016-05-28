package com.apps.alvarobanofos.presentview.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.alvarobanofos.presentview.PresentViewApiClient.RankingResult;
import com.apps.alvarobanofos.presentview.R;

/**
 * Created by alvarobanofos on 21/5/16.
 */
public class RankingRecyclerAdapter extends RecyclerView.Adapter<RankingRecyclerAdapter.ViewHolder> {
    private RankingResult mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View view;

        public ViewHolder(View v) {
            super(v);
            this.view = v;
        }


    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RankingRecyclerAdapter(RankingResult myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RankingRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {


        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ranking_item, parent, false);

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
        TextView user = (TextView) view.findViewById(R.id.tv_user_ranking);
        TextView numberQuestions = (TextView) view.findViewById(R.id.tv_questions_ranking);
        TextView positionTv = (TextView) view.findViewById(R.id.tv_position_ranking);
        String userStr = mDataset.getRankings().get(position).getUser();
        String numQuestions = mDataset.getRankings().get(position).getNumQuestions()+" preguntas.";
        String positionStr = mDataset.getRankings().get(position).getPosition()+"";

        if(mDataset.getRankings().get(position).isMe()) {
            userStr = "(YO)" + userStr;
        }
        positionTv.setText(positionStr);
        user.setText(userStr);
        numberQuestions.setText(numQuestions);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.getRankings().size();
    }
}



