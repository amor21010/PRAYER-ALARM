package com.example.prayer.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

class AlarmReceiver extends BroadcastReceiver {
    private static AlarmReceiver INCTANCE;


    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Time Up... Now Vibrating !!!",
                Toast.LENGTH_LONG).show();
        Vibrator vibrator = (Vibrator) context
                .getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(2000);
        }

    }

    public static AlarmReceiver getInstance() {
        if (INCTANCE == null) {
            INCTANCE = new AlarmReceiver();
        }
        return INCTANCE;
    }

    public AlarmReceiver() {

    }
}
