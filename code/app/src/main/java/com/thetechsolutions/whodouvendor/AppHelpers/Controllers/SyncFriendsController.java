package com.thetechsolutions.whodouvendor.AppHelpers.Controllers;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataInsert;

import org.vanguardmatrix.engine.datatypes.PhoneContact;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Uzair on 12/15/2015.
 */
public class SyncFriendsController {

    static ArrayList<PhoneContact> phoneContacts;

    public static String excuteContactListFromPhoneAsyn(Activity activity) {
        String gson = null;
        try {
            gson = new getContactListFromPhone(activity).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return gson;
    }

    public static class getContactListFromPhone extends AsyncTask<String, Void, String> {
        Activity _activity;

        public getContactListFromPhone(Activity activity) {
            _activity = activity;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                phoneContacts = UtilityFunctions.readContacts(_activity);
                String json = "";
                try {
                    if (phoneContacts != null) {
                        try {
                            if (RealmDataInsert.insertContact(phoneContacts)) {

                                json = new Gson().toJson(phoneContacts);
                                return json;
                            }
                        } catch (Exception e) {

                        }

                        //MyLogs.printinfo("json " + json);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
                return "";


            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }

    }
}



