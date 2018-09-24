package com.suzei.timescheduler.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.VolumeShaper;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.suzei.timescheduler.R;
import com.suzei.timescheduler.view.MainActivity;

import java.util.Calendar;

import timber.log.Timber;

public class AlarmHelper {

    private static final String CHANNEL_ID = "time_scheduler_channel_id";
    private static final String EXTRA_NOTIFICATION = "EXTRA_NOTIFICATION";

    private Context context;
    private String title;

    private AlarmManager alarmManager;

    public AlarmHelper(Context context, String title) {
        this.context = context;
        this.title = title;

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void start(String startTime, String endTime, int startTimeId, int endTimeId) {
        Calendar startTimeCalendar = getStartTimeCalendar(startTime);
        Calendar endTimeCalendar = getStartTimeCalendar(endTime);

        if (startTimeCalendar.before(Calendar.getInstance())) {
            startTimeCalendar.add(Calendar.DATE, 1);
            endTimeCalendar.add(Calendar.DATE, 1);
        }

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                startTimeCalendar.getTimeInMillis(),
                1000 * 60 * 60 * 24,
                getPendingIntent("Started", startTimeId));

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                endTimeCalendar.getTimeInMillis(),
                1000 * 60 * 60 * 24,
                getPendingIntent("Time is up!", endTimeId));
    }

    public void cancel(int id) {
        alarmManager.cancel(getPendingIntent("", id));
    }

    private Calendar getStartTimeCalendar(String startTime) {
        String[] timeArr = startTime.split(":");

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArr[0]));
        c.set(Calendar.MINUTE, Integer.parseInt(timeArr[1]));
        return c;
    }

    private PendingIntent getPendingIntent(String body, int id) {
        String sound = PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString("alarm_tone", "");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.back_arrow)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(Uri.parse(sound));

        Intent broadcastIntent = new Intent(context, AlarmBroadcastReceiver.class);
        broadcastIntent.putExtra(EXTRA_NOTIFICATION, builder.build());

        return PendingIntent.getBroadcast(
                context,
                id,
                broadcastIntent,
                0);
    }

}
