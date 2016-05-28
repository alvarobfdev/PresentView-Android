package com.apps.alvarobanofos.presentview.Adapters;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apps.alvarobanofos.presentview.Dialogs.ConfirmAnswerDialog;
import com.apps.alvarobanofos.presentview.Helpers.DateParser;
import com.apps.alvarobanofos.presentview.Helpers.ImageLoader;
import com.apps.alvarobanofos.presentview.Models.Question;
import com.apps.alvarobanofos.presentview.R;

import java.util.ArrayList;

/**
 * Created by alvarobanofos on 21/5/16.
 */
public class AnswersRecyclerAdapter extends RecyclerView.Adapter<AnswersRecyclerAdapter.ViewHolder> {
    private ArrayList<String[]> mDataset;
    private Activity activity;
    private Question question;

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
    public AnswersRecyclerAdapter(ArrayList<String[]> myDataset, Activity activity, Question question) {
        mDataset = myDataset;
        this.activity = activity;
        this.question = question;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AnswersRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {


        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answer_item, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final View view = holder.view;
        boolean selectable = Boolean.valueOf(mDataset.get(position)[4]);



        view.setOnClickListener(new View.OnClickListener() {





            @Override
            public void onClick(View v) {
                long time_ini = question.getTime_ini().getTime();
                long time_end = time_ini + (question.getDuration()*1000);
                long now = DateParser.now();
                if(now >= time_ini && now <= time_end && !question.isFinished()) {
                    ConfirmAnswerDialog confirmAnswerDialog = ConfirmAnswerDialog.newInstance(
                            mDataset.get(position)[0],
                            Integer.valueOf(mDataset.get(position)[2])
                    );
                    android.app.FragmentManager fm = activity.getFragmentManager();
                    confirmAnswerDialog.show(activity.getFragmentManager(), "fragment_edit_name");
                }
            }
        });
        TextView answer_title = (TextView) view.findViewById(R.id.tv_answer);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_answer);
        answer_title.setText(mDataset.get(position)[0]);
        ImageLoader imgLoader = new ImageLoader(view.getContext());

        if(selectable) {
            answer_title.setTextColor(activity.getResources().getColor(R.color.colorSecondaryButton));
        }

        if(mDataset.get(position)[1].length() > 0) {
            imgLoader.DisplayImage(mDataset.get(position)[1], R.drawable.loading, imageView);
        }
        else {
            imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.loading));
        }
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.pb_percentage);
        TextView textView = (TextView) view.findViewById(R.id.tv_percentage);

        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        if(question.isFinished()) {
            progressBar.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            setPercetages(progressBar, textView, Double.valueOf(mDataset.get(position)[3]));

            if(selectable) {
                view.setSelected(true);
            }

        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void setPercetages(ProgressBar progressBar, TextView tv,  double percentage) {
        progressBar.setMax(10000);
        tv.setText(percentage+"%");
        percentage = percentage*100;
        Double d = Double.valueOf(percentage);

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", d.intValue());
        animation.setDuration(2000); // 0.5 second
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

    }
}



