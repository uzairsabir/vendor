package com.thetechsolutions.whodouvendor.AppHelpers.Contacts.models;

import com.thetechsolutions.whodouvendor.AppHelpers.Config.AppConstants;
import com.thetechsolutions.whodouvendor.AppHelpers.Contacts.activities.ContactsMainActivity;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataInsert;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouvendor.AppHelpers.WebService.ServiceUrl;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.android.webservice.WebService;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.util.ArrayList;

/**
 * Created by Uzair on 8/20/2016.
 */
public class ContactModel {

    public static boolean SyncContacts(String contactJson) {
        try {
            String id = AppPreferences.getString(AppPreferences.PREF_USER_ID);
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sync_time", UtilityFunctions.getcurrentDateTime()));
            params.add(new BasicNameValuePair("user_id", id));
            params.add(new BasicNameValuePair("contacts", contactJson));
            JSONObject resultJson;
            try {

                resultJson = WebService.callHTTPPost(
                        ServiceUrl.call_sync_contact, params, true)
                        .extractJSONObject();

                try {
                    if (WebService.getResponseCode(resultJson) == 0) {

                        return true;
                    }
                } catch (Exception e) {
                    return true;
                }
                return true;

            } catch (OutOfMemoryError e) {
                e.printStackTrace();

            }
            return false;
        } catch (Exception e) {

        }

        return true;


    }

    public static boolean checkUserExistence(String username, String user_type) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("user_type", user_type));

        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_existense_vendor_consumer, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 1000) {

                //new user
                MyLogs.printinfo("user_status_new ");
                AppPreferences.setString(AppPreferences.PREF_IS_PROVIDER_ALREADY_REGISTERED, AppConstants.USER_NEW);
                return true;

            } else if (WebService.getResponseCode(resultJson) == 1001) {

                //registered user
                MyLogs.printinfo("user_status_registered ");
                AppPreferences.setString(AppPreferences.PREF_IS_PROVIDER_ALREADY_REGISTERED, AppConstants.USER_REGISTERED);

                try {
                    RealmDataInsert.insertVendorProfile(resultJson.getJSONArray(AppConstants.BODY), ContactsMainActivity.tab_pos);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return true;

            } else if (WebService.getResponseCode(resultJson) == 1014) {

                //not on app
                MyLogs.printinfo("user_status_not on app ");
                AppPreferences.setString(AppPreferences.PREF_IS_PROVIDER_ALREADY_REGISTERED, AppConstants.USER_NOT_ON_APP);
                try {
                    RealmDataInsert.insertVendorProfile(resultJson.getJSONArray(AppConstants.BODY), ContactsMainActivity.tab_pos);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;

            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }

    public static boolean checkDeleteConsumerVendorLink(String providerUserName) {

        String consumer_id = String.valueOf(RealmDataRetrive.getColleaguesDetail(providerUserName).getId());
        String vendor_id = AppPreferences.getString(AppPreferences.PREF_USER_ID);
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("friend_id", consumer_id));
        params.add(new BasicNameValuePair("vendor_id", vendor_id));

        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_delete_consumer_vendor_link, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

                return true;

            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }

    public static boolean checkDeleteConsumerFriendLink(String providerUserName) {

        String consumer_id = String.valueOf(RealmDataRetrive.getCustomersDetail(providerUserName).getId());
        String vendor_id = AppPreferences.getString(AppPreferences.PREF_USER_ID);
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("consumer_id", consumer_id));
        params.add(new BasicNameValuePair("vendor_id", vendor_id));

        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_delete_consumer_friend_link, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

                return true;

            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }
}
