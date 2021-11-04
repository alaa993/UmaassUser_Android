package net.umaass_user.app.servicess;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;


import net.umaass_user.app.R;
import net.umaass_user.app.ui.ActivitySplash;

import java.util.UUID;

public class NotificationConteroller {

    private static NotificationConteroller INSTANCE;

    public static NotificationConteroller getInstance() {
        if (INSTANCE == null) {
            synchronized (NotificationConteroller.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NotificationConteroller();
                }
            }
        }
        return INSTANCE;
    }

    public void showNotification(Context context, String title, String message) {
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String cId = UUID.randomUUID().toString();
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(cId,
                                                                  "CHANNEL_NAME",
                                                                  NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("DISCRIPTION");
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            channel.setSound(alarmSound, audioAttributes);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, cId)
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle(title) // title for notification
                .setContentText(message)// message for notification
                .setSound(alarmSound)
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(context, ActivitySplash.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        if (mNotificationManager != null) {
            mNotificationManager.notify(0, mBuilder.build());
        }
    }
}
