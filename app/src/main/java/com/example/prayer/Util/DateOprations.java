package com.example.prayer.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.prayer.Pojo.Responce;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DateOprations {


    Context context;

    public DateOprations(Context context) {
        this.context = context;
    }

    public static final String DATE_FORMAT_2 = "MMM DD, yyyy";


    @SuppressLint("SimpleDateFormat")
    public String getCurrentDate() {

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat dmy = new SimpleDateFormat(DATE_FORMAT_2);
        dmy.setTimeZone(TimeZone.getTimeZone("EET"));
        String date = dmy.format(today);
        Log.d("DAYES", "getCurrentDate: " + date);
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public float ToDays(String date) {
        SimpleDateFormat dmy = new SimpleDateFormat(DATE_FORMAT_2);

        Log.d("DAYES", "ToDays: " + date);

        Date newDate = null;
        try {
            newDate = dmy.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long seconds = 0;
        seconds = newDate.getTime() / 1000;

        long minutes = seconds / 60;
        long hours = minutes / 60;
        Log.d("DAYES", "ToDays: " + date + "=" + (hours / 24));
        return hours / 24;
    }

    public float getProgress(String Strt, float times) {

        float days = ToDays(getCurrentDate()) - ToDays(Strt);

        Log.d("DAYES", "days : " + days);

        if (days != 0) {
            Log.d("DAYES", "times / days 1 : " + (times / days));
            return (times * 100 / days);
        } else
            return (times * 100);

    }

    public String getCurrentHHmm() {


        Calendar calendar = Calendar.getInstance();
        Log.d("getCurrentHHmm", "getCurrentHHmm: " + calendar.get(Calendar.HOUR));
        String hhmm = calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
        String h = hhmm.split(":")[0];
        int hh = Integer.parseInt(h);
        if (calendar.get(Calendar.AM_PM) == Calendar.PM) hh = hh + 12;
        hhmm = hhmm.replace(h, String.valueOf(hh));
        int m = Integer.parseInt(hhmm.split(":")[1]);

        if (m < 10) {
            String n = "0" + m;
            hhmm = hhmm.replace(String.valueOf(m), n);
        }
        if (hh < 10) {
            String n = "0" + hh;
            hhmm = hhmm.replace(String.valueOf(m), n);
        }


        return hhmm;
    }


     String nextPray(List<String> prayTime) {
        String Current = getCurrentHHmm();
        Log.d("nextPray", "nextPray: " + Current);

        String chh = Current.split(":")[0];
        String Cmm = Current.split(":")[1];
        int current = Integer.valueOf(chh);
        int cmm = Integer.valueOf(Cmm);
        for (int i = 0; i < prayTime.size(); i++) {
            if (i == 1 || i == 4 || i == 7) continue;
            String phh = prayTime.get(i).split(":")[0];
            int Time = Integer.valueOf(phh);
            int dif = Time - current;
            Log.d("nextPray", "nextPray: " + dif + "=" + Time + "-" + current);
            if (dif > 0) {
                return prayTime.get(i);
            } else if (dif == 0) {
                String mm = prayTime.get(i).split(":")[1];
                int m = Integer.valueOf(mm);
                if ((m - cmm) > 0) return prayTime.get(i);
            }
        }


        return prayTime.get(0);
    }

    public String todayInMeladi(Responce responce) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String lastTimeStarted = settings.getString("last_Day", null);
        String DateText = responce.getData().getDate().getReadable();
        if (!DateText.equals(lastTimeStarted)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("last_Day", DateText);
            editor.apply();
            return DateText;
        } else
            return lastTimeStarted;
    }


    public String todayInHijri(Responce responce) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String lastTimeHijri = settings.getString("last_Day_Hijri", null);
        String HijriText = responce.getData().getDate().getHijri().getDate();

        if (!HijriText.equals(lastTimeHijri)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("last_Day", HijriText);
            editor.apply();
            return HijriText;
        } else
            return lastTimeHijri;
    }
    public String storedNextPray(List<String> Times) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String lastTimeStarted = settings.getString("last_next_Pray", null);
        String newNxtPray = nextPray(Times);
        if (!newNxtPray.equals(lastTimeStarted)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("last_next_Pray", newNxtPray);
            editor.apply();

            return newNxtPray;
        } else
            return lastTimeStarted;
    }



}
