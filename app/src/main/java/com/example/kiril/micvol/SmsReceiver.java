package com.example.kiril.micvol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import static com.example.kiril.micvol.Main.ACTION_PACKAGE;
import static com.example.kiril.micvol.Main.SMS_RECEIVE_ACTION;
import static com.example.kiril.micvol.Main.CONST_STATUS_CODE;
import static com.example.kiril.micvol.Main.CODE_GUARD_STATE_ON;
import static com.example.kiril.micvol.Main.CODE_GUARD_STATE_OFF;
import static com.example.kiril.micvol.Main.MSG_GUARD_ON;
import static com.example.kiril.micvol.Main.MSG_GUARD_OFF;
import static com.example.kiril.micvol.Main.LOG_TAG;

/**
 * Created by s36 on 25.05.2016.
 */

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "SmsReceiver onReceive");

        if (intent.getAction().equalsIgnoreCase(SMS_RECEIVE_ACTION)) {
            String smsText = new String();
            StringBuilder stringBuilder = new StringBuilder();
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Intent intentSms = new Intent(context, MyService.class);

                Object[] pdus = (Object[]) bundle.get("pdus");
                for (Object pdu : pdus) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                    stringBuilder.append("adress " + smsMessage.getOriginatingAddress());
                    stringBuilder.append(" text " + smsMessage.getMessageBody());

                    smsText = smsMessage.getMessageBody();
                }
                Log.d(LOG_TAG, stringBuilder.toString());
//                Log.d(LOG_TAG, "SmsReceiver MSG_GUARD_ON " + MSG_GUARD_ON.toLowerCase());

//                if (stringBuilder.toString().toLowerCase().contains(MSG_GUARD_ON.toLowerCase())) {

                if (smsText.equalsIgnoreCase(MSG_GUARD_ON)) {
                    intentSms.putExtra(CONST_STATUS_CODE, CODE_GUARD_STATE_ON);
                    context.startService(intentSms);
                    Log.d(LOG_TAG, "SmsReceiver SMS MSG_GUARD_STATE_ON");
                }

                if (smsText.equalsIgnoreCase(MSG_GUARD_OFF)) {
                    intentSms.putExtra(CONST_STATUS_CODE, CODE_GUARD_STATE_OFF);
                    context.startService(intentSms);
                    Log.d(LOG_TAG, "SmsReceiver SMS CODE_GUARD_STATE_OFF");
                }
            }
        }
    }
}
