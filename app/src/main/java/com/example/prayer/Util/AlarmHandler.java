package com.example.prayer.Util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Objects;

public class AlarmHandler {
    Context context;

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


}
