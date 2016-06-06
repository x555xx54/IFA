package com.example.kiril.micvol;

import android.util.Log;

import java.io.IOException;

/**
 * Created by s36 on 25.05.2016.
 */
public class Worker implements Runnable {

    private static final String LOG_TAG = "mylog";
    SoundMeter soundMeter;
    Boolean status;
    Double vol;

    public Worker(SoundMeter soundMeter, Boolean status, Double vol) {
        this.soundMeter = soundMeter;
        this.status = status;
        this.vol = vol;
    }


    @Override
    public void run() {
        try {
            soundMeter.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (status) {
            vol = soundMeter.getAmplitude();
            Log.d(LOG_TAG, "vol in thread " + vol +" status " + status.toString());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                soundMeter.stop();
                status = false;
                e.printStackTrace();
            } finally {

                //Log.d(LOG_TAG, "finaly");
            }
        }
        soundMeter.stop();
    }
}
