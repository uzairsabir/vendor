package org.vanguardmatrix.engine.android;

import org.vanguardmatrix.engine.android.data.ActivityManager;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

public class Constants {

    public static ConfigMode runningMode = ConfigMode.PRODUCTION;

    public static String XMPP_DOMAIN = "@iamkarachi";
    public static String XMPP_HOST_NAME = "iamkarachi";
    public static String XMPP_HOST = "54.173.24.189";

    public static String XMPP_BASE_URL = "http://" + getHostIp();
    public static final String IMAGES_BASE_URL = getPublicUrl4Image() + "images/";

    public static String getHostIp() {

        if (runningMode == null) {
            runningMode = ConfigMode.PRODUCTION;
        }

        if (runningMode.equals(ConfigMode.PRODUCTION)) {
            Constants.XMPP_DOMAIN = "@iamkarachi";
            Constants.XMPP_HOST_NAME = "iamkarachi";
            Constants.XMPP_HOST = "www.iamkarachiapp.com"; // amazon
            return "thepilgrimseries.com";
        } else if (runningMode.equals(ConfigMode.STATGING)) {
            Constants.XMPP_DOMAIN = "@iamkarachi";
            Constants.XMPP_HOST_NAME = "iamkarachi";
            Constants.XMPP_HOST = "beta.iamkarachiapp.com"; // cube xs
            return "thepilgrimageguide.com";
        } else if (runningMode.equals(ConfigMode.DEVELOPMENT)) {
            Constants.XMPP_DOMAIN = "@main.iamkarachi";
            Constants.XMPP_HOST_NAME = "main.iamkarachi";
            Constants.XMPP_HOST = "main.iamkarachiapp.com"; // dev http://thepilgrimageguide.com
            return "202.142.175.163";
        } else if (runningMode.equals(ConfigMode.MAINTENANCE)) {
            Constants.XMPP_DOMAIN = "@main.iamkarachi";
            Constants.XMPP_HOST_NAME = "main.iamkarachi";
            Constants.XMPP_HOST = "main.iamkarachiapp.com"; // main
            return "202.142.175.163";
        } else {
            return "202.142.175.163";
        }
    }

    public static final String GOOGLE_ANALYTICS_TRACKER_ID = "UA-61646158-5";

    public static final String IMAGES_FOLDER_NAME = "zaair";
    public static final String BASE_URL_YAHOO_API = "http://where.yahooapis.com/";
    public static String testingParameter = "d5fc4d988f1321b136496dea0ade0330";

    public static String myCountryCodeInt = "92";
    public static String YAHOO_APP_ID = "tRozZcnV34EjhsSbsatSezXs2O95lhtgNByhhC1jkVAAD7Swumw9Sdi_EznJJ2HLzeCaftRK_dSGeKTix3XKs24QvshX.co-";

    public static String directory_get_category = getPublicUrl()
            + "directory/home";
    public static String outlet_by_category = getPublicUrl() + "catalog/get-outlets-by-category";
    public static String outlet_by_search_type = getPublicUrl() + "catalog/get-outlets-search-by-type";
    //Zaair Links
    public static String update_paid_status = getPublicUrl() + "zaair/update-app-status";
    public static String device_registeration = getPublicUrl() + "zaair/create-user-device";
    public static String get_all_locations = getPublicUrl() + "zaair/get-countries-list";
    public static String get_all_ziaraat = getPublicUrl() + "zaair/all-ziaraat-landmarks";
    public static String get_all_countries = getPublicUrl() + "zaair/get-all-countries-list";
    public static String get_all_cities = getPublicUrl() + "zaair/get-all-cities-list";
    public static String get_all_personalities = getPublicUrl() + "zaair/get-all-personalities";
    public static String get_all_tags = getPublicUrl() + "zaair/get-all-tags-list";
    public static String get_all_assets = getPublicUrl() + "zaair/get-asset-list";
    public static String get_all_landmarks_type = getPublicUrl() + "zaair/get-landmark-types";
    public static String get_all_assets_type = getPublicUrl() + "zaair/get-assets-types";
    public static String get_assets_detail = getPublicUrl() + "zaair/get-asset-detail-list";
    public static String get_all_landmarks_description = getPublicUrl() + "zaair/get-ziaraat-description";
    public static String get_all_on_this_day_events = getPublicUrl() + "zaair/get-day-detail-list";
    public static String get_landmarks_personality_detail = getPublicUrl() + "zaair/get-landmarks-personality-list";
    public static String get_personality_characteristics = getPublicUrl() + "zaair/get-personality-characteristics";
    public static String get_emergency_contact = getPublicUrl() + "zaair/get-emergency-contacts";
    public static String get_source_detail = getPublicUrl() + "zaair/get-ziaraat-source-detail";
    public static String get_trip_detail = getPublicUrl() + "zaair/get-trip-detail";
    public static String get_trip_features = getPublicUrl() + "zaair/get-trip-features";

    public static String getYahooWeatherApiURL(String woeid) {
        return "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20%3D%20'" + woeid + "'%20AND%20u%3D'c'&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
//        return "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20%3D%20'" + woeid + "'&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
    }

