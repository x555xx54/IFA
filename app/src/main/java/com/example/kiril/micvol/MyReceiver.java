package com.example.kiril.micvol;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

import static com.example.kiril.micvol.Main.CONST_STATUS_CODE;
import static com.example.kiril.micvol.Main.CODE_MIC_ON;
import static com.example.kiril.micvol.Main.CODE_MIC_OFF;
import static com.example.kiril.micvol.Main.LOG_TAG;
import static com.example.kiril.micvol.MyService.isMicOn;
import static com.example.kiril.micvol.MyService.isHPhoneOn;


/**
 * Created by s36 on 25.05.2016.
 */
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "MyReceiver onReceive");


        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) ;
        {
            context.startService(new Intent().setComponent(new ComponentName("com.example.kiril.micvol", "com.example.kiril.micvol.MyService")));
        }

        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            Intent intentMic = new Intent().setComponent(new ComponentName("com.example.kiril.micvol", "com.example.kiril.micvol.MyService"));

            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    if (isHPhoneOn) {
                        Log.d(LOG_TAG, "Headset is unplugged");
                        isHPhoneOn = false;
                        isMicOn = false;
                    }
                    break;
                case 1:
                    isHPhoneOn = true;
                    Log.d(LOG_TAG, "Headset is plugged");
                    break;
                default:
                    Log.d(LOG_TAG, "I don't know");
            }

            int mic = intent.getIntExtra("microphone", -1);
            switch (mic) {
                case 0:
                    Log.d(LOG_TAG, "No microphone");
                    intentMic.putExtra(CONST_STATUS_CODE, CODE_MIC_ON);
                    context.startService(intentMic);
                    break;
                case 1:
                    Log.d(LOG_TAG, "Headset with microphone");
                    intentMic.putExtra(CONST_STATUS_CODE, CODE_MIC_OFF);
                    isHPhoneOn = true;
                    break;
            }
        }

        if (intent.getAction().equals(Intent.ACTION_MEDIA_BUTTON)) {

            KeyEvent keyEvent = (KeyEvent) intent.getExtras().get(Intent.EXTRA_KEY_EVENT);
            switch (keyEvent.getKeyCode()) {
                case KeyEvent.KEYCODE_HEADSETHOOK:
                    Log.d(LOG_TAG, "Button A - 79");
                    break;
                case KeyEvent.KEYCODE_VOICE_ASSIST:
                    Log.d(LOG_TAG, "D");
                    break;
                case KeyEvent.KEYCODE_VOLUME_UP:
                    Log.d(LOG_TAG, "B");
                    break;
                case KeyEvent.KEYCODE_VOLUME_DOWN:
                    Log.d(LOG_TAG, "C");
                    break;
            }
        }

    }
}
