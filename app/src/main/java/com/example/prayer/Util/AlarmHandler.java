package com.example.prayer.Util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.provider.AlarmClock;

import androidx.annotation.RequiresApi;

import com.google.android.datatransport.runtime.scheduling.jobscheduling.AlarmManagerScheduler;
import com.google.android.datatransport.runtime.scheduling.jobscheduling.AlarmManagerSchedulerBroadcastReceiver;

import java.util.Calendar;
import java.util.Objects;

public class AlarmHandler {
    private Context context;

    public AlarmHandler(Context context) {
        this.context = context;
    }


    public void createAlarm(String Time) {
        String hh = Time.split(":")[0];
        String mm = Time.split(":")[1];
        AlarmManager alarmMgr;
        PendingIntent alarmIntent;
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

// Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hh));
        calendar.set(Calendar.MINUTE, Integer.parseInt(mm));

// setRepeating() lets you specify a precise custom interval--in this case,
// 20 minutes.
        Objects.requireNonNull(alarmMgr).setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60, alarmIntent);
        //context.startActivity(intent);

    }


    public void setAlarm(String Time, String name) {
        String hh = Time.split(":")[0];
        String mm = Time.split(":")[1];
        Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
        i.addFlags(0);
        i.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        i.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(hh));
        i.putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(mm));
        i.putExtra(AlarmClock.EXTRA_MESSAGE, name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            i.putExtra(AlarmClock.EXTRA_VIBRATE, true);
        }
        context.startActivity(i);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void stopAlarms(String Time, String name) {
        AlarmReceiver alarmReceiver=new AlarmReceiver();

        String hh = Time.split(":")[0];
        String mm = Time.split(":")[1];

        IntentFilter i = new IntentFilter(AlarmClock.ACTION_DISMISS_ALARM);
        i.addAction(AlarmClock.EXTRA_SKIP_UI);
        i.addAction( AlarmClock.ALARM_SEARCH_MODE_LABEL);

        context.registerReceiver(alarmReceiver,i);


    }


}
