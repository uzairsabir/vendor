package eu.siacs.conversation.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;

import eu.siacs.conversation.services.XmppConnectionService;

public class ChatPreferences {

    public static final String USER_NAME = "USER_NAME";
    public static final String USER_PROFILE_PIC = "USER_PROFILE_PIC";
    static Context context;

    public ChatPreferences(Context _context) {
        context = _context;
    }


    private static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(context);
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

    public void setString(String key, String value) {
        getSharedPreferences().edit().putString(key, value).commit();
    }

    public String getString(String key) {

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