package com.apps.alvarobanofos.presentview;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apps.alvarobanofos.presentview.Adapters.QuestionsRecyclerAdapter;
import com.apps.alvarobanofos.presentview.Controllers.QuestionsController;
import com.apps.alvarobanofos.presentview.Models.Question;

import java.util.ArrayList;

/**
 * Created by alvarobanofos on 28/5/16.
 */
public class LostQuestionsController extends CustomController {
    Activity activity;
    ViewGroup container;
    RecyclerView mRecyclerView;
    RelativeLayout noQuestionsLayout;
    RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;



    public LostQuestionsController(Activity activity, ViewGroup container) {
        this.activity = activity;
        this.container = container;
    }

    protected void onCreate() {
        View view = activity.getLayoutInflater().inflate(R.layout.recycler_questions,
                this.container, false);

        container.addView(view);
        this.view = view;
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        noQuestionsLayout = (RelativeLayout) view.findViewById(R.id.noQuestionsLayout);
        TextView textViewup = (TextView) view.findViewById(R.id.tv_noquestions_up);
        TextView textViewdown = (TextView) view.findViewById(R.id.tv_noquestions_down);

        textViewup.setText("Aquí aparecen las preguntas no contestadas");
        textViewdown.setText("Sigue así! Cuantas más preguntas contestes, más opciones de premio");

        loadQuestions();

    }

    private void loadQuestions() {
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<Question> questions = QuestionsController.getInstance(activity).getLostQuestions();

        ArrayList<String[]> myDataset = new ArrayList<>();

        for(Question question: questions) {
            myDataset.add(question.getSimpleDataSet());
        }
        mAdapter = new QuestionsRecyclerAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        if(mAdapter.getItemCount() > 0) {
            noQuestionsLayout.setVisibility(View.GONE);
        }


    }


    @Override
    protected void onMessageReceived(Intent intent) {
        super.onMessageReceived(intent);
        loadQuestions();
    }



}
