package com.apps.alvarobanofos.presentview.Receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.apps.alvarobanofos.presentview.QuestionsActivity;
import com.apps.alvarobanofos.presentview.R;

public class OnetimeAlarmReceiver extends BroadcastReceiver {

   public static final int QUESTION_NOTIFICATION = 12001;

  public void onReceive(Context context, Intent intent) {

      Bundle bundle = intent.getExtras();
      String questionTitle = bundle.getString("questionTitle");

      NotificationCompat.Builder mBuilder =
              new NotificationCompat.Builder(context)
                      .setSmallIcon(R.drawable.ic_launcher_presentview)
                      .setContentTitle(questionTitle)
                      .setContentText("Esta pregunta va a ser lanzada en menos de 30s.");
// Creates an explicit intent for an Activity in your app
      Intent resultIntent = new Intent(context, QuestionsActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
      TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
      stackBuilder.addParentStack(QuestionsActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
      stackBuilder.addNextIntent(resultIntent);
      PendingIntent resultPendingIntent =
              stackBuilder.getPendingIntent(
                      0,
                      PendingIntent.FLAG_UPDATE_CURRENT
              );
      mBuilder.setContentIntent(resultPendingIntent);
      mBuilder.setAutoCancel(true);
      NotificationManager mNotificationManager =
              (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
      mNotificationManager.notify(QUESTION_NOTIFICATION, mBuilder.build());
   }
 }