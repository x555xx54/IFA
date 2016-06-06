package com.example.kiril.micvol;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.example.kiril.micvol.Main.CODE_SETTING_UPDATE;
import static com.example.kiril.micvol.Main.CONST_OWNER_TELEPHONE_NUMBER;
import static com.example.kiril.micvol.Main.CONST_STATUS_CODE;
import static com.example.kiril.micvol.Main.CONST_SHARED_PREFERENCES;
import static com.example.kiril.micvol.Main.CODE_GUARD_STATE_ON;
import static com.example.kiril.micvol.Main.CODE_GUARD_STATE_OFF;
import static com.example.kiril.micvol.Main.CODE_ALARM_STATE_ON;
import static com.example.kiril.micvol.Main.CODE_ALARM_STATE_OFF;

import static com.example.kiril.micvol.Main.LOG_TAG;


/**
 * Created by s36 on 26.05.2016.
 */
public class MyService extends Service {

    boolean ENABLE_SMS;

    final String FILENAME = "fffile";
    int count = 0;
    static final String CONST_GUARD_STATE = "guardState";

    static boolean isGuard;
    static boolean isMicOn;
    static boolean isHPhoneOn;
    static boolean isAlarm;

    MediaPlayer mp;
    MyReceiver myReceiver;

    SharedPreferences preferences;

    SmsSender smsSender;

