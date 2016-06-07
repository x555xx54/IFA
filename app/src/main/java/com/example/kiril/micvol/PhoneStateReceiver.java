package com.example.kiril.micvol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import static com.example.kiril.micvol.Main.LOG_TAG;


/**
 * Created by kir on 06.06.2016.
 */
public class PhoneStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            TelephonyManager tmgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        }
    }
    class MyPhoneStateListener extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            if (state==1){
                Log.d(LOG_TAG, "MyPhoneListener");
            }
        }
    }
}
