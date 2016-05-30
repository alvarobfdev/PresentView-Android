/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package com.apps.alvarobanofos.presentview;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.apps.alvarobanofos.presentview.Helpers.DbHelper;
import com.apps.alvarobanofos.presentview.PresentViewApiClient.PresentViewApiClient;
import com.apps.alvarobanofos.presentview.PresentViewApiClient.RevisionResult;
import com.apps.alvarobanofos.presentview.common.activities.SampleActivityBase;
import com.apps.alvarobanofos.presentview.common.logger.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class QuestionsActivity extends SampleActivityBase {

    public static final String TAG = "QuestionsActivity";

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        updateDBIfRevisionChanged();


        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SlidingTabsBasicFragment fragment = new SlidingTabsBasicFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, 1001);
        }
        return super.onOptionsItemSelected(item);

    }



    private void updateDBIfRevisionChanged() {

        PresentViewApiClient.JsonApiRequestListener jsonApiRequestListener =
                new PresentViewApiClient.JsonApiRequestListener() {
                    @Override
                    public void jsonApiRequestResult(Object object) {
                        RevisionResult revisionResult = (RevisionResult) object;
                        Log.d(TAG, "LocalRevision: "+DbHelper.getInstance(QuestionsActivity.this).getLocalRevision());
                        if(revisionResult.getRevision() > DbHelper.getInstance(QuestionsActivity.this).getLocalRevision()) {
                            DbHelper.getInstance(QuestionsActivity.this).updateDB(revisionResult.getRevision());
                        }
                    }
                };
        PresentViewApiClient presentViewApiClient = new PresentViewApiClient(
                this,
                jsonApiRequestListener
        );

        Map<String, String> json = new HashMap<>();

        presentViewApiClient.requestJsonApi(PresentViewApiClient.GET_REVISION, new JSONObject(json));
    }

}
