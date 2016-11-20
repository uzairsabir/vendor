package org.vanguardmatrix.engine.utils;

import android.util.Log;

public class MyLogs {

    public static void printerror(String message) {
//        if (!Constants.runningMode.equals(ConfigMode.PRODUCTION))
        Log.e("MyLogsError", message);
    }

    public static void printinfo(String message) {
//        if (!Constants.runningMode.equals(ConfigMode.PRODUCTION))
        Log.i("MyLogsInfo", message);
    }

}
