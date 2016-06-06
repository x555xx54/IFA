package com.example.kiril.micvol;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.example.kiril.micvol.Main.CONST_OWNER_TELEPHONE_NUMBER;
import static com.example.kiril.micvol.Main.CONST_SHARED_PREFERENCES;
import static com.example.kiril.micvol.Main.CONST_STATUS_CODE;
import static com.example.kiril.micvol.Main.CODE_SETTING_UPDATE;
import static com.example.kiril.micvol.Main.LOG_TAG;


/**
 * Created by s36 on 31.05.2016.
 */
public class SettingActivity extends PreferenceActivity {

    SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        addPreferencesFromResource(R.layout.activity_setting);

        preference = PreferenceManager.getDefaultSharedPreferences(this);


        PreferenceScreen rootScreen = getPreferenceManager().createPreferenceScreen(this);
        setPreferenceScreen(rootScreen);

        CheckBoxPreference chbPrefSmsEnable = new CheckBoxPreference(this);
        chbPrefSmsEnable.setKey("chbPrefSmsEnable");
        chbPrefSmsEnable.setDefaultValue(false);
        chbPrefSmsEnable.setChecked(false);
        chbPrefSmsEnable.setTitle("СМС оповещеие");
        chbPrefSmsEnable.setSummaryOn("Охранная система будет оповещать о смене состояния по СМС");
        chbPrefSmsEnable.setSummaryOff("Охранная система не будет оповещать о смене состояния по СМС, используйте для настройки");
        chbPrefSmsEnable.setIcon(R.drawable.message_1_32);
        rootScreen.addPreference(chbPrefSmsEnable);


        PreferenceScreen ownerScreen = getPreferenceManager().createPreferenceScreen(this);
        ownerScreen.setKey("ownerScreen");
        ownerScreen.setTitle("Владельцы");
        ownerScreen.setSummary("Список телефонных номеро владельцев для оповещения");
        ownerScreen.setIcon(R.drawable.user_1_32);

//        EditTextPreference etpOwner1 = new EditTextPreference(this);
//        etpOwner1.setKey("etpOwner1");
//        etpOwner1.setTitle(preference.getString("etpOwner1", null));
//        etpOwner1.setIcon(R.drawable.telephone_1_32);
//        ownerScreen.addPreference(etpOwner1);


