package com.mirea.kt.serviceapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper {
        private final NotificationManagerCompat notificationManager;
        private static final String CHANNEL_ID = "my_channel_id";
        private final Context context;
        public NotificationHelper(Context context) {
            this.context = context;
            this.notificationManager = NotificationManagerCompat.from(context);
        }
        public void showNotification(String title, String message, int notificationId){
            Notification notification = createNotification(title, message, notificationId);
            notificationManager.notify(notificationId, notification);
        }
        public Notification createNotification(String title, String message, int
                notificationId){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My channel", NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("My channel description");
                channel.enableLights(true);
                channel.setLightColor(Color.RED);
                channel.enableVibration(false);
                notificationManager.createNotificationChannel(channel);
            }
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context, CHANNEL_ID)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setPriority(NotificationCompat.PRIORITY_HIGH);
            Notification notification = builder.build();
            return notification;
        }
        public void cancelNotification(int notificationId){
            if(notificationManager != null){
                notificationManager.cancel(notificationId);
            }
        }
    }
