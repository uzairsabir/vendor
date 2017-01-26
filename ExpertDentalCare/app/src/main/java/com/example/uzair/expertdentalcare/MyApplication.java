package com.example.uzair.expertdentalcare;

import android.app.Application;
import android.content.ContextWrapper;

import com.pixplicity.easyprefs.library.Prefs;

/**
 * Created by Uzair on 12/18/2016.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }
}