        EditTextPreference[] etprefArr = new EditTextPreference[10];
        for (int i = 0; i < etprefArr.length; i++) {
            etprefArr[i] = new EditTextPreference(this);
            etprefArr[i].setKey("etpOwner" + i);
            etprefArr[i].setTitle(preference.getString("etpOwner" + i, null));
            etprefArr[i].setIcon(R.drawable.telephone_1_32);
            etprefArr[i].setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    preference.setTitle((CharSequence) newValue);
                    return true;
                }
            });
            ownerScreen.addPreference(etprefArr[i]);
            if (etprefArr[i].getTitle() == null || etprefArr[i].getTitle().equals("")) {
                break;
            }
        }


        rootScreen.addPreference(ownerScreen);


        PreferenceCategory categoryPrefSensor = new PreferenceCategory(this);
        categoryPrefSensor.setKey("chbPrefSmsEnable");
        categoryPrefSensor.setTitle("Датчики");
        categoryPrefSensor.setSummary("Настройка поведения используемых датчиков");
        rootScreen.addPreference(categoryPrefSensor);


        PreferenceScreen sensorScreen = getPreferenceManager().createPreferenceScreen(this);
        sensorScreen.setKey("sensorScreen");
        sensorScreen.setTitle("Заряд");
        sensorScreen.setSummary("Использования датчика зарядки устройства");
        sensorScreen.setIcon(R.drawable.usb_1_32);


        PreferenceCategory categoryPrefSensorPwrOn = new PreferenceCategory(this);
        categoryPrefSensorPwrOn.setKey("categoryPrefSensorPwrOn");
        categoryPrefSensorPwrOn.setTitle("Заряд On");
        categoryPrefSensorPwrOn.setSummary("Настройка поведения используемых датчиков");
        sensorScreen.addPreference(categoryPrefSensorPwrOn);


        PreferenceCategory categoryPrefSensorPwrOff = new PreferenceCategory(this);
        categoryPrefSensorPwrOff.setKey("categoryPrefSensorPwrOff");
        categoryPrefSensorPwrOff.setTitle("Заряд Off");
        categoryPrefSensorPwrOff.setSummary("Настройка поведения используемых датчиков");

        sensorScreen.addPreference(categoryPrefSensorPwrOff);

        rootScreen.addPreference(sensorScreen);

        PreferenceScreen wordScreen = getPreferenceManager().createPreferenceScreen(this);
        wordScreen.setKey("wordScreen");
        wordScreen.setTitle("Управляюище слова");
        wordScreen.setSummary("Укажите кодовые слова, которые будут включать и выключать охранную систему");
        wordScreen.setIcon(R.drawable.signup_1_32);

        EditTextPreference etpGuardOn = new EditTextPreference(this);
        etpGuardOn.setKey("etpGuardOn");
        etpGuardOn.setTitle("Указано \"" + preference.getString("etpGuardOn", null) + "\"");
        etpGuardOn.setSummary("Слово, которое будет ставить систему в режим охраны");
        etpGuardOn.setText("text");
        etpGuardOn.setDialogIcon(R.drawable.lockon_1_32);
        etpGuardOn.setDialogTitle("Кодовое слово включения");
        etpGuardOn.setIcon(R.drawable.lockon_1_32);
        etpGuardOn.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setTitle((CharSequence) newValue);
                return true;
            }
        });
        wordScreen.addPreference(etpGuardOn);


        EditTextPreference etpGuardOff = new EditTextPreference(this);
        etpGuardOff.setKey("etpGuardOff");
        etpGuardOff.setTitle("Указано \"" + preference.getString("etpGuardOff", null) + "\"");
        etpGuardOff.setSummary("Слово, которое будет ставить систему в режим охраны");
        etpGuardOff.setDialogIcon(R.drawable.lockoff_1_32);
        etpGuardOff.setDialogTitle("Кодовое слово включения");
        etpGuardOff.setIcon(R.drawable.lockoff_1_32);
        etpGuardOff.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setTitle((CharSequence) newValue);
                return true;
            }
        });
        wordScreen.addPreference(etpGuardOff);


        File folder;
        folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "MyNewFolder");


        if (!folder.exists()) {
            folder.mkdir();
        }


        List<File> files = new ArrayList<>();
        File sdCardRoot = Environment.getExternalStorageDirectory();
        File yourDir = new File(sdCardRoot, "MyNewFolder");


        if (yourDir.listFiles() != null) {
            for (File f : yourDir.listFiles()) {
                if (f.isFile()) {
                    files.add(f);
                }
            }

            String[] entry = new String[files.size()];
            String[] entryValue = new String[files.size()];

            for (int i = 0; i < files.size(); i++) {
                entry[i] = files.get(i).getName();
                entryValue[i] = String.valueOf(i);
            }


            ListPreference lpAlarmSound = new ListPreference(this);
            lpAlarmSound.setKey("lpAlarmSound");
            lpAlarmSound.setTitle("Оповещение");
            lpAlarmSound.setSummary("Выберите сигнал тревоги");
            lpAlarmSound.setIcon(R.drawable.ringtone_1_32);
            lpAlarmSound.setEntries(entry);
            lpAlarmSound.setEntryValues(entryValue);

//        lpAlarmSound.setEntries(R.array.entries);
//        lpAlarmSound.setEntryValues(R.array.entry_values);
/*


        PreferenceScreen soundAlarmScreen = getPreferenceManager().createPreferenceScreen(this);
        soundAlarmScreen.setKey("wordScreen");
        soundAlarmScreen.setTitle("Управляюище слова");
        soundAlarmScreen.setSummary("Укажите кодовые слова, которые будут включать и выключать охранную систему");
        soundAlarmScreen.setIcon(R.drawable.bell_1_32);
*/


            //soundAlarmScreen.addPreference(lpAlarmSound);

            rootScreen.addPreference(lpAlarmSound);
        }

        rootScreen.addPreference(wordScreen);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "SettingActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Intent intent = new Intent(this, MyService.class);
        intent.putExtra(CONST_STATUS_CODE, CODE_SETTING_UPDATE);
        startService(intent);
    }

    class EditTextMyVersion extends EditTextPreference {


        public EditTextMyVersion(Context context) {
            super(context);
        }


    }
}
