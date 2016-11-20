package com.thetechsolutions.whodouvendor.Signup.controllers;

import android.app.Activity;

import com.thetechsolutions.whodouvendor.AppHelpers.Config.AppConstants;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataInsert;
import com.thetechsolutions.whodouvendor.AppHelpers.WebService.Parser;
import com.thetechsolutions.whodouvendor.AppHelpers.WebService.ServiceUrl;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.android.webservice.WebService;
import org.vanguardmatrix.engine.utils.MyLogs;

import java.util.ArrayList;

/**
 * Created by Uzair on 8/14/2016.
 */
public class SignUpModel {
    private static SignUpModel ourInstance = new SignUpModel();

    public static SignUpModel getInstance() {
        return ourInstance;
    }

    private SignUpModel() {
    }

    public boolean checkUserExistence(String username) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("user_type", AppConstants.APP_TYPE));

        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_user_existense, params, true)
                    .extractJSONObject();

            try {
                AppPreferences.setString(AppPreferences.PREF_USER_NUMBER, username);
            } catch (Exception e) {

            }


            if (WebService.getResponseCode(resultJson) == 1000) {

                //new user
                AppPreferences.setString(AppPreferences.PREF_USER_STATUS, AppConstants.USER_NEW);
                AppPreferences.setString(AppPreferences.PREF_USER_LOGIN_TYPE, AppConstants.SIGNUP);
                return true;

            } else if (WebService.getResponseCode(resultJson) == 1001) {
                AppPreferences.setString(AppPreferences.PREF_USER_STATUS, AppConstants.USER_REGISTERED);
                AppPreferences.setString(AppPreferences.PREF_USER_LOGIN_TYPE, AppConstants.VERIFY);
                //registered user

//                try {
//                    //RealmDataInsert.insertProfile(resultJson.getJSONArray(AppConstants.BODY));
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                return true;

            } else if (WebService.getResponseCode(resultJson) == 1014) {
                AppPreferences.setString(AppPreferences.PREF_USER_STATUS, AppConstants.USER_NOT_ON_APP);
                AppPreferences.setString(AppPreferences.PREF_USER_LOGIN_TYPE, AppConstants.SIGNUP);
                //not on app
                try {
                    RealmDataInsert.insertProfile(resultJson.getJSONArray(AppConstants.BODY));

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


    protected boolean signUp(String email, String firstName, String lastName, String zipCode,String subcategory_id) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("email", "" + email));
        params.add(new BasicNameValuePair("firstname", "" + firstName));
        params.add(new BasicNameValuePair("lastname", "" + lastName));
        params.add(new BasicNameValuePair("zipcode", "" + zipCode));
        params.add(new BasicNameValuePair("username", "" + AppPreferences.getString(AppPreferences.PREF_USER_NUMBER)));
        params.add(new BasicNameValuePair("login_type", AppPreferences.getString(AppPreferences.PREF_USER_LOGIN_TYPE)));
        params.add(new BasicNameValuePair("user_type", AppConstants.APP_TYPE));
        params.add(new BasicNameValuePair("subcategory_id",subcategory_id));
        JSONObject resultJson;
        try {


            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_sign_up, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

                return true;
            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }

    protected boolean codeVerification(Activity activity,String code) {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", "" + AppPreferences.getString(AppPreferences.PREF_USER_NUMBER)));
        params.add(new BasicNameValuePair("login_type", AppPreferences.getString(AppPreferences.PREF_USER_LOGIN_TYPE)));
        params.add(new BasicNameValuePair("user_type", AppConstants.APP_TYPE));
        params.add(new BasicNameValuePair("verification_code", code));
        params.add(new BasicNameValuePair("is_registered_user", AppPreferences.getString(AppPreferences.PREF_USER_STATUS)));
        JSONObject resultJson;
        try {


            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_code_verification, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

                try {

                    RealmDataInsert.insertProfile(resultJson.getJSONArray(AppConstants.BODY));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                try {
//                    activity.startActivity(ConversationActivity.createIntent(activity));
//                } catch (Exception e) {
//
//                }
//
//                try {
//                    MagicCreateActivity obj = new MagicCreateActivity();
//                    obj.createXmppConnection("123456", "1234");
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }


                return true;
            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }

    protected boolean getProfile() {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", "" + AppPreferences.getString(AppPreferences.PREF_USER_NUMBER)));
        JSONObject resultJson;
        try {


            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_get_profile, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

                return Parser.insertProfile(resultJson);
            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }

    public boolean vendorCategories() {

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", AppPreferences.getString(AppPreferences.PREF_USER_NUMBER)));
        JSONObject resultJson;
        try {

            resultJson = WebService.callHTTPPost(
                    ServiceUrl.call_vendor_categories, params, true)
                    .extractJSONObject();

            if (WebService.getResponseCode(resultJson) == 0) {

                try {
                    RealmDataInsert.insertCategories(resultJson.getJSONArray(AppConstants.BODY));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //return true;
            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();

        }
        return false;

    }


}
