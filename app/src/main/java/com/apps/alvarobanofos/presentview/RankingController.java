package com.apps.alvarobanofos.presentview;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.apps.alvarobanofos.presentview.Adapters.RankingRecyclerAdapter;
import com.apps.alvarobanofos.presentview.Helpers.DbHelper;
import com.apps.alvarobanofos.presentview.PresentViewApiClient.PresentViewApiClient;
import com.apps.alvarobanofos.presentview.PresentViewApiClient.RankingResult;
import com.apps.alvarobanofos.presentview.Providers.PresentViewContentProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alvarobanofos on 27/5/16.
 */
public class RankingController extends CustomController {

    Activity activity;
    ViewGroup container;
    RecyclerView mRecyclerView;
    RelativeLayout noRankingLayout;
    RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;


    public RankingController(){
        //Empty controller needed
    }

    public RankingController(Activity activity, ViewGroup container) {
        this.activity = activity;
        this.container = container;
    }

    protected void onCreate() {
        View view = activity.getLayoutInflater().inflate(R.layout.ranking_layout,
                this.container, false);

        container.addView(view);
        this.view = view;

        mRecyclerView = (RecyclerView) view.findViewById(R.id.ranking_recycler_view);
        noRankingLayout = (RelativeLayout) view.findViewById(R.id.noRankingLayout);

        loadRanking();

    }

    private void loadRanking() {
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RankingResult myDataset = getDataset();
        mAdapter = new RankingRecyclerAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        if(mAdapter.getItemCount() > 0) {
            noRankingLayout.setVisibility(View.GONE);
        }


    }


    private RankingResult getDataset() {
        String json = DbHelper.getInstance(activity).getRankingJson();
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, RankingResult.class);
    }

    @Override
    protected void onMessageReceived(Intent intent) {
        super.onMessageReceived(intent);
        loadRanking();
    }

    public void updateRankingDB(final Context context) {

        PresentViewApiClient.JsonApiRequestListener jsonApiRequestListener = new PresentViewApiClient.JsonApiRequestListener() {
            @Override
            public void jsonApiRequestResult(Object object) {
                String json = (String)object;
                ContentResolver resolver = context.getContentResolver();
                ContentValues values = new ContentValues();
                values.put("json", json);
                resolver.update(Uri.parse(PresentViewContentProvider.URL_RANKING), values, null, null);
            }
        };

        PresentViewApiClient presentViewApiClient = new PresentViewApiClient(activity, jsonApiRequestListener);
        presentViewApiClient.setTransformResult(false);
        Map<String, String> json = new HashMap<>();
        json.put("userToken", DbHelper.getInstance(context).getUser().getToken());
        presentViewApiClient.requestJsonApi(PresentViewApiClient.GET_RANKING, new JSONObject(json));

    }
}
