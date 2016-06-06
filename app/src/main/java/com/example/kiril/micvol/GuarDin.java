package com.example.kiril.micvol;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by kir on 04.06.2016.
 */
public class GuarDin implements MyVariable {

    int state;


    int residAlarm;
    int residGuardOn;
    int residGuardOff;

    MediaPlayer mp;
    Context context;
    SmsSender smsSender;


    public GuarDin() {
        residAlarm = R.raw.f6010_04_25;
        residGuardOn = R.raw.guardon_25;
        residGuardOff = R.raw.lay_25;

        smsSender = new SmsSender(context);
    }

    void playSound(int resid) {
        if (mp != null) {
            mp.release();
        }
        mp = new MediaPlayer();
        mp = MediaPlayer.create(context, resid);
    }

    void playSoundAlarm() {
        playSound(residAlarm);
    }

    void playSoundGuardOn() {
        playSound(residGuardOn);
    }

    void playSoundGuardOff() {
        playSound(residGuardOff);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setResidAlarm(int residAlarm) {
        this.residAlarm = residAlarm;
    }

    public void setResidGuardOff(int residGuardOff) {
        this.residGuardOff = residGuardOff;
    }

    public void setResidGuardOn(int residGuardOn) {
        this.residGuardOn = residGuardOn;
    }
}
