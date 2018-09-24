package com.suzei.timescheduler.alarm;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 83927;
    private static final String EXTRA_NOTIFICATION = "EXTRA_NOTIFICATION";

    @Override
    public void onReceive(Context context, Intent intent) {
        Notification notification = intent.getParcelableExtra(EXTRA_NOTIFICATION);

        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.notify(NOTIFICATION_ID, notification);
    }

}
