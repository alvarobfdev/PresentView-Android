package com.apps.alvarobanofos.presentview.Helpers;

import android.content.Context;
import android.widget.Toast;

import com.apps.alvarobanofos.presentview.R;

/**
 * Created by alvarobanofos on 20/2/16.
 */
public class NetworkErrors {

    public static void throwNetworkErrors(Context context, int statusCode) {
        switch (statusCode) {
            case 404:
                Notifications.singleToast(context, context.getString(R.string.error_404));
                break;
            case 500:
                Notifications.singleToast(context, context.getString(R.string.error_404));
                break;
            case 400:
                Notifications.singleToast(context, context.getString(R.string.error_400), Toast.LENGTH_LONG);
                break;
            case -1:
                Notifications.singleToast(context, context.getString(R.string.error_not_network));
        }
    }
}
