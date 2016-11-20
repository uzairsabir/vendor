package org.vanguardmatrix.engine.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.vanguardmatrix.engine.android.AppPreferences;

import java.io.IOException;

/**
 * Created by Uzair on 12/12/2015.
 */
public class GCMController {
    static GoogleCloudMessaging gcm;
    static String regid;
    static String SENDER_ID = "324611973215";

    //343262953399
    //AIzaSyC3oWcqq9ekcfZnBgdw8y64NqzKcnD4uWs

    public static String API_KEY_SERVER_GCM = "AIzaSyB-SaQj94o4sRQco6k9Oa_1TlNUe92rdpU";

   // public static final String PROJECT_ID = "324611973215";
    //public static final String SERVER_KEY = "AIzaSyB-SaQj94o4sRQco6k9Oa_1TlNUe92rdpU";




    public static boolean CreateGCMID(Activity activity) {

        if (UtilityFunctions.checkPlayServices(activity)) {
            gcm = GoogleCloudMessaging.getInstance(activity);
            regid = AppPreferences.getString(AppPreferences.PREF_GCM_ID);
            Log.e("reg id : ", " : " + regid);
            if (UtilityFunctions.isEmpty(regid)) {
                registerInBackground(activity);
                if (UtilityFunctions.isEmpty(regid)) {
                    return false;
                } else {
                    return true;
                }
            } else {
                Log.e("reg id all ready : ", " : " + regid);
                return true;
            }
        }

        return false;
    }

    private static void registerInBackground(Activity activity) {
        if (UtilityFunctions.checkInternet(activity)) {
            new Register4GCMAsyncTask(activity).execute();
        } else {
            UtilityFunctions.showToast_onCenter("Please check your internet connection", activity);
        }

    }


    private static class Register4GCMAsyncTask extends AsyncTask<String, Void, String> {

        private Activity _activity;

        public Register4GCMAsyncTask(Activity activity) {
            _activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... values) {
            String msg = "";
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(_activity);
                }
                regid = gcm.register(SENDER_ID);

            } catch (IOException ex) {
                msg = "" + ex.getMessage();
                ex.printStackTrace();
            } catch (Exception e) {
                msg = "" + e.getMessage();
                e.printStackTrace();
            }
            return regid;
        }

        @Override
        protected void onPostExecute(String msg) {
            if (regid.equals("")) {
                UtilityFunctions.showToast_onCenter("Please check your internet connection", _activity);
            } else {
                AppPreferences.setString(AppPreferences.PREF_GCM_ID, regid);
                MyLogs.printerror("reg id " + regid);
            }
        }
    }


}
