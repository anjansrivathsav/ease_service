package com.ease_service.shobhit.easeservice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){

        Intent i = new Intent(this,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(this);
        notificationbuilder.setContentTitle("FCM Notification");
        notificationbuilder.setContentText(remoteMessage.getNotification().getBody());
        notificationbuilder.setAutoCancel(true);
        notificationbuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationbuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationbuilder.build());

    }
}
