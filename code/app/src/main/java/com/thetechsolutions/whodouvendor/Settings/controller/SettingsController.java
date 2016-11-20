package com.thetechsolutions.whodouvendor.Settings.controller;

import android.app.Activity;
import android.os.AsyncTask;

import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouvendor.R;
import com.thetechsolutions.whodouvendor.Settings.model.SettingModel;

/**
 * Created by Uzair on 7/16/2016.
 */
public class SettingsController {

    public static void updateProfile(Activity activity, String providerName, String first_name, String last_name,
                                     String user_address, String user_city, String user_state, String user_country,
                                     String email_address, String zip_code, String subcategory_id, String imageUrl, int pos) {

        new updateProfile(activity, providerName, first_name, last_name, user_address, user_city, user_state, user_country, email_address,
                zip_code, subcategory_id, imageUrl, pos).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    public static void updatePreference(Activity activity, String preference_id, String preference_value) {

        new updatePreference(activity, preference_id, preference_value).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }


    private static class updateProfile extends AsyncTask<String, Void, Integer> {

        Activity activity;
        String providerName, first_name, last_name, user_address, user_city, user_state, user_country, email_address,
                zip_code, subcategory_id, imageUrl;
        int pos;

        public updateProfile(Activity _activity, String _providerName, String _first_name, String _last_name,
                             String _user_address, String _user_city, String _user_state, String _user_country,
                             String _email_address, String _zip_code, String _subcategory_id, String _imageUrl, int _pos) {
            activity = _activity;
            providerName = _providerName;
            first_name = _first_name;
            last_name = _last_name;
            user_address = _user_address;
            user_city = _user_city;
            user_state = _user_state;
            user_country = _user_country;
            email_address = _email_address;
            zip_code = _zip_code;
            subcategory_id = _subcategory_id;
            imageUrl = _imageUrl;
            pos = _pos;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppController.showDialoge(activity);


        }

        @Override
        protected Integer doInBackground(String... params) {

            try {

                SettingModel.updateProfile(providerName, first_name, last_name, user_address, user_city, user_state, user_country, email_address,
                        zip_code, subcategory_id, imageUrl, pos);


                return 0;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 4;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 0) {

                AppController.showToast(activity, activity.getResources().getString(R.string.updated_successfully));
                AppController.hideDialoge();
            } else {
                AppController.showToast(activity, activity.getResources().getString(R.string.went_wrong));
            }


        }


    }

    private static class updatePreference extends AsyncTask<String, Void, Integer> {

        Activity activity;
        String preference_id, preference_value;


        public updatePreference(Activity _activity, String _preference_id, String _preference_value) {
            activity = _activity;
            preference_id = _preference_id;
            preference_value = _preference_value;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //AppController.showDialoge(activity);


        }

        @Override
        protected Integer doInBackground(String... params) {

            try {

                SettingModel.updatePreference(preference_id, preference_value);


                return 0;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 4;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 0) {

            } else {
                AppController.showToast(activity, activity.getResources().getString(R.string.went_wrong));
            }


        }


    }

}
