package com.thetechsolutions.whodouvendor.AppHelpers.Contacts.controllers;

import android.app.Activity;
import android.os.AsyncTask;

import com.thetechsolutions.whodouvendor.AppHelpers.Contacts.models.ContactModel;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.ListenerController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.SyncFriendsController;
import com.thetechsolutions.whodouvendor.AppHelpers.WebService.AsynGetDataController;
import com.thetechsolutions.whodouvendor.Home.activities.HomeMainActivity;
import com.thetechsolutions.whodouvendor.R;
import com.thetechsolutions.whodouvendor.Signup.activities.ContactSyncActivity;

import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

/**
 * Created by Uzair on 7/16/2016.
 */
public class ContactsController {
    String gson;
    int counterHomeService = 0;

    private static ContactsController ourInstance = new ContactsController();

    public static ContactsController getInstance() {

        return ourInstance;
    }

    public static int getIntroLayout(int position) {
        if (position == 0) {
            return R.layout.fragment_sticky_progress_activity_with_search;
        } else
            return R.layout.fragment_listview_progress_activity_with_search;


    }

    public void syncContact(Activity activity) {
        new getSyncContact(activity).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    public class getSyncContact extends AsyncTask<String, Void, Boolean> {
        Activity activity;


        public getSyncContact(Activity _activity) {
            activity = _activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {


                gson = SyncFriendsController.excuteContactListFromPhoneAsyn(activity);
                if (!UtilityFunctions.isEmpty(gson)) {

                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {


                try {
                    AsynGetDataController.getInstance().getMyProvidersOrFriends(activity, 0, false);
                } catch (Exception e) {

                }
                try {
                    AsynGetDataController.getInstance().getMyProvidersOrFriends(activity, 1, false);
                } catch (Exception e) {

                }

                try {
                    AsynGetDataController.getInstance().getSchedules(activity);
                } catch (Exception e) {

                }


            }
        }
    }

    public class sendContctToServer extends AsyncTask<String, Void, Boolean> {

        Activity activity;
        String contactJson = "";


        public sendContctToServer(Activity activity, String json) {
            this.activity = activity;
            contactJson = json;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {

                try {


                    String json = contactJson.replaceAll("\\\\", "");
                    if (!UtilityFunctions.isEmpty(json)) {
                        ContactModel.SyncContacts(json);
                        return true;
                    }


                } catch (Exception e) {

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                AppPreferences.setBoolean(AppPreferences.PREF_CONTACT_SYNC_DONE, true);
                try {
                    ContactSyncActivity.activity.finish();
                } catch (Exception e) {

                }


                try {
                    HomeMainActivity.activity.finish();
                    HomeMainActivity.activity.finish();
                } catch (Exception e) {

                }
                try {

                    ListenerController.openHomeActivity(activity, 0);


                } catch (Exception e) {

                }

                try {
                    AsynGetDataController.getInstance().homeListRefresh(activity, false);
                } catch (Exception e) {

                }


            }
        }
    }

    public void sendContactsToServer(Activity activity) {

        try {
            counterHomeService++;

            if (counterHomeService == 2) {

                new sendContctToServer(activity, gson).execute();
                counterHomeService = 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
