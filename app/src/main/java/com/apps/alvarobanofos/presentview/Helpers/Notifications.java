package com.apps.alvarobanofos.presentview.Helpers;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by alvarobanofos on 20/2/16.
 */
public class Notifications {


    public static void singleToast(Context context, CharSequence text) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public static void singleToast(Context context, CharSequence text, int duration) {
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
