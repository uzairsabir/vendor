package org.vanguardmatrix.engine.android;

import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.vanguardmatrix.engine.datatypes.SocailSignedUserObject;
import org.vanguardmatrix.engine.datatypes.UserObject;

import java.util.ArrayList;
import java.util.HashSet;

public class AppPreferences {

    public static final String BEST_LOCATION = "BEST_LOCATION";
    public static final String PREF_DEVICE_WIDTH = "device-width";
    public static final String PREF_DEVICE_DEPTH = "device-depth";
    public static final String PREF_DEVICE_HEIGHT = "device-height";
    public static final String PREF_GCM_ID = "PREF_GCM_ID";
    public static final String PREF_USER_OBJECT = "PREF_USER_OBJECT";
    public static final String PREF_FACEBOOK_USER_OBJECT = "PREF_FACEBOOK_USER_OBJECT";
    public static final String PREF_IS_SIGNUP_DONE = "PREF_IS_SIGNUP_DONE";
    public static final String PREF_DOWNLOAD_FILE = "OCT_DM_FILE_";
    public static final String PREF_USER_PHONE_NO = "user_phone_no";
    private static final int ALERT_FREQUENCY = 2 * 60000; // 2 minute
    public static final String PREF_CONTACT_SYNC_DONE = "PREF_CONTACT_SYNC_DONE";
    public static final String PREF_REGISTERATION_DONE = "PREF_REGISTERATION_DONE";

    public static final String PREF_ACCOUNT_SETTING_YES = "PREF_ACCOUNT_SETTING_YES";
    public static final String PREF_IS_SETTING_ITEM_IS_CLICKED = "PREF_IS_SETTING_ITEM_IS_CLICKED";


    public static final String PREF_USER_COUNTRY_CODE = "PREF_USER_COUNTRY_CODE";
    public static final String PREF_IS_INTRO_COMPLETED = "PREF_IS_INTRO_COMPLETED";
    public static final String PREF_IS_DUMY_CONTENT_INSERT = "PREF_IS_DUMY_CONTENT_INSERT";

    public static final String PREF_VENDOR_CATEGORY_ID = "PREF_VENDOR_CATEGORY_ID";
    public static final String PREF_VENDOR_SUB_CATEGORY_ID = "PREF_VENDOR_SUB_CATEGORY_ID";

    public static final String PREF_USER_NUMBER = "PREF_USER_NUMBER";
    public static final String PREF_USER_STATUS = "PREF_USER_STATUS";
    public static final String PREF_USER_LOGIN_TYPE = "PREF_USER_LOGIN_TYPE";
    public static final String PREF_IS_PROVIDER_ALREADY_REGISTERED = "PREF_IS_PROVIDER_ALREADY_REGISTERED";
    public static final String PREF_USER_ID= "PREF_USER_ID";
//    public static final String PREF_USER_COUNTRY_CODE = "PREF_USER_COUNTRY_CODE";

    public static final String PREF_MY_PROVIDERS_NUMBERS = "PREF_MY_PROVIDERS_NUMBERS";
    public static final String PREF_MY_FRIENDS_NUMBERS = "PREF_MY_FRIENDS_NUMBERS";

    private static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(Application
                .getInstance());
    }

    public static void setBoolean(String key, boolean value) {
        try {
            getSharedPreferences().edit().putBoolean(key, value).commit();
        } catch (Exception e) {
            System.out.print("exception while setting Preference Value: "
                    + value + " for key " + key);
        }
    }

    public static boolean getBoolean(String key) {
        try {
            return getSharedPreferences().getBoolean(key, false);
        } catch (Exception e) {
            System.out.println("Couldn't retrieve Preference Key.");
            e.printStackTrace();
            return false;
        }
    }

    public static void setInt(String key, int value) {

        getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        try {
            return getSharedPreferences().getInt(key, 0);
        } catch (Exception e) {
            System.out.println("Couldn't retrieve Preference Key.");
            e.printStackTrace();
            return -1;
        }
    }

    public static void setString(String key, String value) {
        getSharedPreferences().edit().putString(key, value).commit();
    }

    public static String getString(String key) {

        try {
            return getSharedPreferences().getString(key, "");
        } catch (Exception e) {
            System.out.println("Couldn't retrieve Preference Key.");
            e.printStackTrace();
            return "";
        }
    }

    public static void setLong(String key, long value) {
        getSharedPreferences().edit().putLong(key, value).commit();
    }

    public static long getLong(String key) {

        try {
            return getSharedPreferences().getLong(key, -1);
        } catch (Exception e) {
            System.out.println("Couldn't retrieve Preference Key.");
            e.printStackTrace();
            return -1;
        }
    }

    public static void setStringArray(String key, ArrayList<String> values) {
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            getSharedPreferences().edit().putString(key, a.toString()).commit();
        } else {
            getSharedPreferences().edit().putString(key, null).commit();
        }
    }




    public static void setStringArray(String key, HashSet<String> values) {
        JSONArray a = new JSONArray();
        for (String x : values) {
            a.put(x);
        }
        if (!values.isEmpty()) {
            getSharedPreferences().edit().putString(key, a.toString()).commit();
        } else {
            getSharedPreferences().edit().putString(key, null).commit();
        }
    }

    public static ArrayList<String> getStringArray(String key) {
        String json = getSharedPreferences().getString(key, null);
        ArrayList<String> urls = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    public static Location getCurrentBestLocation() {

        String locationJson = getSharedPreferences().getString(BEST_LOCATION, "");

        Location x = new Location("");
        x.setLatitude(0.0);
        x.setLongitude(0.0);

        if (locationJson.equals(""))
            return x;

        return (locationJson == null) ? x : constructLocation(locationJson);
    }

    public static void setCurrentBestLocation(Location location) {
        setString(BEST_LOCATION, new Gson().toJson(location));
    }

    public static UserObject getUserObject() {

        String locationJson = getSharedPreferences().getString(PREF_USER_OBJECT, "");

        return createUserObj(locationJson);
    }

    public static void setUserObject(UserObject userObject) {
        setString(PREF_USER_OBJECT, new Gson().toJson(userObject));
    }


    public static LatLng getCurrentBestLatLng() {

        String locationJson = getSharedPreferences().getString(BEST_LOCATION, null);

        if (locationJson.equals(""))
            return new LatLng(0, 0);

        return (locationJson == null) ? new LatLng(0, 0) : new LatLng(constructLocation(locationJson).getLatitude(), constructLocation(locationJson).getLongitude());
    }

    private static Location constructLocation(String locationJson) {
        Location location = new Gson().fromJson(locationJson, Location.class);
        long timeDelta = System.currentTimeMillis() - location.getTime();
        return (timeDelta <= ALERT_FREQUENCY) ? location : null;
    }

    private static UserObject createUserObj(String locationJson) {
        UserObject obj = new Gson().fromJson(locationJson, UserObject.class);
        return obj;
    }

    private static SocailSignedUserObject createfbUserObj(String locationJson) {
        SocailSignedUserObject obj = new Gson().fromJson(locationJson, SocailSignedUserObject.class);
        return obj;
    }


    // public static final String PREF_GROUPE_LOCA = "web_password";

    public static void unset(String key) {
        try {
            getSharedPreferences().edit().remove(key).commit();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void clearAll() {
        getSharedPreferences().edit().clear().commit();
    }

    // public static void setString(String key, int value) {
    // getSharedPreferences().edit().putInt(key, value).commit();
    // }

}