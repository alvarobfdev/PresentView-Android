package com.apps.alvarobanofos.presentview;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.apps.alvarobanofos.presentview.Adapters.PrizesRecyclerAdapter;
import com.apps.alvarobanofos.presentview.Helpers.DbHelper;
import com.apps.alvarobanofos.presentview.Models.Question;
import com.apps.alvarobanofos.presentview.Providers.PresentViewContentProvider;

import java.util.ArrayList;

/**
 * Created by alvarobanofos on 28/5/16.
 */
public class PrizesController extends CustomController {
    Activity activity;
    ViewGroup container;
    RecyclerView mRecyclerView;
    RelativeLayout noRankingLayout;
    RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;



    public PrizesController(Activity activity, ViewGroup container) {
        this.activity = activity;
        this.container = container;
    }

    protected void onCreate() {
        View view = activity.getLayoutInflater().inflate(R.layout.prizes_layout,
                this.container, false);

        container.addView(view);
        this.view = view;
        mRecyclerView = (RecyclerView) view.findViewById(R.id.prizes_recycler_view);
        noRankingLayout = (RelativeLayout) view.findViewById(R.id.noPrizesLayout);
        loadPrizes();

    }

    private void loadPrizes() {
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList<Question> myDataset = getDataset();
        mAdapter = new PrizesRecyclerAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        if(mAdapter.getItemCount() > 0) {
            noRankingLayout.setVisibility(View.GONE);
        }


    }

    private ArrayList<Question> getDataset() {
        ContentResolver resolver = activity.getContentResolver();
        String selection = "winner_user_id = ?";
        String selectionArgs[] = {DbHelper.getInstance(activity).getUser().getUser_id()+""};
        String orderBy = "time_ini DESC";
        Cursor cursor = resolver.query(PresentViewContentProvider.CONTENT_URI_QUESTION, null, selection, selectionArgs, orderBy);
        ArrayList<Question> questions = Question.getQuestionsFromCursor(cursor);
        return questions;

    }

    @Override
    protected void onMessageReceived(Intent intent) {
        super.onMessageReceived(intent);
        loadPrizes();
    }


}
