package com.example.kiril.micvol;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;



/**
 * Created by kir on 29.05.2016.
 */
public class SmsSender {

    Context context;

    public SmsSender(Context context) {
        this.context = context;
    }

    void sendSMS(String number, String msg) {
        //Intent intent = new Intent().setComponent(new ComponentName("com.example.kiril.micvol", "com.example.kiril.micvol.MyService"));
        Intent intent = new Intent(context, MyService.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);

        SmsManager sms = SmsManager.getDefault();
        if (sms != null) {
            sms.sendTextMessage(number, null, msg, pi, null);
            if (false) {

            }
        }
    }


/*private void sendSMSDefault() {
        Intent intent = new Intent().setComponent(new ComponentName("com.example.kiril.micvol", "com.example.kiril.micvol.MyService"));
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);


        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage("+79130055113", null, "Test_SMS_AUTOSEND", pi, null);

    }*/
//        SmsManager.getDefault().sendTextMessage(+7, null, "Test_SMS_AUTOSEND", null, null);



       /* SmsManager sms = SmsManager.getDefault();
        PendingIntent sentPI;
        String SENT = "SMS_SEND";

        sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);

        sms.sendTextMessage("+79130055113", null, "Test_SMS_AUTOSEND", sentPI, null);
        */

}