    @Override
    public void onCreate() {
        super.onCreate();

        isAlarm = false;

        // writeFile();

        Log.d(LOG_TAG, "MyService onCreate");
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(myReceiver, intentFilter);

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakelockTag");
        wakeLock.acquire();


        smsSender = new SmsSender(this);


        preferences = PreferenceManager.getDefaultSharedPreferences(this);


        File folder;
        folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "MyNewFolder");

        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        Log.d(LOG_TAG, "MyFile list create");

//        List<File> files = new ArrayList<>();
//        File sdCardRoot = Environment.getExternalStorageDirectory();
//        File yourDir = new File(sdCardRoot, "MyNewFolder");
//        for (File f : yourDir.listFiles()){
//            Log.d(LOG_TAG, "for File "+f.getName());
//
//            if (f.isFile()) {
//                files.add(f);
//                Log.d(LOG_TAG, "MyFile list "+f.getName());
//            }
//        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            Log.d(LOG_TAG, "intent = null");
            return super.onStartCommand(intent, flags, startId);
        }
        if (intent.getIntExtra(CONST_STATUS_CODE, -1) == CODE_SETTING_UPDATE) {

            ENABLE_SMS = preferences.getBoolean("chbPrefSmsEnable", true);
        }
        Log.d(LOG_TAG, String.valueOf("ENABLE_SMS " + ENABLE_SMS));

        Log.d(LOG_TAG, "MyService onStartCommand isGuard " + isGuard);
        Context context = getApplicationContext();

        if (intent.getIntExtra(CONST_STATUS_CODE, -1) == CODE_GUARD_STATE_ON) {
            if (!isGuard) {
                isGuard = true;
                Log.d(LOG_TAG, "MyService - guard on");

                mp = new MediaPlayer();
                mp = MediaPlayer.create(context, R.raw.guardon_25);

                mp.setLooping(false);
                mp.start();
                if (ENABLE_SMS) {
                    smsSender.sendSMS("+79137965444", "Охрана вкл");
                }
            }
        }

        if (intent.getIntExtra(CONST_STATUS_CODE, -1) == CODE_GUARD_STATE_OFF) {

            if (mp != null) {
                mp.stop();
                mp.release();
                mp = null;
            }
            Log.d(LOG_TAG, "MyService - alarm off");

            if (isGuard && isAlarm) {
                count++;
                if (ENABLE_SMS) {
                    //smsSender.sendSMS("+79130055113", "Охрана выкл");
                    smsSender.sendSMS("+79137965444", "Охрана выкл");
                }
            }

            if (!isAlarm) {
                mp = new MediaPlayer();
                mp = MediaPlayer.create(context, R.raw.guardon_25);
                mp.setLooping(false);

                mp.start();
            }
            isGuard = false;
            isAlarm = false;
        }

        if (intent.getIntExtra(CONST_STATUS_CODE, -1) == CODE_ALARM_STATE_ON) {

            if (isGuard && !isAlarm) {
                if (mp != null) {
                    mp.stop();
                    mp.release();
                }
                mp = new MediaPlayer();

                mp = MediaPlayer.create(context, R.raw.f6010_04_25);
                mp.setLooping(true);
                mp.start();

                isAlarm = true;
                isAlarm = true;
                count++;
                Log.d(LOG_TAG, "MyService - alarm on");
                if (ENABLE_SMS) {
                    smsSender.sendSMS("+79130055113", "Замок открыт");
                    smsSender.sendSMS("+79137965444", "Замок открыт");
                }


//                try {
//                    AssetFileDescriptor descriptor = context.getAssets().openFd("file.mp3");
//                    mp.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
//                    descriptor.close();
//                    mp.prepare();
//                    mp.setLooping(true);
//                    mp.setVolume(3, 3);
//                    mp.start();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (IllegalArgumentException e) {
//                    e.printStackTrace();
//                } catch (IllegalStateException e) {
//                    e.printStackTrace();
//                }

            }
        }

        Toast.makeText(context, String.valueOf(count) + " isGuard " + isGuard + "\n isAlarm " + isAlarm, Toast.LENGTH_SHORT).show();

        Log.d(LOG_TAG, "isGuard " + isGuard + " isAlarm " + isAlarm);

        return Service.START_STICKY;
        //        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        preferences = getSharedPreferences(CONST_SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(CONST_GUARD_STATE, isGuard);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    void writeFile() {
        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(FILENAME, MODE_APPEND)));
            // пишем данные
            bw.write("Содержимое файла");
            // закрываем поток
            bw.close();
            Log.d(LOG_TAG, "Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void readFile() {
        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                Log.d(LOG_TAG, str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


//        String path = getApplication().getApplicationContext().getFilesDir().getAbsolutePath();
//        //File file = new File(path + "/" + fileName);
//        Log.d(LOG_TAG, "AbsolutePath" + path);


/*

        if (intent.getIntExtra(CONST_STATUS_CODE, -1) == CODE_MIC_ON) {

            isMicOn = true;


            Log.d(LOG_TAG, "MyService mic on, isMicOn");
            Toast.makeText(this, "MyService mic on, isMicOn", Toast.LENGTH_SHORT).show();

            if (isGuard) {
                if (mp != null) {
                    mp.release();
                }
                mp = new MediaPlayer();
                mp = MediaPlayer.create(this, R.raw.file);
                mp.setLooping(true);
                mp.start();
                Log.d(LOG_TAG, "MyService mic on, play alarm");
                Toast.makeText(this, "MyService mic on, play alarm", Toast.LENGTH_LONG).show();
            }
        }


        if (intent.getIntExtra(CONST_STATUS_CODE, -1) == CODE_MIC_OFF) {

            isMicOn = false;

            if (mp != null) {
                mp.release();
                mp = null;
            }
            Log.d(LOG_TAG, "MyService mic off, alarm off");
            Toast.makeText(this, "MyService mic off, alarm off", Toast.LENGTH_LONG).show();
        }

        if (intent.getIntExtra(CONST_STATUS_CODE, -1) == CODE_HEADSET_ON) {

            isHPhoneOn = true;

            if (isGuard) {
                if (mp != null) {
                    mp.release();
                }
                mp = new MediaPlayer();
                mp = MediaPlayer.create(this, R.raw.file);
                mp.start();
                Log.d(LOG_TAG, "MyService headphone on, play alarm");
                Toast.makeText(this, "MyService headphone on, play alarm", Toast.LENGTH_LONG).show();
            }
        }

        if (intent.getIntExtra(CONST_STATUS_CODE, -1) == CODE_HEADSET_OFF) {

            isHPhoneOn = false;

            if (mp != null) {
                mp.release();
                mp = null;
            }
            Log.d(LOG_TAG, "MyService headphone off, play no alarm");
            Toast.makeText(this, "MyService headphone off, play no alarm", Toast.LENGTH_LONG).show();
        }

        */



        /*preferences = getSharedPreferences(CONST_SHARED_PREFERENCES, MODE_PRIVATE);
        HashSet<String> ownerSet = (HashSet) preferences.getStringSet(CONST_OWNER_TELEPHONE_NUMBER, null);

        if (ownerSet != null) {
            for (String owner : ownerSet) {
                Log.d(LOG_TAG, owner);
            }
        } else {
            Log.d(LOG_TAG, "ownerSet is null");
        }
        isGuard = preferences.getBoolean(CONST_GUARD_STATE, true);*/