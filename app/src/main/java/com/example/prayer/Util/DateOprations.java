package com.example.prayer.Util;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.prayer.Pojo.Responce;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateOprations {

    private String CurrentDate = java.text.DateFormat.getDateTimeInstance().format(new Date());


    @SuppressLint("SimpleDateFormat")
    public float ToDays(String date) {
        SimpleDateFormat dmy = new SimpleDateFormat("MMM DD, yyyy");

        Log.d("DAYES", "ToDays: " + date);

        Date newDate = null;
        try {
            newDate = dmy.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        long seconds = newDate.getTime() / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        Log.d("DAYES", "ToDays: " + date + "=" + (hours / 24));
        return hours / 24;
    }

    public float getProgress(String Strt, float times) {

        float days = ToDays(CurrentDate) - ToDays(Strt);

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


    public String nextPray(List<String> prayTime) {
        String Current = getCurrentHHmm();
        Log.d("nextPray", "nextPray: "+Current);

        String chh = Current.split(":")[0];
        String Cmm = Current.split(":")[1];
        int current = Integer.valueOf(chh);
        int cmm = Integer.valueOf(Cmm);
        String nextPray = null;
        for (int i = 0; i < prayTime.size(); i++) {
            if (i == 1 || i == 3 || i == 7) continue;
            String phh = prayTime.get(i).split(":")[0];
            int Time = Integer.valueOf(phh);
            int dif = Time - current;
            Log.d("nextPray", "nextPray: "+dif +"="+ Time +"-"+ current);
            if (dif > 0) {
                return prayTime.get(i);
            } else if (dif==0){
                String mm = prayTime.get(i).split(":")[1];
                int m = Integer.valueOf(mm);
                if ((m - cmm) > 0) return prayTime.get(i);
            }else if (dif<0&&i==6){
                return prayTime.get(0);
            }
        }


        return nextPray;
    }

}
