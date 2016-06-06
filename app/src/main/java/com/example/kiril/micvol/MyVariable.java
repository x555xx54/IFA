package com.example.kiril.micvol;

/**
 * Created by s36 on 31.05.2016.
 */
public interface MyVariable {
    static final String LOG_TAG = "mylog";
    static final String LOG_TAG_FILE = "mylogfile";
    static final String LOG_TAG_SMS = "mylogsms";
    static final String LOG_TAG_MEDIA = "mylogmedia";

    static final int STATE_SYSTEM_START = 1;
    static final int STATE_SYSTEM_STOP = 0;
    static final int STATE_SYSTEM_GUARDON_SMS = 10;
    static final int STATE_SYSTEM_GUARDOFF_SMS = 20;
    static final int STATE_SYSTEM_GUARDON_BUTTON = 30;
    static final int STATE_SYSTEM_GUARDOFF_BUTTON = 40;
    static final int STATE_SYSTEM_ALARMON = 50;
    static final int STATE_SYSTEM_ALARMOFF = 60;

    static final String MSG_GUARD_ON = "guard";
    static final String MSG_GUARD_OFF = "guardstop";
}
