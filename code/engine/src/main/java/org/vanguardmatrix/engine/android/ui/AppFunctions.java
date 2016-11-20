package org.vanguardmatrix.engine.android.ui;

import android.net.Uri;
import android.util.Log;

import com.initializer.engine.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.vanguardmatrix.engine.android.Application;
import org.vanguardmatrix.engine.android.Constants;
import org.vanguardmatrix.engine.android.webservice.WebService;
import org.vanguardmatrix.engine.android.webservice.WebServiceResponse;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.util.ArrayList;

public class AppFunctions {

    public static Uri getDefaultNotificationSound() {
        return Uri.parse("android.resource://"
                + Application.getInstance().getPackageName() + "/"
                + R.raw.app_sms_sound);
    }

    public static Uri getAppCallingSound() {
        return Uri.parse("android.resource://"
                + Application.getInstance().getPackageName() + "/"
                + R.raw.app_call_sound);
    }

    public static Uri getAppOutgoingCallSound() {
        return Uri.parse("android.resource://"
                + Application.getInstance().getPackageName() + "/"
                + R.raw.outgoing_alert);
    }


//    public static boolean isCallConnectionValid(String accountData,
//                                                String userData, Activity activity) {
//        try {
//            if (NetworkManager.isConnected(activity)) {
//                if (RosterManager.getInstance()
//                        .getBestContact(accountData, userData).getStatusMode()
//                        .isOnline()) {
//                    Log.e("chatviewer", "roster manager chat status isOnline");
//                    if (NetworkManager.isConnectedFast(activity)) {
//                        return true;
//
//                    } else {
//                        UtilityFunctions.showToast_onCenter(
//                                "Your Connection is too slow for this Call.",
//                                activity);
//                        return false;
//                    }
//                } else {
//                    UtilityFunctions.showToast_onCenter(
//                            "User is not Available.", activity);
//                    return false;
//                }
//            } else {
//                UtilityFunctions.showToast_onCenter("You are Offline.",
//                        activity);
//                return false;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    public static String getWOEIDFromCityName(String city) {

        String woeid = "";
        ArrayList<NameValuePair> localtrendsParameter = new ArrayList<NameValuePair>();
        localtrendsParameter.add(new BasicNameValuePair("appid", "["
                + Constants.YAHOO_APP_ID + "]" + "&format=json"));

        Log.e("aw", "calling yahoo for woeid using city : " + city);

        try {
            WebServiceResponse response = WebService.callHTTPGet(
                    Constants.BASE_URL_YAHOO_API + "v1/places.q('" + city
                            + "')", localtrendsParameter, true);
            JSONObject localTrends = response.extractJSONObject();
            Log.e("Extract JSON", "" + localTrends);
            JSONArray a = UtilityFunctions.jPathToArray(localTrends,
                    "places->place");
            Log.e("This is json array", "" + a);

            for (int i = 0; i < a.length(); i++) {
                Log.e(i + "index value", " : " + a.getString(i));
                woeid = a.getJSONObject(i).getJSONObject("timezone attrs")
                        .getString("woeid");
                // woeid = a.getJSONObject(i).getString("woeid");

            }
            // woeid =
            // localTrends.getJSONObject("places").getJSONArray("place").getJSONObject("country attrs").getString("woeid");
            Log.e("This is woeid", " ." + woeid);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("In catch woeid", woeid);
        }
        return woeid;

    }

//	private static Twitter twitterObj;
//
//	public static Twitter getTwitterObject_withAuth(Context context) {
//
//		if (twitterObj == null) {
//
//			Log.e("AWWWWWWW", "creating object");
//			ConfigurationBuilder builder = new ConfigurationBuilder();
//
//			builder.setOAuthConsumerKey(AppPreferences.TWITTER_CONSUMER_KEY);
//			builder.setOAuthConsumerSecret(AppPreferences.TWITTER_CONSUMER_SECRET);
//			builder.setOAuthAccessToken(AppPreferences
//					.getString(AppPreferences.PREF_TWITTER_ACCESS_TOKEN));
//			builder.setOAuthAccessTokenSecret(AppPreferences
//					.getString(AppPreferences.PREF_TWITTER_ACCESS_TOKEN_SECRET));
//			twitter4j.conf.Configuration configuration = builder.build();
//
//			TwitterFactory factory = new TwitterFactory(configuration);
//			twitterObj = factory.getInstance();
//		}
//
//		return twitterObj;
//	}

//    public static String getPhoneBookNameByNumber(String Number) {
//
//        ContentResolver cr = HomeScreenOld.activity.getContentResolver();
//        Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
//                Uri.encode(Number));
//        Cursor cursor = cr.query(uri,
//                new String[]{PhoneLookup.DISPLAY_NAME}, null, null, null);
//        if (cursor == null) {
//            return null;
//        }
//        String contactName = "";
//        if (cursor.moveToFirst()) {
//            contactName = cursor.getString(cursor
//                    .getColumnIndex(PhoneLookup.DISPLAY_NAME));
//        }
//
//        if (cursor != null && !cursor.isClosed()) {
//            cursor.close();
//        }
//
//        Log.e("***PhoneBookName  ", Number + "--- " + contactName);
//
//        return contactName;
//
//    }

}
