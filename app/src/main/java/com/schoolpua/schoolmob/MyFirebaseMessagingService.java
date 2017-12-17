package com.schoolpua.schoolmob;


import android.os.Bundle;
import android.util.Log;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by hesha on 12/11/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        Log.d("momo", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("momo", "Message data payload: " + remoteMessage.getData());
            Log.d("momo", "Notification message title: " +remoteMessage.getData().get("title"));

            if (true) {scheduleJob();}
            else {handleNow();}
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("momo", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.d("momo", "Message Title: " + remoteMessage.getNotification().getTitle());
        }
        sendNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
    }
    private void scheduleJob() {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder().setService(MyJobService.class).setTag("my-job-tag").build();
        dispatcher.schedule(myJob);
    }

    private void handleNow() {
        Log.d("momo", "Short lived task is done.");
    }

    private void sendNotification(String messageBody,String title) {
        Intent i = new Intent(this, notification.class);
        i.putExtra("messageBody", messageBody);
        i.putExtra("title", title);
        startActivity(i);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, i,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.app_name);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.labelicon)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
