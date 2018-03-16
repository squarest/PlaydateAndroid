package com.make.playdate.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.make.playdate.R;
import com.make.playdate.chat.presentation.chat.ChatActivity;

/**
 * Created by Developer on 6/29/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    String title, message;
    public static final int notifyIDActivity = 9001;

    String userId;
    String userName;
    Intent resultIntent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        RemoteMessage.Notification not = remoteMessage.getNotification();

        if (not != null) {
            message = remoteMessage.getNotification().getBody();
            title = remoteMessage.getNotification().getTitle();
            Log.e(TAG, "Data: " + remoteMessage.getData());

          if (remoteMessage.getData().size() > 0) {
              userId = remoteMessage.getData().get("user_id");
              userName = remoteMessage.getData().get("user_name");
          }
            sendNotification();
        }


    }


    private void sendNotification() {
        Context context = getApplicationContext();
        resultIntent=new Intent(this, ChatActivity.class);

        resultIntent.putExtra("user_id", userId);
        resultIntent.putExtra("user_name", userName);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
                resultIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context);
        Notification notification = mBuilder.setSmallIcon(R.drawable.icon).setTicker(message).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentText(message).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notifyIDActivity, notification);
    }
}