    public static String getPublicUrl() {

        if (UtilityFunctions.isEmpty(XMPP_BASE_URL))
            XMPP_BASE_URL = "http://" + getHostIp();

        return XMPP_BASE_URL + getSubUrl();
    }

    public static String getPublicUrl4Image() {

        if (UtilityFunctions.isEmpty(XMPP_BASE_URL))
            XMPP_BASE_URL = "http://" + getHostIp();

        return XMPP_BASE_URL + getSubUrl4Image();
    }

    public static String getSubUrl() {
        if (runningMode.equals(ConfigMode.PRODUCTION)) {
            return "/api/";
        } else if (runningMode.equals(ConfigMode.STATGING)) {
            return "/api/";
        } else if (runningMode.equals(ConfigMode.DEVELOPMENT)) {
            return "/zaair/api/";
        } else if (runningMode.equals(ConfigMode.MAINTENANCE)) {
            return "/zaair/api/";
        } else {
            return "/api/";
        }
    }

    public static String getSubUrl4Image() {
        if (runningMode.equals(ConfigMode.PRODUCTION)) {
            return "/assets/";
        } else if (runningMode.equals(ConfigMode.STATGING)) {
            return "/assets/";
        } else if (runningMode.equals(ConfigMode.DEVELOPMENT)) {
            return "/zaair/assets/";
        } else if (runningMode.equals(ConfigMode.MAINTENANCE)) {
            return "/zaair/assets/";
        } else {
            return "/assets/";
        }
    }

    public static String getApplicationGooglePlayLink() {
        return "https://play.google.com/store/apps/details?id="
                + getPackageName();
    }

    public static String getApplicationGooglePlayLinkShortened() {
        return "https://goo.gl/1ac2aD";// must create online using getApplicationGooglePlayLink() ,,, https://goo.gl/

    }

    private static String getPackageName() {
        return ActivityManager.getInstance().getRunningActivity().getPackageName();
//        return "com.vanguardmatrix.zaair";
    }

    public static enum ConfigMode {
        DEVELOPMENT, MAINTENANCE, PRODUCTION, STATGING
    }

    public static enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg:
        // roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a
        // company.
    }


//    public static final String ejabberdApiUrl = getPublicUrl() + "ejabberdapi";
//    public static final String XMPP_ADDFRIENDS_URL = getPublicUrl()
//            + "ejabberdapi";
//    public static final String XMPP_ADDFRIENDS_URL_SYNC = getPublicUrl()
//            + "ejabberdapi/synccontacts";
//    public static final String GET_PROFILE_URL = getPublicUrl()
//            + "ejabberdapi/getuserprofilebyid";
//
//    public static final String feeds_update_user_tags = getPublicUrl()
//            + "feeds/update-tags";
//    public static final String feeds_get_user_tags = getPublicUrl()
//            + "feeds/update-my-tags";
//    public static final String feeds_get_detail = getPublicUrl()
//            + "users/wall-item-detail";
//    public static final String homescreen_get_blogs = getPublicUrl()
//            + "scripter/get-iamkarachi-blogs";
//    public static final String user_add_preferred_location = getPublicUrl()
//            + "voice/add-preferred-locations";
//    public static final String user_delete_preferred_location = getPublicUrl()
//            + "voice/delete-preferred-locations";
//    public static int GENERAL_TRACKER = 0;
//    public static String tourImagesArray[] = {
//            Constants.IMAGES_BASE_URL + "defaults/tour/tour1.jpg",
//            Constants.IMAGES_BASE_URL + "defaults/tour/tour2.jpg",
//            Constants.IMAGES_BASE_URL + "defaults/tour/tour3.jpg",
//            Constants.IMAGES_BASE_URL + "defaults/tour/tour4.jpg",
//            Constants.IMAGES_BASE_URL + "defaults/tour/tour5.jpg",
//            Constants.IMAGES_BASE_URL + "defaults/tour/tour6.jpg",
//            Constants.IMAGES_BASE_URL + "defaults/tour/tour7.jpg"};
//    public static String APP_INVITE_TITLE = "Invite Using";
//    public static String APP_INVITE_SUBJECT = "\nTry #IAMKARACHI for Android!";
//    public static String APP_INVITE_BODY = "Hi, I'm using #IAMKARACHI for Android and I recommend it. Click here: "
//            + getApplicationGooglePlayLink();
//    public static String INTENT_RETURN = "intent-response";
//    public static String APP_SHARE_TITLE = "Share Using";
//    public static String myCountryCode = "pk";
//
//    public static boolean bulkFriendsSent = false;
//    public static boolean callstarted = false;
//    public static String username = "";
//
//    public static final String BASE_URL_YAHOO_WEATHER_API = "http://weather.yahooapis.com/forecastrss";
//    public static String ANONYMOUS = "Anonymous";
//
//    public static final String IAMKARACHI_INFO_LINK = "http://www.iamkarachi.org/who-we-are/";
//    public static final String PRIVACY_POLICY_URL = "https://www.iubenda.com/privacy-policy/431140";
//    // The following line should be changed to include the correct property id.
//
//    public static final String FILES_FOLDER = "AW_PAKI";


}
