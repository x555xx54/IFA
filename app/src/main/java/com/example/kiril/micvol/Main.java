package com.example.kiril.micvol;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import static com.example.kiril.micvol.MyService.isGuard;
import static com.example.kiril.micvol.MyService.isMicOn;
import static com.example.kiril.micvol.MyService.isHPhoneOn;

import java.io.IOException;
import java.util.HashSet;


public class Main extends AppCompatActivity {



    static final int CODE_GUARD_STATE_ON = 10;
    static final int CODE_GUARD_STATE_OFF = 20;
    static final int CODE_ALARM_STATE_ON = 30;
    static final int CODE_ALARM_STATE_OFF = 40;

    static final int CODE_HEADSET_ON = 50;
    static final int CODE_HEADSET_OFF = 60;

    static final int CODE_MIC_ON = 70;
    static final int CODE_MIC_OFF = 80;

    static final int CODE_SETTING_UPDATE = 90;


    static final String ACTION_PACKAGE = "com.example.kiril.micvol";
    static final String SMS_RECEIVE_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    static final String CONST_STATUS_CODE = "status_code";
    static final String CONST_OWNER_TELEPHONE_NUMBER = "owner_telephone";
    static final String CONST_SHARED_PREFERENCES = "myPreferences";
    static final String CONST_MSG_GUARD_ON= "guardOn";
    static final String CONST_MSG_GUARD_OFF = "guardOff";

    static final String LOG_TAG = "mylog";
    static final String MSG_GUARD_ON = "guard";
    static final String MSG_GUARD_OFF = "guardstop";

    //SharedPreferences preferences;


    TextView tvVol, tvGuardStatus;
    MediaPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvGuardStatus = (TextView) findViewById(R.id.tvGuardStatus);
       // tvVol = (TextView) findViewById(R.id.tvVol);

/*
        HashSet<String> ownerSet = new HashSet<>();
        ownerSet.add("+79130055113");
        ownerSet.add("+79137965444");


        preferences = getSharedPreferences(CONST_SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(CONST_OWNER_TELEPHONE_NUMBER, ownerSet);
        editor.putString(CONST_MSG_GUARD_ON, "guardstart");
        editor.putString(CONST_MSG_GUARD_OFF,"guardstop");
        editor.commit();*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        tvGuardStatus.setText(String.valueOf(isGuard));
    }

    public void onClickGuard(View v) throws IOException, InterruptedException {
        Intent intent = new Intent().setComponent(new ComponentName("com.example.kiril.micvol", "com.example.kiril.micvol.MyService"));

        switch (v.getId()) {
            case R.id.btnGuardOn:
                //Thread.sleep(3000);
                intent.putExtra(CONST_STATUS_CODE, CODE_GUARD_STATE_ON);
                break;

            case R.id.btnGuardOff:
                intent.putExtra(CONST_STATUS_CODE, CODE_GUARD_STATE_OFF);
                break;
        }
        startService(intent);
        Log.d(LOG_TAG, "Btn click, startService(" + intent.getIntExtra(CONST_STATUS_CODE, -1) + ")");
        tvGuardStatus.setText(String.valueOf(isGuard));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem menuItem = menu.add(0,1,0, "Настройки");
        menuItem.setIntent(new Intent(this,SettingActivity.class));

        return super.onCreateOptionsMenu(menu);

    }
/*public void onClickBnt(View v) {
        switch (v.getId()) {
            case R.id.btnHsStatus:
                tvVol.setText(String.valueOf("Headset " + isHPhoneOn + ", microphone is " + isMicOn));


                Intent intent = new Intent().setComponent(new ComponentName("com.example.kiril.micvol", "com.example.kiril.micvol.MyService"));

                SmsManager sms = SmsManager.getDefault();
                PendingIntent sentPI;
                String SENT = "SMS_SEND";

                sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);

                sms.sendTextMessage("+79130055113", null, "Test_SMS_AUTOSEND", sentPI, null);


                break;

            case R.id.btnPlay:
                player = null;
                player = MediaPlayer.create(this, R.raw.file);
                player.start();
                break;

            case R.id.btnMuzStop:
                if (player != null) {
                    player.stop();
                    player.release();
                    player = null;
                }
                break;
        }
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
   /*     soundMeter.stop();
        unregisterReceiver(myReceiver);
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }*/
    }


}
