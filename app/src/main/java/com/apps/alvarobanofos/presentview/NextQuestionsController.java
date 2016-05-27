package com.apps.alvarobanofos.presentview;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.apps.alvarobanofos.presentview.Adapters.QuestionsRecyclerAdapter;
import com.apps.alvarobanofos.presentview.Helpers.DateParser;
import com.apps.alvarobanofos.presentview.Helpers.DbHelper;
import com.apps.alvarobanofos.presentview.Models.Question;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by alvarobanofos on 21/5/16.
 */
public class NextQuestionsController extends CustomController {

    Activity activity;
    ViewGroup container;
    private int overallYScroll = 0;
    private int scrollStatus = 0;



    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Timer timer = null;

    public NextQuestionsController(Activity activity, ViewGroup container) {
        this.activity = activity;
        this.container = container;
    }

    protected void onCreate() {
        View view = activity.getLayoutInflater().inflate(R.layout.recycler_questions,
                this.container, false);

        container.addView(view);
        this.view = view;

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);


        timer = new Timer();
        loadList();


    }

    private void loadList() {
        Log.d("NextQuestionsController", "Loading list...");
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);

        reloadList();
    }

    private void reloadList() {
        // specify an adapter (see also next example)
        ArrayList<Question> questions = DbHelper.getInstance(activity).getNextQuestions();
        ArrayList<String[]> myDataset = new ArrayList<>();

        if(timer != null) {
            timer.cancel();
            timer = new Timer();
        }

        for(Question question: questions) {
            myDataset.add(question.getSimpleDataSet());
            programStatusChange(question, myDataset);
        }


        mAdapter = new QuestionsRecyclerAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void programStatusChange(Question question, final ArrayList<String[]>dataSet) {

        final String[] strings = dataSet.get(dataSet.size()-1);
        if(question.getTime_ini().getTime() > DateParser.now()) {
            Long timeToStart = question.getTime_ini().getTime() - DateParser.now();
            Long timeToEnd = timeToStart + (question.getDuration() * 1000);
            timer.schedule(new TimerTask() {
                public void run() {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            strings[1] = "En curso";
                            mAdapter = new QuestionsRecyclerAdapter(dataSet);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    });

                }
            }, timeToStart);

            timer.schedule(new TimerTask() {
                public void run() {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            strings[1] = "Finalizado";
                            mAdapter = new QuestionsRecyclerAdapter(dataSet);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    });

                }
            }, timeToEnd);
        }
        else if((question.getTime_ini().getTime()+(question.getDuration()*1000)) > DateParser.now()) {
            Log.d("NQC", "HERE");
            Long timeToEnd = (question.getTime_ini().getTime() + question.getDuration() * 1000) - DateParser.now();
            Log.d("NQC", timeToEnd+"");

            timer.schedule(new TimerTask() {
                public void run() {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            strings[1] = "Finalizado";
                            mAdapter = new QuestionsRecyclerAdapter(dataSet);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    });

                }
            }, timeToEnd);
        }
    }

    @Override
    protected void onMessageReceived(Intent intent) {
        reloadList();
    }

    @Override
    protected void onDestroy() {
        if(timer != null) {
            timer.cancel();
        }
        timer = null;
    }
}
