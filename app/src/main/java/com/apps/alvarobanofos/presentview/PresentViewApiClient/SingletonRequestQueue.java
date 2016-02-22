package com.apps.alvarobanofos.presentview.PresentViewApiClient;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by alvarobanofos on 20/2/16.
 */
public class SingletonRequestQueue {

    private static RequestQueue requestQueue = null;

    public static RequestQueue getInstance(Context context) {
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }
}
