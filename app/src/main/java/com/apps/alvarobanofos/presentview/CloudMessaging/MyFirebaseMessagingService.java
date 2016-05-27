package com.apps.alvarobanofos.presentview.CloudMessaging;

import android.util.Log;

import com.apps.alvarobanofos.presentview.Helpers.DbHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by alvarobanofos on 22/5/16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static String TAG = "MyFirebaseMessagingService";



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Map<String, String> data =  remoteMessage.getData();
        if(data.get("subject").equals("updatedRevision")) {
            Log.d(TAG, "Revision: " + data.get("revision"));

            int revision = Integer.valueOf(data.get("revision"));
            if(revision > DbHelper.getInstance(this).getLocalRevision()) {
                DbHelper.getInstance(this).updateDB(revision);
            }
        }
    }


}
