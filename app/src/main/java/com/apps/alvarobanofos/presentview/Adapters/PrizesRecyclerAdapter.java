package com.apps.alvarobanofos.presentview.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.alvarobanofos.presentview.Helpers.DateParser;
import com.apps.alvarobanofos.presentview.Models.Question;
import com.apps.alvarobanofos.presentview.R;

import java.util.ArrayList;

/**
 * Created by alvarobanofos on 21/5/16.
 */
public class PrizesRecyclerAdapter extends RecyclerView.Adapter<PrizesRecyclerAdapter.ViewHolder> {
    private ArrayList<Question> mDataset;

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
    public PrizesRecyclerAdapter(ArrayList<Question> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PrizesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {


        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prize_item, parent, false);

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
        TextView prize = (TextView) view.findViewById(R.id.tv_prize_title);
        TextView datetime = (TextView) view.findViewById(R.id.tv_datetime_prize_question);

        String prizeStr = mDataset.get(position).getPrize_title();
        String datetimeStr = DateParser.getStringFromLong(
                mDataset.get(position).getTime_ini().getTime(),
                "dd/MM/yyyy\nHH:mm"
        );

        prize.setText(prizeStr);
        datetime.setText(datetimeStr);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}



