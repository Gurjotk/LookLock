package brdevelopers.com.jobvibe.pushNotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;


import java.util.Calendar;

import brdevelopers.com.jobvibe.R;

public class MyNotificationManager {
    Context context;
    int requestID = 234;
    public MyNotificationManager(Context context) {
        this.context = context;
    }


    public void showNotificationOreo(String title, String message, Intent intent) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            PendingIntent pendingIntent = PendingIntent.getActivity(context, requestID,
                    intent, PendingIntent.FLAG_ONE_SHOT);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            String channelId = context.getString(R.string.app_name);
            String channelName = "Footcraft";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            notificationChannel.canShowBadge();
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(notificationChannel);

            Notification.Builder notificationBuilder = new Notification.Builder(context, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                    .setTicker(title)
                    .setWhen(0)
                    .setStyle(new Notification.BigTextStyle().bigText(message))
                    .setAutoCancel(false)
                    .setContentIntent(pendingIntent)
                    .setContentTitle(title)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setContentText(message);


            notificationBuilder.build().flags = Notification.FLAG_AUTO_CANCEL;
            Notification notification = notificationBuilder.build();
            notificationManager.notify((int) Calendar.getInstance().getTimeInMillis(), notification);
        }
        }

    void showNotification(String title, String message, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestID,
                intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "App");
        notificationBuilder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentText(message)
                .setLights(Color.GREEN,500,500)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setLights(Color.GREEN, 4000, 1000).setPriority(NotificationCompat.PRIORITY_MAX);

        notificationBuilder.build().flags = Notification.FLAG_AUTO_CANCEL;
        Notification notification = notificationBuilder.build();
        notificationManager.notify((int)  Calendar.getInstance().getTimeInMillis(), notification);
    }
}
