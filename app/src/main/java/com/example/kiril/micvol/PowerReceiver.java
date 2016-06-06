package com.example.kiril.micvol;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static com.example.kiril.micvol.Main.CODE_ALARM_STATE_ON;
import static com.example.kiril.micvol.Main.CODE_GUARD_STATE_ON;
import static com.example.kiril.micvol.Main.LOG_TAG;
import static com.example.kiril.micvol.Main.ACTION_PACKAGE;
import static com.example.kiril.micvol.Main.CONST_STATUS_CODE;

/**
 * Created by kir on 30.05.2016.
 */
public class PowerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentPwr2 = new Intent().setComponent(new ComponentName("com.example.kiril.micvol", "com.example.kiril.micvol.MyService"));

        Log.d(LOG_TAG, "PowerReceiver onReceive ");

        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_POWER_CONNECTED)){
            intentPwr2.putExtra(CONST_STATUS_CODE, CODE_ALARM_STATE_ON);
            context.startService(intentPwr2);
            Log.d(LOG_TAG, "PowerReceiver CODE_ALARM_STATE_ON POWER_ON");

        }
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_POWER_DISCONNECTED)){
            intentPwr2.putExtra(CONST_STATUS_CODE, CODE_ALARM_STATE_ON);
            context.startService(intentPwr2);
            Log.d(LOG_TAG, "PowerReceiver CODE_ALARM_STATE_ON POWER_OFF");

        }
    }
}
