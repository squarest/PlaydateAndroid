package com.prince.logan.playdate.FCM;

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
import com.prince.logan.playdate.Activity.ChatActivity;
import com.prince.logan.playdate.Activity.MainActivity;
import com.prince.logan.playdate.R;

import org.json.JSONObject;

/**
 * Created by Developer on 6/29/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    JSONObject jsonObject = null;
    String title, message;
    public static final int notifyIDActivity = 9001;

    String userId;
    String userName;
    NotificationCompat.Builder builder;
    Intent resultIntent;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

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
    // [END receive_message]


    private void sendNotification() {
        Context context = getApplicationContext();
//        final String appPackageName = newAppName; // getPackageName() from Context or Activity object
//        try {
//            resultIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.softedia." + appPackageName));
//        } catch (android.content.ActivityNotFoundException anfe) {
//            resultIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.softedia." + appPackageName));
//        }
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