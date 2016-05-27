package com.apps.alvarobanofos.presentview.Controllers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.apps.alvarobanofos.presentview.Providers.PresentViewContentProvider;

/**
 * Created by alvarobanofos on 24/5/16.
 */
public class RevisionController {

    public void updateRevisionDB(Context context, int revision) {

        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("revision", revision);
        resolver.update(Uri.parse(PresentViewContentProvider.URL_REVISIONS), values, null, null);
    }

}
