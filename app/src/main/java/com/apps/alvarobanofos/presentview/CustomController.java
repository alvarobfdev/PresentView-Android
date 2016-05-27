package com.apps.alvarobanofos.presentview;

import android.content.Intent;
import android.view.View;

/**
 * Created by alvarobanofos on 22/5/16.
 */
public class CustomController {
    View view;
    protected void onCreate() {};
    protected void onDestroy() {};
    protected View getView() { return view; }
    protected void onMessageReceived(Intent intent) {};
}
