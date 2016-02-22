package com.apps.alvarobanofos.presentview.Helpers;

import android.content.Context;

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
            case -1:
                Notifications.singleToast(context, context.getString(R.string.error_not_network));
        }
    }
}
