package com.example.petcare;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.Application;
import android.app.NotificationChannel;
import android.os.Build;

public class AlarmReceiver extends BroadcastReceiver {

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Your Channel Name";
            String channelDescription = "Your Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("your_channel_id", channelName, importance);
            channel.setDescription(channelDescription);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        int notificationId=intent.getIntExtra("notificationId", 0);
        String message=intent.getStringExtra("todo");

        Intent mainIntent=new Intent(context, Remind.class);
        PendingIntent contentIntent=PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_IMMUTABLE);

        createNotificationChannel(context);
        NotificationManager myNotificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(context, "your_channel_id");
        }
        builder.setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("It's Time!")
                .setContentText(message)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentIntent(contentIntent);

        myNotificationManager.notify(notificationId, builder.build());

    }
}

