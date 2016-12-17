package org.vanguardmatrix.engine.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.initializer.engine.R;
import com.terrakok.phonematter.PhoneFormat;

import org.apache.http.HttpResponse;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.chrono.IslamicChronology;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.android.Application;
import org.vanguardmatrix.engine.android.Labels;
import org.vanguardmatrix.engine.android.data.DatabaseManager;
import org.vanguardmatrix.engine.datatypes.Contact;
import org.vanguardmatrix.engine.datatypes.PhoneContact;
import org.vanguardmatrix.engine.xpath.parser.DBHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class UtilityFunctions {

    public static ArrayList<String> sortedArray = new ArrayList<String>();
    // private static ArrayList<Contact> phoneContactsMap;
    public static String DateFormat = "yyyy-MM-dd";
    public static String tmpSyncTime = "";
    private static int deviceHeight = 0;
    private static int deviceWidth = 0;
    public static final int READ_CONTACTS_PERMISSIONS_REQUEST = 1;
    private static final Uri CAL_URI = CalendarContract.Calendars.CONTENT_URI;


    public static String get_MD5(String str) {

        try {
            // Create MD5 Hash
            String result = "";
            MessageDigest md = MessageDigest.getInstance("MD5"); // or "SHA-1"
            md.update(str.getBytes());
            BigInteger hash = new BigInteger(1, md.digest());
            result = hash.toString(16);
            while (result.length() < 32) {
                result = "0" + result;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.i("MD5", "Some thing went wrong creating hash" + e.toString());
        }

        return null;
    }

    public static void showSmallToast_onCenter(String message, Activity activity) {

        Toast toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 100);
        toast.show();
    }

    public static void showToast_onCenter(String message, Activity activity) {

        Toast toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 100);
        toast.show();
    }

    public static void showToastLong_onCenter(String message, Activity activity) {

        Toast toast = Toast.makeText(activity, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 100);
        toast.show();
    }

    public static void showToast_onCenter(String message, Context activity) {

        Toast toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 100);
        toast.show();
    }

    public static void showToast_onTop(String message, Activity activity) {

        Toast toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 100);
        toast.show();
    }

    public static void showToast_normal(String message, Activity activity) {

        Toast toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.show();
    }

    @SuppressWarnings({"unused", "rawtypes"})
    public static Map<String, Contact> getSortedHashMap(
            Map<String, Contact> myHashmap) {

        Log.e("Unsorted Hashmao", "--- " + myHashmap);
        Map<String, Contact> sortedContactsMap = new LinkedHashMap<String, Contact>();

        int i = 0;
        Iterator myVeryOwnIterator = myHashmap.keySet().iterator();
        while (myVeryOwnIterator.hasNext()) {
            String key = (String) myVeryOwnIterator.next();
            Contact value = (Contact) myHashmap.get(key);

            // Log.e("Sorted Field", value.getuserName());
            sortedArray.add(value.getuserName() + "," + key);
        }
        Collections.sort(sortedArray);

        for (int j = 0; j < sortedArray.size(); j++) {

            String[] separated = sortedArray.get(j).split(",");
            Log.e("Sorted Array = ",
                    separated[1] + " --- "
                            + myHashmap.get(separated[1]).getuserName());
            sortedContactsMap.put(separated[1], myHashmap.get(separated[1]));
        }

        // Log.e("Sorted Hashmao", "--- " + sortedContactsMap);
        return sortedContactsMap;

    }

    public static int getDeviceOSVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    public static String getDeviceMacAddress(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();
        return address;
    }

    public static String getDeviceInfo() {
        return "Android_OS_Build_Manufacturer:" + android.os.Build.MANUFACTURER + ", " + "Android_OS_Build_Device:"
                + android.os.Build.DEVICE + ", " + "Android_OS_Build_Version:" + android.os.Build.VERSION.SDK_INT;
    }

    @SuppressLint("SimpleDateFormat")
    public static String dob_to_age(String dob) {
        String age = "1 day old";
        Date dateOfBirth = null;
        SimpleDateFormat format = new SimpleDateFormat(DateFormat);
        try {
            dateOfBirth = format.parse(dob);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = new Date();
        long diffInMis = endDate.getTime() - dateOfBirth.getTime();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMis);
        // Date dt = new Date();
        // double seconds = 0;// Math.floor((dt - date) / 1000);

        int interval = (int) seconds / 31536000;
        if (interval > 0) {
            if (interval > 1)
                return interval + " years old";
            else
                return interval + " year old";
        }
        interval = (int) seconds / (86400 * 31);
        if (interval > 0) {
            if (interval > 1)
                return interval + " months old";
            else
                return interval + " month old";
        }
        interval = (int) seconds / (86400 * 7);
        if (interval > 0) {
            if (interval > 1)
                return interval + " weeks old";
            else
                return interval + " week old";
        }
        interval = (int) Math.floor(seconds / 86400);
        if (interval > 0) {
            if (interval > 1)
                return interval + " days old";
            else
                return interval + " day old";
        }

        return age;
    }

    public static String timeSince(Date date) {

        if (date == null)
            return "";
        // Date startDate = JsonDateToDate(jsonDate);
        Date startDate = date;
        Date endDate = new Date();

        long diffInMis = endDate.getTime() - startDate.getTime();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMis);
        // Date dt = new Date();
        // double seconds = 0;// Math.floor((dt - date) / 1000);

        int interval = (int) seconds / 31536000;
        if (interval > 0) {
            if (interval > 1)
                return interval + " years ago";
            else
                return interval + " year ago";
        }
        interval = (int) seconds / (86400 * 31);
        if (interval > 0) {
            if (interval > 1)
                return interval + " months ago";
            else
                return interval + " month ago";
        }
        interval = (int) seconds / (86400 * 7);
        if (interval > 0) {
            if (interval > 1)
                return interval + " weeks ago";
            else
                return interval + " week ago";
        }
        interval = (int) Math.floor(seconds / 86400);
        if (interval > 0) {
            if (interval > 1)
                return interval + " days ago";
            else
                return interval + " day ago";
        }
        interval = (int) Math.floor(seconds / 3600);
        if (interval > 0) {
            if (interval > 1)
                return interval + " hrs ago";
            else
                return interval + " hr ago";
        }
        interval = (int) Math.floor(seconds / 60);
        if (interval > 0) {
            if (interval > 1)
                return interval + " mins ago";
            else
                return interval + " min ago";
        }
        if (Math.floor(seconds) <= 10.0)
            return "just now";
        else
            return (int) Math.floor(seconds) + " secs ago";
    }

    public static int getSecondsSinceTime(Date thisData) {

        Date startDate = thisData;
        Date endDate = new Date();

        long diffInMis = endDate.getTime() - startDate.getTime();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMis);

        return (int) Math.floor(seconds);
    }

    public static int getMinDiff(Date d1, Date d2) {
        long diffInMs = d2.getTime() - d1.getTime();
        long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);

        return (int) Math.floor(diffInSec / 60);
    }

    public static Date JsonDateToDate(String jsonDate) {
        // "/Date(1321867151710)/"
        int idx1 = jsonDate.indexOf("(");
        int idx2 = jsonDate.indexOf("+");
        String s = jsonDate.substring(idx1 + 1, idx2);
        long l = Long.valueOf(s);
        return new Date(l);
    }

    // To animate view slide out from bottom to top
    public static void slideToTop(final View view) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0,
                -(view.getHeight() * 3));
        animate.setDuration(750);
        animate.setFillAfter(true);

        animate.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // view.startAnimation(animation);
                // animation.start();
                Log.e("aw", "hiding anim should start");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                // show_hide.setText("Show Tweet");

            }
        });
        view.startAnimation(animate);
    }

    // To animate view slide out from top to bottom
    public static void slideToBottom(final View view) {
        TranslateAnimation animate = new TranslateAnimation(0, 0,
                -(view.getHeight() * 3), 0);
        animate.setDuration(750);
        animate.setFillAfter(true);
        animate.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                // show_hide.setText("Hide Tweet");
            }
        });
        view.startAnimation(animate);
    }

    // To animate view slide out from left to right
    public static void slideToRight_hidingOption(final View view) {
        TranslateAnimation animate = new TranslateAnimation(0,
                (view.getWidth()), 0, 0);
        animate.setDuration(750);
        animate.setFillAfter(true);

        animate.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // view.startAnimation(animation);
                // animation.start();
                Log.e("aw", "hiding anim should start");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // view.setVisibility(View.GONE);
                Log.e("aw", "hiding anim ended");
                // show_hide.setText("Show Tweet");
                view.setVisibility(View.GONE);
            }
        });
        view.startAnimation(animate);
    }

    // To animate view slide out from right to left
    public static void slideToLeft_showingOption(final View view) {
        TranslateAnimation animate = new TranslateAnimation(+view.getWidth(),
                0, 0, 0);
        animate.setDuration(750);
        animate.setFillAfter(true);
        animate.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                Log.e("aw", "showing anim should start");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // view.setVisibility(View.GONE);
                Log.e("aw", "shwoing anim end");
                // show_hide.setText("Hide Tweet");
                view.setVisibility(View.GONE);
            }
        });
        view.startAnimation(animate);
    }

    public static void moveTesting(final View view) {

        TranslateAnimation animate = new TranslateAnimation(0, 0, 50, 0);
        animate.setDuration(750);
        animate.setFillAfter(true);
        animate.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                Log.e("aw", "showing anim should start");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // view.setVisibility(View.GONE);
                Log.e("aw", "shwoing anim end");
                // show_hide.setText("Hide Tweet");
                view.setVisibility(View.VISIBLE);
            }
        });
        view.startAnimation(animate);

    }

    public static void moveLeft(View view) {
        Animation animation = new TranslateAnimation(+getDeviceWidth(), 0, 0, 0);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        view.startAnimation(animation);
        view.setVisibility(View.VISIBLE);
    }

    public static void OpenSocialTabs(View view) {
        Animation animation = new TranslateAnimation(180, 0, 180, 0);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        view.startAnimation(animation);
        view.setVisibility(View.VISIBLE);
    }

    public static void updateDeviceHeightwidth(Activity activity) {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay()
                .getMetrics(displaymetrics);

        setDeviceHeight(displaymetrics.heightPixels);
        setDeviceWidth(displaymetrics.widthPixels);
        Log.e("imkarachi", "Config updates : device width:" + getDeviceWidth()
                + " , device Hegith:" + getDeviceHeight());

    }

    public static int getDeviceHeight() {
        return deviceHeight;
    }

    public static void setDeviceHeight(int deviceHeight) {
        UtilityFunctions.deviceHeight = deviceHeight;
    }

    public static int getDeviceWidth() {
        return deviceWidth;
    }

    public static void setDeviceWidth(int deviceWidth) {
        UtilityFunctions.deviceWidth = deviceWidth;
    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                .matches();
    }

    public static JSONObject parseContacts(String key, String object) {

        JSONObject x = null;
        try {
            x = new JSONObject(object);
        } catch (JSONException e) {
            if (!object.contains("ok"))
                e.printStackTrace();
        }

        return x;
    }

    public static JSONObject getJsonfromString(String result) {

        try {

            JSONObject json = new JSONObject(result).getJSONObject("response");
            return json.getJSONObject("profile");

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject getContactJsonfromResponse2(String result) {

        try {
            return new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject getUpdatedContactJsonfromResponse(String result) {

        try {
            return new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void hideKeyboard(Activity activity, View view) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public static void hideKeyboard(Activity actavity) {

        // hide keyboard :
        actavity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    public static void showKeyboard(Activity actavity) {

        // Show soft-keyboard:
        actavity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


    }

    public static boolean isEmpty(String str) {
        if ((str == null) || str.matches("^\\s*$") || str.matches("")
                || str.matches("null")) {
            return true;
        } else {
            return false;
        }
    }

    public static Uri getUriFromPath(String filePath) {

        try {
            return Uri.fromFile(new File(filePath));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    public static String getRealPathFromURI(Uri contentUri, Activity activity) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = activity.getContentResolver().query(contentUri, proj,
                    null, null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * Getting All Images Path
     *
     * @param activity
     * @return ArrayList with images Path
     */
    @SuppressWarnings("unused")
    public static ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }

    /**
     * Getting All Images Path
     *
     * @param activity
     * @return ArrayList with images Path
     */

    @SuppressWarnings("unused")
    public static ArrayList<String> getAllShownVideosPath(Activity activity) {

        Log.e("videos", "fetching");
        Uri uri;
        Cursor cursor;
        int column_index_data, id;
        // ArrayList<String[]> listOfAllVideos = new ArrayList<String[]>();
        ArrayList<String> listOfAllVideos = new ArrayList<String>();
        String videoLink = "";
        // String[] videoLink = new String[2];
        // String absolutePathOfImage = null;
        try {

            uri = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

            String[] projection = {MediaColumns.DATA, MediaColumns._ID};

            cursor = activity.getContentResolver().query(uri, projection, null,
                    null, null);

            id = cursor.getColumnIndexOrThrow(MediaColumns._ID);
            column_index_data = cursor.getColumnIndexOrThrow(MediaColumns.DATA);

            // column_index_folder_name = cursor
            // .getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
            while (cursor.moveToNext()) {
                // Log.e("video URI", uri.toString());
                // int id =
                // ca.getInt(ca.getColumnIndex(MediaStore.MediaColumns._ID));
                //
                videoLink = cursor.getString(column_index_data);

                // videoLink[0] = cursor.getString(column_index_data);
                // videoLink[1] = cursor.getString(id);

                listOfAllVideos.add(videoLink);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listOfAllVideos;
    }

    public static String getMyIpAddress(Activity activity) {
        WifiManager wifiManager = (WifiManager) activity
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        Log.e("imkarachi_CONFIQ", " current my ip: " + ip + "derived from :"
                + wifiInfo.getNetworkId() + " ,,, " + wifiInfo.getLinkSpeed());
        return ip;

    }

    // public static String intToIp(int i) {
    // return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "."
    // + ((i >> 8) & 0xFF) + "." + (i & 0xFF);
    // }

    public static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + ((i >> 24) & 0xFF);
    }

    public static String getApplicationGooglePlayLink() {
        return "https://play.google.com/store/apps/details?id="
                + Application.getInstance().getPackageName();

    }

    public static String getRescueApplicationGooglePlayLink(String packageName) {
        return "https://play.google.com/store/apps/details?id="
                + packageName;

    }


    public static Bitmap rotate(Bitmap b, int degrees) {
        if (degrees != 0 && b != null) {
            Matrix m = new Matrix();

            m.setRotate(degrees, (float) b.getWidth() / 2,
                    (float) b.getHeight() / 2);
            try {
                Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
                        b.getHeight(), m, true);
                if (b != b2) {
                    b.recycle();
                    b = b2;
                }
            } catch (OutOfMemoryError ex) {
                throw ex;
            }
        }
        return b;
    }

    public static Bitmap getThumbnail(Uri uri) {
        File image = new File(uri.getPath());
        int THUMBNAIL_SIZE = 100;

        Log.e("aw", "here in getPreview");
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(image.getPath(), bounds);
        if ((bounds.outWidth == -1) || (bounds.outHeight == -1))
            return null;

        int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight
                : bounds.outWidth;

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = originalSize / THUMBNAIL_SIZE;
        return BitmapFactory.decodeFile(image.getPath(), opts);
    }

    public static Bitmap getBitmapFromPath(String path, Activity activity) {
        Uri uri = getUriFromPath(path);
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 2048;
            in = activity.getContentResolver().openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(in, null, o);
            in.close();

            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(
                        2,
                        (int) Math.round(Math.log(IMAGE_MAX_SIZE
                                / (double) Math.max(o.outHeight, o.outWidth))
                                / Math.log(0.5)));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            in = activity.getContentResolver().openInputStream(uri);
            Bitmap b = BitmapFactory.decodeStream(in, null, o2);
            in.close();

            return b;
        } catch (FileNotFoundException e) {
            Log.e("Config", "file " + path + " not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Config", "file " + path + " not found");
        }
        return null;
    }

    public static Bitmap getBitmapFromUri(Uri uri, Activity activity) {
        // Uri uri = getUriFromPath(path);
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 2048;
            in = activity.getContentResolver().openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(in, null, o);
            in.close();

            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(
                        2,
                        (int) Math.round(Math.log(IMAGE_MAX_SIZE
                                / (double) Math.max(o.outHeight, o.outWidth))
                                / Math.log(0.5)));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            in = activity.getContentResolver().openInputStream(uri);
            Bitmap b = BitmapFactory.decodeStream(in, null, o2);
            in.close();

            return b;
        } catch (FileNotFoundException e) {
            Log.e("Config", "file " + uri.getPath() + " not found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Config", "file " + uri.getPath() + " not found");
        }
        return null;
    }

    public static String getBase64(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        return Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
    }

    public static int getRandomNumberBetween(int min, int max) {

        int finalNumber = new Random().nextInt((max - min) + 1) + min;

        return finalNumber;
    }

    public static String getCity4mLatLong(String lat_long) {

        //       HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        // tempAddressName = lat_long;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            // http://maps.googleapis.com/maps/api/geocode/
//            // json?latlng=40.714224,-73.961452&sensor=true_or_false
//            HttpGet httpGet = new HttpGet(
//                    "http://maps.google.com/maps/api/geocode/json?latlng="
//                            + URLEncoder.encode(lat_long, "UTF-8")
//                            + "&sensor=false");
//            Log.e("aw", "url : " + httpGet.getURI());
//
//            response = client.execute(httpGet);
            //          HttpEntity entity = response.getEntity();
            //         InputStream stream = entity.getContent();
//            int b;
//            while ((b = stream.read()) != -1) {
//                stringBuilder.append((char) b);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        try {
            jsonObject = new JSONObject(stringBuilder.toString());
            Log.e("aw", jsonObject.toString());

            Log.e("aw getting results :", jsonObject.getJSONArray("results")
                    .toString());
            jsonArray = jsonObject.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                Log.e("aw", "reading types : "
                        + jsonArray.getJSONObject(i).getJSONArray("types")
                        .toString());

                if (jsonArray.getJSONObject(i).getJSONArray("types").toString()
                        .equalsIgnoreCase("[\"locality\",\"political\"]")) {

                    JSONArray locality_Political = new JSONArray();

                    Log.e("aw",
                            "getting address component : "
                                    + jsonArray.getJSONObject(i)
                                    .getJSONArray("address_components")
                                    .toString());
                    locality_Political = jsonArray.getJSONObject(i)
                            .getJSONArray("address_components");

                    for (int j = 0; j < locality_Political.length(); j++) {
                        // array_type array_element = array[j];
                        if (locality_Political
                                .getJSONObject(j)
                                .getJSONArray("types")
                                .toString()
                                .equalsIgnoreCase(
                                        "[\"locality\",\"political\"]")) {

                            Log.e("aw", "disired city : "
                                    + locality_Political.getJSONObject(j)
                                    .getString("long_name").toString());

                            return locality_Political.getJSONObject(j)
                                    .getString("long_name").toString();
                        }

                    }

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("aw", "fail to parse json in getLocationInfo method");
        }

        return "";
    }

    /*
     * @author: Shan-e-Raza Expected Params: First Param: String containing the
     * Path of the Object looking to extract from JSON e.g:
     * "data->fields->Address" Returns: Success: Returns the targeted JSON
     * Object Failure: Null
     *
     * Dated: 12th February, 2013
     */
    public static JSONObject jPath(JSONObject jsonObject, String jPath) {
        if (jPath.contains("->")) {

            String[] jPathSeparated = jPath.split("->");

            for (int index = 0; index < jPathSeparated.length; index++) {
                try {
                    jsonObject = jsonObject
                            .getJSONObject(jPathSeparated[index]);
                } catch (JSONException e1) {
                    jsonObject = null;
                }
            }
        } else {
            jsonObject = null;
        }

        return jsonObject;
    }

    /*
     * @author: Shan-e-Raza Expected Params: First Param: String containing the
     * Path of the Object looking to extract from JSON e.g:
     * "data->fields->Address" Returns: Success: Returns the targeted JSON Array
     * Failure: Null
     *
     * Dated: 15th February, 2013
     */
    public static JSONArray jPathToArray(JSONObject jsonObject, String jPath) {
        JSONArray jsonArray = null;

        if (jPath.contains("->")) {

            String[] jPathSeparated = jPath.split("->");

            for (int index = 0; index < jPathSeparated.length; index++) {
                try {
                    if ((index + 1) == jPathSeparated.length)
                        jsonArray = jsonObject
                                .getJSONArray(jPathSeparated[index]);
                    else
                        jsonObject = jsonObject
                                .getJSONObject(jPathSeparated[index]);
                } catch (JSONException e1) {
                    jsonArray = null;
                }
            }
        } else {
            jsonArray = null;
        }

        return jsonArray;
    }

    public static String getPostalCode(Document doc) {

        String postalCode = null;

        NodeList nodes = doc.getElementsByTagName("result");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            NodeList nodeList = node.getChildNodes();
            for (int j = 0; j < nodeList.getLength(); j++) {
                Node childNode = nodeList.item(j);

                childNode.normalize();

                if (childNode.getNodeType() == Node.ELEMENT_NODE) {

                    if (childNode.getNodeName().equals("type")
                            && childNode.getTextContent().equals("postal_code")) {

                        Node markedNode = tranverseSiblingsInXML(childNode,
                                "address_component");

                        markedNode = markedNode.getFirstChild();

                        markedNode = tranverseSiblingsInXML(markedNode,
                                "short_name");

                        postalCode = markedNode.getTextContent();
                    }
                }

                if (postalCode != null)
                    break;
            }

            if (postalCode != null)
                break;
        }

        return postalCode;
    }

    public static String getStreetAddress(Document doc) {

        String streetAddress = null;
        String neighborhoodAddress = null;
        String subLocalityAddress = null;
        String localityAddress = null;
        String postalCodeAddress = null;

        NodeList nodes = doc.getElementsByTagName("result");

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            NodeList nodeList = node.getChildNodes();
            for (int j = 0; j < nodeList.getLength(); j++) {
                Node childNode = nodeList.item(j);

                childNode.normalize();

                if (childNode.getNodeType() == Node.ELEMENT_NODE) {

                    if (childNode.getNodeName().equals("type")
                            && childNode.getTextContent().equals(
                            "street_address")) {

                        Node markedNode = tranverseSiblingsInXML(childNode,
                                "formatted_address");

                        streetAddress = markedNode.getTextContent();
                    }

                    if (childNode.getNodeName().equals("type")
                            && childNode.getTextContent()
                            .equals("neighborhood")) {

                        Node markedNode = tranverseSiblingsInXML(childNode,
                                "formatted_address");

                        neighborhoodAddress = markedNode.getTextContent();
                    }

                    if (childNode.getNodeName().equals("type")
                            && childNode.getTextContent().equals("sublocality")) {

                        Node markedNode = tranverseSiblingsInXML(childNode,
                                "formatted_address");

                        subLocalityAddress = markedNode.getTextContent();
                    }

                    if (childNode.getNodeName().equals("type")
                            && childNode.getTextContent().equals("locality")) {

                        Node markedNode = tranverseSiblingsInXML(childNode,
                                "formatted_address");

                        localityAddress = markedNode.getTextContent();
                    }

                    if (childNode.getNodeName().equals("type")
                            && childNode.getTextContent().equals("postal_code")) {

                        Node markedNode = tranverseSiblingsInXML(childNode,
                                "formatted_address");

                        postalCodeAddress = markedNode.getTextContent();
                    }
                }

                if (streetAddress != null || neighborhoodAddress != null
                        || subLocalityAddress != null
                        || localityAddress != null || postalCodeAddress != null)
                    break;
            }

            if (streetAddress != null || neighborhoodAddress != null
                    || subLocalityAddress != null || localityAddress != null
                    || postalCodeAddress != null)
                break;
        }

        if (streetAddress != null) {
            return streetAddress;
        } else if (neighborhoodAddress != null) {
            return neighborhoodAddress;
        } else if (subLocalityAddress != null) {
            return subLocalityAddress;
        } else if (localityAddress != null) {
            return localityAddress;
        } else if (postalCodeAddress != null) {
            return postalCodeAddress;
        } else {
            return null;
        }
    }

    public static Node tranverseSiblingsInXML(Node markedNode, String nameToFind) {
        while (markedNode.getNextSibling() != null) {

            if (markedNode.getNodeType() == Node.ELEMENT_NODE
                    && markedNode.getNodeName().equals(nameToFind)) {
                break;
            }

            markedNode = markedNode.getNextSibling();
        }

        return markedNode;
    }

    public static String convertInputStreamToString(InputStream inputStream)
            throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        // Log.e("Service Responce", "convertInputStreamToString" + result);
        return result;

    }

    public static HashMap<String, JSONObject> parse(String json) {

        JsonObject object = (JsonObject) new com.google.gson.JsonParser()
                .parse(json);
        Set<Map.Entry<String, JsonElement>> set = object.entrySet();
        Iterator<Map.Entry<String, JsonElement>> iterator = set.iterator();
        HashMap<String, JSONObject> map = new HashMap<String, JSONObject>();
        while (iterator.hasNext()) {

            Map.Entry<String, JsonElement> entry = iterator.next();
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            if (key.equals("timestamp") || key.equals("timeStamp")) {
                tmpSyncTime = (String) entry.getValue().toString();
                Log.e("timeStamp getting", tmpSyncTime);
                if (tmpSyncTime.charAt(0) == '"')
                    tmpSyncTime = tmpSyncTime.substring(1);
                if (tmpSyncTime.charAt(tmpSyncTime.length() - 1) == '"')
                    tmpSyncTime = tmpSyncTime.substring(0,
                            tmpSyncTime.length() - 1);
                Log.e("timeStamp passing", tmpSyncTime);
            } else
                map.put(key, parseContacts(key, value.toString()));
            // Log.e("json entry", "key :" + key + " value : "
            // + entry.getValue().toString());
        }
        return map;
    }

    public static String getProgressPercent(String string) {

        try {
            return Integer.parseInt(string) + " %";
        } catch (Exception e) {
            return "100" + " %";
        }

    }

    public static String getDayFromInt(int dayInt) {
        if (dayInt == 0)
            return "Sun";
        else if (dayInt == 2)
            return "Tue";
        else if (dayInt == 3)
            return "Wed";
        else if (dayInt == 4)
            return "Thu";
        else if (dayInt == 5)
            return "Fri";
        else if (dayInt == 6)
            return "Sat";
        else if (dayInt == 7)
            return "Sun";
        else
            return "Mon";
    }

    public static String getCurrentDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        return dayOfTheWeek;
        // return getDayFromInt(getDayIntFromString(dayOfTheWeek));//
        // dayOfTheWeek;
    }

    public static int GetUnixTime() {
        Calendar calendar = Calendar.getInstance();
        long now = calendar.getTimeInMillis();
        int utc = (int) (now / 1000);
        return (utc);

    }

    public static String getCurrentTime() {

        // try {
        // String currPSTime = UtilityFunctions.getPST_fromCurrTime(ParseDate
        // .getStringFromDate(ParseDate
        // .getDateFromLongSystemTime(System
        // .currentTimeMillis()),
        // ParseDate.FORMAT_STANDARD));
        // Log.e("PST 1", " : " + currPSTime);
        //
        // Log.e("GMT",
        // " : "
        // + ParseDate.getStringFromDate(ParseDate
        // .getDateFromLongSystemTime(Long
        // .parseLong("" + GetUnixTime())),
        // ParseDate.FORMAT_STANDARD));
        //
        // LocalDateTime x = new LocalDateTime(System.currentTimeMillis());
        //
        // Date y = UtilityFunctions.getPST_fromCurrTime(x, "UTC", "PKT");
        //
        // Log.e("PST 2",
        // " : "
        // + ParseDate.getStringFromDate(y,
        // ParseDate.FORMAT_STANDARD));
        //
        // } catch (Exception e) {
        // e.printStackTrace();
        // }

        Calendar c = Calendar.getInstance();
        String hr, min = "00";
        if (c.get(Calendar.HOUR_OF_DAY) < 10)
            hr = "0" + c.get(Calendar.HOUR_OF_DAY);
        else
            hr = "" + c.get(Calendar.HOUR_OF_DAY);

        if (c.get(Calendar.MINUTE) < 10)
            min = "0" + c.get(Calendar.MINUTE);
        else
            min = "" + c.get(Calendar.MINUTE);

        return hr + ":" + min;
    }

    // public static String getCurrentTimeIn24Hout(){
    // DateFormat[] formats = new DateFormat[] {
    // DateFormat.getDateInstance(),
    // DateFormat.getDateTimeInstance(),
    // DateFormat.getTimeInstance(),
    // };
    // for (DateFormat df : formats) {
    // //System.out.println(df.format(new Date(0)));
    // }
    //
    // return "";
    // }

    public static String getCurrentTimeAmPm() {

        Calendar c = Calendar.getInstance();

        if (c.get(Calendar.AM_PM) == 0)
            return "AM";
        else
            return "PM";

    }

    public static int getDayIntFromString(String day) {
        if (day.equalsIgnoreCase("Sunday"))
            return 0;
        else if (day.equalsIgnoreCase("Monday"))
            return 1;
        else if (day.equalsIgnoreCase("Tuesday"))
            return 2;
        else if (day.equalsIgnoreCase("Wednesday"))
            return 3;
        else if (day.equalsIgnoreCase("Thurday"))
            return 4;
        else if (day.equalsIgnoreCase("Friday"))
            return 5;
        else if (day.equalsIgnoreCase("Saturday"))
            return 6;
        else
            return 7;
    }

    public static String getMonthFromInt(int monthInt) {
        if (monthInt == 1)
            return Labels.january;
        else if (monthInt == 2)
            return Labels.february;
        else if (monthInt == 3)
            return Labels.march;
        else if (monthInt == 4)
            return Labels.april;
        else if (monthInt == 5)
            return Labels.may;
        else if (monthInt == 6)
            return Labels.june;
        else if (monthInt == 7)
            return Labels.july;
        else if (monthInt == 8)
            return Labels.august;
        else if (monthInt == 9)
            return Labels.september;
        else if (monthInt == 10)
            return Labels.october;
        else if (monthInt == 11)
            return Labels.november;
        else if (monthInt == 12)
            return Labels.december;
        else
            return "Month";
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res,
                                                         int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap getCenteredCircularBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;
        if (bmp.getWidth() != radius || bmp.getHeight() != radius)
            sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
        else
            sbmp = bmp;
        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(),
                Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        @SuppressWarnings("unused")
        final int color = 0xffa19774;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(sbmp.getWidth() / 2 + 0.7f,
                sbmp.getHeight() / 2 + 0.7f, sbmp.getWidth() / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);

        return output;
    }

    @SuppressWarnings("deprecation")
    public static void turnGPSOn(Activity activity) {
        String provider = Settings.Secure.getString(
                activity.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!provider.contains("gps")) { // if gps is disabled
            Log.e("auto gps on ", " not allowing");
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings",
                    "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            activity.sendBroadcast(poke);
            OnGPS_auto(activity);
        } else {
            Log.e("auto gps on ", "  allowing");
            // OnGPS_auto(activity);
        }

    }

    @SuppressWarnings("deprecation")
    public static void turnGPSOff(Activity activity) {
        String provider = Settings.Secure.getString(
                activity.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (provider.contains("gps")) { // if gps is enabled

            final Intent poke = new Intent();
            poke.setClassName("com.android.settings",
                    "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            activity.sendBroadcast(poke);
        }
    }

    public static void OnGPS_auto(Activity activity) {
        try {
            Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
            intent.putExtra("enabled", true);
            activity.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void OffGPS_auto(Activity activity) {
        try {
            Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
            intent.putExtra("enabled", false);
            activity.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showFullImage(Activity _activity, String _pageTitle, String url, List<String> urls, int position) {

        String[] images = null;
        if (urls != null) {
            images = new String[urls.size()];
            for (int i = 0; i < urls.size(); i++) {
                images[i] = urls.get(i);
            }
        }

        if (isEmpty(_pageTitle))
            _pageTitle = _activity.getResources().getString(R.string.application_name) + " Image Browser";
//       Intent intent = new Intent(_activity, FullImageView.class);
//        intent.putExtra("ImageUri", url);
//        intent.putExtra("ImageTitle", _activity.getResources().getString(R.string.application_name) + " Image Browser");
        _activity.startActivity(FullImageView.createIntent(_activity, _pageTitle, url, images, position));

    }

    public static void showFullImageWithTitleList(Activity _activity, List<String> _pageTitle, String url, List<String> urls, int position) {

        String[] images = null;
        if (urls != null) {
            images = new String[urls.size()];
            for (int i = 0; i < urls.size(); i++) {
                images[i] = urls.get(i);
            }
        }
        String[] titles = null;
        if (_pageTitle != null) {
            titles = new String[_pageTitle.size()];
            for (int i = 0; i < _pageTitle.size(); i++) {
                titles[i] = _pageTitle.get(i);
            }
        }

//        if (_pageTitle))
//            _pageTitle = _activity.getResources().getString(R.string.application_name) + " Image Browser";

        _activity.startActivity(FullImageViewWithTitleList.createIntent(_activity, titles, url, images, position));
    }

//    public static void showFullImage(Activity _activity, String _pageTitle, String url, String[] urls) {
//
//
//        if (isEmpty(_pageTitle))
//            _pageTitle = _activity.getResources().getString(R.string.application_name) + " Image Browser";
//        Intent intent = new Intent(_activity, FullImageView.class);
//        intent.putExtra("ImageUri", url);
//        intent.putExtra("ImageTitle", _activity.getResources().getString(R.string.application_name) + " Image Browser");
//        _activity.startActivity(FullImageView.createIntent(_activity, _pageTitle, url, urls));
//}

    public static void clearApplicationData(Context context) {
        // File cache = context.getCacheDir();
        File appDir = getCacheDirectory(context);
        // File appDir = new File(cache.getParent());

        Log.d("appDir", appDir.toString());
        if (appDir.exists()) {
            deleteAllFilesAndFolder(appDir);
        }

        File dataDir = context.getFilesDir();
        Log.d("appDir", dataDir.toString());
        if (dataDir.exists()) {
            deleteAllFilesAndFolder(dataDir);
        }

        File database = context.getDatabasePath(DatabaseManager.dbFileName);
        Log.d("DB Path", database.toString());
        deleteFile(database.toString());
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

    public static boolean deleteAllFilesAndFolder(File dir) {
        if (dir.exists()) {
            File[] fileList = dir.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                Log.i("Deleting files", fileList[i].toString());
                fileList[i].delete();
            }
            return true;
        }
        return false;
    }

    public static boolean deleteFile(String file) {
        try {

            new File(file).delete();
            return true;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
        return false;
    }

    // our caching functions
    // Find the dir to save cached images
    public static File getCacheDirectory(Context context) {
        // String sdState = android.os.Environment.getExternalStorageState();
        File cacheDir;

        // if (sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
        // File sdDir = android.os.Environment.getExternalStorageDirectory();
        // // TODO : AW Change your diretcory here
        // cacheDir = new File(sdDir, "/tweetpicca/tac/.images");
        // Log.e("aw", " cache path : " + cacheDir.getAbsolutePath());
        // } else {
        cacheDir = context.getCacheDir();
        Log.e("aw", " cache path : " + cacheDir.getAbsolutePath());
        // }

        boolean success = true;
        if (!cacheDir.exists()) {
            success = cacheDir.mkdir();
            if (success) {
                Log.e("tag", "Directory created.");
            } else {
                Log.e("tag", "Directory creation failed.");
                cacheDir = context.getCacheDir();
            }
        }

        return cacheDir;
    }

    public static void loadSocialProfile(String url, Activity activity) {
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(browserIntent);
    }

    public static Uri getPhotoUriFromID(String id, Activity _activity) {
        try {
            Cursor cur = _activity
                    .getContentResolver()
                    .query(ContactsContract.Data.CONTENT_URI,
                            null,
                            ContactsContract.Data.CONTACT_ID
                                    + "="
                                    + id
                                    + " AND "
                                    + ContactsContract.Data.MIMETYPE
                                    + "='"
                                    + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE
                                    + "'", null, null);
            if (cur != null) {
                if (!cur.moveToFirst()) {
                    return null; // no photo
                }
            } else {
                return null; // error in cursor process
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Uri person = ContentUris.withAppendedId(
                ContactsContract.Contacts.CONTENT_URI, Long.parseLong(id));
        return Uri.withAppendedPath(person,
                ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
    }

    public static void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }

    public static void loadMapURLForRoute(Activity activity, String sourceLatitude,
                                          String sourceLongitude, String sourceName,
                                          String destinationLatitude, String destinationLongitude,
                                          String destinationName) {

        String uri = String.format(Locale.ENGLISH,
                "http://maps.google.com/maps?saddr=%s,%s(%s)&daddr=%s,%s (%s)",
                sourceLatitude, sourceLongitude, sourceName + " @ "
                        + sourceLatitude + "," + sourceLongitude,
                destinationLatitude, destinationLongitude, destinationName
                        + " @ " + destinationLatitude + ","
                        + destinationLongitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity");
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(uri));
                activity.startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                Toast.makeText(activity, "Please install a maps application",
                        Toast.LENGTH_LONG).show();
            }
        }
    }


    public static void loadMapURLForDrivingDirections(Activity activity, String sourceLatitude,
                                                      String sourceLongitude, String sourceName,
                                                      String destinationLatitude, String destinationLongitude,
                                                      String destinationName) {

        String uri = String.format(Locale.ENGLISH,
                "http://maps.google.com/maps?saddr=%s,%s(%s)&daddr=%s,%s (%s)",
                sourceLatitude, sourceLongitude, sourceName + " @ "
                        + sourceLatitude + "," + sourceLongitude,
                destinationLatitude, destinationLongitude, destinationName
                        + " @ " + destinationLatitude + ","
                        + destinationLongitude + "&mode=driving");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity");
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(uri));
                activity.startActivity(unrestrictedIntent);
            } catch (ActivityNotFoundException innerEx) {
                Toast.makeText(activity, "Please install a maps application",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public static boolean isLatLongOK(String lati, String longi) {
        if ((isEmpty(lati) || isEmpty(longi))
                || (lati.equals("0") && longi.equals("0"))
                || (lati.equals("0.0") && longi.equals("0.0"))) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isLatLongOK(double latitude, double longitude) {
        String lati = "" + latitude;
        String longi = "" + longitude;
        if ((isEmpty(lati) || isEmpty(longi))
                || (lati.equals("0") && longi.equals("0"))
                || (lati.equals("0.0") && longi.equals("0.0"))) {
            return false;
        } else {
            return true;
        }
    }

    public static ArrayList<String> getFileNames(final String folder,
                                                 final String fileNameFilterPattern, final int sort)
            throws PatternSyntaxException {
        ArrayList<String> myData = new ArrayList<String>();
        File fileDir = new File(folder);
        if (!fileDir.exists() || !fileDir.isDirectory()) {
            return null;
        }

        String[] files = fileDir.list();

        if (files.length == 0) {
            return null;
        }
        for (int i = 0; i < files.length; i++) {
            if (fileNameFilterPattern == null
                    || files[i].matches(fileNameFilterPattern))
                myData.add(files[i]);
        }
        if (myData.size() == 0)
            return null;

        if (sort != 0) {
            Collections.sort(myData, String.CASE_INSENSITIVE_ORDER);
            if (sort < 0)
                Collections.reverse(myData);
        }

        return myData;
    }

    public static String get_GoogleMaps_URL_4m_latitude_longitude(
            String latitude, String longitude) {
        return "http://maps.google.com/?q=" + latitude + "," + longitude;

    }

    public static String get_GoogleMaps_URL_4m_latitude_longitude(
            double latitude, double longitude) {
        return "http://maps.google.com/?q=" + latitude + "," + longitude;

    }

    public static String getPST_fromCurrTime(String currTime) {
        DateFormat utcFormat = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date;
        try {
            // "2012-08-15T22:56:02.038Z"
            date = utcFormat.parse(currTime);
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }

        DateFormat pstFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        pstFormat.setTimeZone(TimeZone.getTimeZone("PST"));

        String finalPSTime = pstFormat.format(date);
        System.out.println(finalPSTime);
        return "" + finalPSTime;
    }

    public static Date getPST_fromCurrTime(LocalDateTime date, String srcTz,
                                           String destTz) {
        DateTime srcDateTime = date.toDateTime(DateTimeZone.forID(srcTz));
        DateTime dstDateTime = srcDateTime.withZone(DateTimeZone.forID(destTz));
        return dstDateTime.toLocalDateTime().toDateTime().toDate();
    }

    public static String getUrlWithoutSlahesOrDoubleQoute(String Url) {
        String WithOutDoubleSlashes = "";
        if (Url.startsWith("//"))
            WithOutDoubleSlashes = Url.replaceAll("//", "/");
        if (Url.contains("\""))
            WithOutDoubleSlashes = Url.replaceAll("\"", "");
        else
            return Url;
        return WithOutDoubleSlashes;

    }

    public static String getUrlWithoutDoubleQuotes(String Url) {
        String wihOutDoubleQuotes = "";
        if (Url.contains("\""))
            wihOutDoubleQuotes = Url.replaceAll("\"", "");
        return wihOutDoubleQuotes;
    }

    public static String removeAllSlashes(String Url) {
        String jsonFormattedString = Url.replaceAll("\\\\", "");
        return jsonFormattedString;
    }


    public static String addSlaheAfterSingleSlahe(String imageUrl) {

        if (isEmpty(imageUrl))
            return imageUrl;

        String c = "";

        try {

            String a = Character.toString(imageUrl.charAt(5));
            String b = Character.toString(imageUrl.charAt(6));
            if (a.equals("/") && !b.equals("/")) {
                c = imageUrl;
                c = c.substring(0, 5) + "/" + c.substring(5, c.length());
            } else {
                c = imageUrl;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        return c;

    }

    public static Date getGMTTime(Date currentLocalTime) {
        return getGMT_convertedTime(currentLocalTime, "+0000");
    }


    public static Date getPKTime(Date currentLocalTime) {
        return getGMT_convertedTime(currentLocalTime, "+0500");
    }

    public static Date getGMT_convertedTime(Date currentLocalTime,
                                            String offset_GMT_2convert) {

        // currentLocalTime.setHours(currentLocalTime.getHours() + 2);
        DateFormat date = new SimpleDateFormat("Z");
//        String offset = date.format(currentLocalTime);
        String offset = "+0000";

//        Log.e("GMT ", "offset source: " + offset);
//        Log.e("GMT ", "offset target: " + offset_GMT_2convert);

        // int currGMT_hours = Integer.parseInt(offset.substring(0, 3));
        // int currGMT_Mins = Integer.parseInt(offset.substring(3, 5));

        int currGMT_hours = stringToint(offset.substring(0, 3));
        int currGMT_Mins = stringToint(offset.substring(3, 5));

//        Log.e("offset ", "Hours : " + currGMT_hours);
//        Log.e("offset ", "Mins : " + currGMT_Mins);
        int GMT_2convert = stringToint(offset_GMT_2convert.substring(0, 3));
        // int GMT_2convert = Integer
        // .parseInt(offset_GMT_2convert.substring(0, 3));

//        Log.e("GMT ", "GMT_2convert : " + GMT_2convert);
//
//        Log.e("gmt_diff", ": " + (0 + (GMT_2convert - currGMT_hours)));

        currentLocalTime.setHours(currentLocalTime.getHours()
                + (GMT_2convert - currGMT_hours));

        currentLocalTime.setMinutes(currentLocalTime.getMinutes()
                + (-(currGMT_Mins)));

//        Log.e("LocalTimeAfter",
//                "--"
//                        + ParseDate.getStringFromDate(currentLocalTime,
//                        ParseDate.FORMAT_YYYY_MM_DD_HH_mm_ss));

        return currentLocalTime;

    }

    public static int stringToint(String str) {
        int i = 0, number = 0;
        boolean isNegative = false;
        int len = str.length();
        if (str.charAt(0) == '-') {
            isNegative = true;
            i = 1;
        }
        while (i < len) {
            number *= 10;
            number += (str.charAt(i++) - '0');
        }
        if (isNegative)
            number = -number;
        return number;
    }

    public static String intToString(int n) {
        if (n == 0)
            return "0";
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            int curr = n % 10;
            n = n / 10;
            sb.append(curr);
        }
        String s = sb.substring(0);
        sb = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            sb.append(s.charAt(i));
        }
        return sb.substring(0);
    }

    public static void loadAd(Activity activity, AdView adView, AdSize adSize,
                              String adUnitID) {

        try {

            adView.setVisibility(View.INVISIBLE);

            if (adView.getVisibility() == View.INVISIBLE) {
                adView.setVisibility(View.GONE);
                return;
            }

            AdRequest adRequest = new AdRequest.Builder().build();
            adView.setAdSize(AdSize.SMART_BANNER);
            //adView.setAdUnitId(activity.getString(R.string.ad_bottom_banner_unit_id));

            // PublisherAdRequest.Builder publisherAdRequestBuilder = new
            // PublisherAdRequest.Builder();
            //
            // publisherAdRequestBuilder
            // .addTestDevice(PublisherAdRequest.DEVICE_ID_EMULATOR);
            // publisherAdRequestBuilder
            // .addTestDevice("C4A8A1818B2E42A929CEF580EDF897B0");

            adView.loadAd(adRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void writeToFile(Activity activity, String fileName, JSONObject data) {

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    activity.openFileOutput(fileName,
                            Context.MODE_PRIVATE));
            outputStreamWriter.write(data.toString());
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static String readFromLocalFile(Activity activity, String fileName) {

        String returnJsonObject = "";
        JSONObject jsonObject = null;

        try {
            InputStream inputStream = activity.openFileInput(fileName);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(
                        inputStream);
                BufferedReader bufferedReader = new BufferedReader(
                        inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                returnJsonObject = stringBuilder.toString();

            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return returnJsonObject;
    }

    public static String removeAllSpaceFromString(String myString) {
        String result = myString.replaceAll("[-+.^:,\\W]", "");
        return result;

    }

    public static String removeAllSingleQoute(String myString) {
        String result = myString.replaceAll("`", "");
        ;

        return result;

    }

    public static String removeOnlySpaceFromString(String myString) {
        String result = "";
        result = getAbsoluteNumber(myString.replaceAll("\\s+", ""));
        return result;

    }


    public static void writeToFile(Activity activity, String fileName,
                                   String data) {

        try {

            // File file = new File(activity.getFilesDir() + File.separator
            // + fileName);
            // File parent = file.getParentFile();
            // if (parent != null)
            // parent.mkdirs();
            // if (!file.exists())
            // file.mkdirs();
            // else if (!file.isDirectory() && file.canWrite()) {
            // file.delete();
            // file.mkdirs();
            // }

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    activity.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data.toString());
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            Log.e("Exception",
                    "File write failed: FileNotFoundException " + e.toString());
            e.printStackTrace();

            File file = new File(activity.getFilesDir(), fileName);
            File parent = file.getParentFile();
            if (parent != null)
                parent.mkdirs();
            if (!file.exists())
                file.mkdirs();
            else if (!file.isDirectory() && file.canWrite()) {
                file.delete();
                file.mkdirs();
            }

            OutputStreamWriter outputStreamWriter;
            try {
                outputStreamWriter = new OutputStreamWriter(
                        activity.openFileOutput(fileName, Context.MODE_PRIVATE));
                Log.e("Exception", "File writing now");
                outputStreamWriter.write(data.toString());
                outputStreamWriter.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        } catch (IOException e) {
            Log.e("Exception", "File write failed: IOException " + e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("Exception", "File write failed: Exception " + e.toString());
            e.printStackTrace();
        }
    }

    public static String readFromFileAssets(Activity activity, String fileName) {

        String data = "";

        try {
            InputStream inputStream = activity.getAssets().open(fileName + ".txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(
                        inputStream);
                BufferedReader bufferedReader = new BufferedReader(
                        inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                data = stringBuilder.toString();

            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("login activity", "Can not read file: " + e.toString());
            e.printStackTrace();
        }

        return data;
    }

    public static boolean isFileExist(Activity activity, String fileName) {

        try {

            File file = new File(activity.getFilesDir(), fileName);
            return file.exists();

        } catch (Exception e) {
            Log.e("Exception", "File write failed: Exception " + e.toString());
            e.printStackTrace();
            return false;
        }

    }

    public static void writeToFileNew(Activity activity, String fileName,
                                      String data) {

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    activity.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data.toString());
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String readFromFile(Activity activity, String fileName) {

        String returnData = "";

        try {
            InputStream inputStream = activity.openFileInput(fileName);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(
                        inputStream);
                BufferedReader bufferedReader = new BufferedReader(
                        inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                returnData = stringBuilder.toString();

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnData;
    }

    public static boolean checkInternet(Activity activity) {
        try {
            if (NetworkManager.isConnected(activity)) {
                return true;
            } else {
                handleNoNetwork(activity);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (NetworkManager.isConnected(activity)) {
                return true;
            } else {
                handleNoNetwork(activity);
                return false;
            }
        }

    }

    private static void handleNoNetwork(Activity activity) {
        UtilityFunctions.showToast_onCenter("Check your Internet Connection",
                activity);

    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            // getting application package name, as defined in manifest
            String packageName = context.getApplicationContext()
                    .getPackageName();

            // Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(
                    packageName, PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext()
                    .getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (NameNotFoundException e1) {
            e1.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return key;
    }

    public static void loadURL(Activity activity, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(browserIntent);
    }

    public static String getIslamicMonthNameById(int a) {

        String[] islamicMonths = {"", "Muharram", "Safar", "Rabi I", "Rabi II", "Jumada I", "Jumada II",
                "Rajab", "Shaban", "Ramadan", "Shawwal", "Dhul-Qadah", "Dhul-Hijjah"};

        return islamicMonths[a];
    }

    public static String HijriDate() {

        Calendar c = Calendar.getInstance();

        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);
        c.add(Calendar.DATE, -1);
        int date = c.get(Calendar.DATE);

        Log.e("before", "" + date);
        if (date == 31) {
            date = 30;
        }
        Log.e("after", "" + date);

        Chronology iso = ISOChronology.getInstanceUTC();
        Chronology hijri = IslamicChronology.getInstanceUTC();
        LocalDate todayHijri = null;
        try {
            LocalDate todayIso = new LocalDate(year,
                    month, date, iso);
            todayHijri = new LocalDate(todayIso.toDateTimeAtStartOfDay(),
                    hijri);
        } catch (Exception e) {

        }


        String getFormattedHijri = todayHijri.toString().substring(5);
        String HijriDateArray[] = getFormattedHijri.split("-");
        String newhijriDate = "";
        if (HijriDateArray[0].startsWith("0")) {
            newhijriDate = HijriDateArray[0].substring(1) + "-" + HijriDateArray[1];
        } else {
            newhijriDate = HijriDateArray[0] + "-" + HijriDateArray[1];
        }
        Log.e("HijriDate", "" + newhijriDate);
        return newhijriDate;

    }

    public static String replaceH1toH3(String inputText) {

        String removeH1Tags = inputText.replace("<h1>", "<h3 style=\"font-size:14px; border-bottom:1px solid #e6e6e6" + ";\">");
        String properData = removeH1Tags.replace("</h1>", "</h3>");

        return properData;

    }

    public static String getJustifyText4web(Activity activity, String text, String colorRGBA, String size) {

//        String removeH1Tags = text.replace("<h1>", "<h3 style=\"font-size:14px; border-bottom:1px solid #e6e6e6" + ";\">");
//        String properData = removeH1Tags.replace("</h1>", "</h3>");

        String properData = replaceH1toH3(text);
        String data = "<html><body style=\"text-align:justify; font-weight:lighter; color:" + colorRGBA + ";\">"
                + "<p>"
                + properData
                + "</p> "
                + "</body></html>";

        Log.e("HtmlData", "" + data);

        return data;
    }


    public static String getWebViewTextAW(String textData, int textSizeInPixels, boolean isArabic, String textAlignment,
                                          int lineHeight, String divDir) {

        String properData = replaceH1toH3(textData);

        String fontFamily = "";
        String lineHeight4Spacing = "";
        if (lineHeight > 0) {
            lineHeight4Spacing = "line-height:" + lineHeight + "px;";
        }

        if (isArabic) {
            fontFamily = "@font-face {"
                    + "font-family: 'Arabic-Font';"
                    + "src: url('Arabic-Font.ttf');} "
                    + "body {font-family: 'Arabic-Font';font-size: " + textSizeInPixels + "px;" +
                    lineHeight4Spacing + "text-align: " + textAlignment + ";}";
        } else {
            fontFamily = "@font-face {"
                    + "font-family: 'Roboto-Light';"
                    + "src: url('Roboto-Light.ttf');} "
                    + "body {font-family: 'Roboto-Light';font-size: " + textSizeInPixels + "px; " +
                    lineHeight4Spacing + "text-align: " + textAlignment + ";}";
        }

        String mHTMLHeader = "<html><head><style type=\"text/css\">\n"
                + fontFamily + "\n</style></head><body><div dir='" + divDir + "'>";

        String mHTMLFooter = "</div></body></html>";
        String mHtml = mHTMLHeader + "<div dir='" + divDir + "'>" + properData
                + "</div>" + mHTMLFooter;
        Log.e("mHtml", "" + mHtml);
        return mHtml;
    }

//    public static String getLeftText4web(String text, String colorRGBA, String size) {
//        //Color = "0,0,0,255";
//        String data = "<html><body style=\"text-align:left; font-size:18px;\">"
//                + "<p>"
//                + text
//                + "</p> "
//                + "</body></html>";
//
//        return data;
//    }
//
//    public static String getRightText4web(String text, String colorRGBA, String size) {
//        String fontFamily = "@font-face {"
//                + "font-family: 'Arabic-Font';"
//                + "src: url('Arabic-Font.ttf');} "
//                + "body {font-family: 'Arabic-Font';font-size: 30px;text-align: justify;}";
//        String mHTMLHeader = "<html><head><style type=\"text/css\">\n"
//                + fontFamily + "\n</style></head><body><div dir='rtl'>";
//
//
//        String mHTMLFooter = "</div></body></html>";
//
//        String mHtml = mHTMLHeader + "<div dir='rtl'>" + text
//                + "</div>" + mHTMLFooter;
//
//        Log.e("mHtml", "" + mHtml);
//
//        return mHtml;
//    }
//
//    public static String getTextForRoman(String text, String text_align, String size) {
//        String fontFamily = "@font-face {"
//                + "font-family: 'Arial-Font';"
//                + "src: url('Arial-Font.ttf');} "
//                + "body {font-family: 'Arial-Font';font-size: 30px;text-align:" + text_align + ";}";
//        String mHTMLHeader = "<html><head><style type=\"text/css\">\n"
//                + fontFamily + "\n</style></head><body><div dir='rtl'>";
//
//
//        String mHTMLFooter = "</div></body></html>";
//
//        String mHtml = mHTMLHeader + "<div dir='rtl'>" + text
//                + "</div>" + mHTMLFooter;
//
//        Log.e("mHtml", "" + mHtml);
//
//        return mHtml;
//    }
//
//    public static String getCenterText4webForBismillah(String text, String colorRGBA, String size) {
//        //Color = "0,0,0,255";
//        String fontFamily = "@font-face {"
//                + "font-family: 'Arabic-Font';"
//                + "src: url('Arabic-Font.ttf');} "
//                + "body {font-family: 'Arabic-Font';font-size: 30px;text-align: center;}";
//
//        String mHTMLHeader = "<html><head><style type=\"text/css\">\n"
//                + fontFamily + "\n</style></head><body><div dir='rtl'>";
//
//
//        String mHTMLFooter = "</div></body></html>";
//
//        String mHtml = mHTMLHeader + "<div dir='rtl'>" + text
//                + "</div>" + mHTMLFooter;
//
//        Log.e("mHtml", "" + mHtml);
//
//
//        return mHtml;
//    }
//
//    public static String getCenterText4web(String text, String colorRGBA, String size) {
//        //Color = "0,0,0,255";
//        String data = "<html><body style=\"text-align:center; font-size:18px;\">"
//                + "<p>"
//                + text
//                + "</p> "
//                + "</body></html>";
//
//
//        return data;
//    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 300 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int) px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

    public static String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    /**
     * Function to get Progress percentage
     *
     * @param currentDuration
     * @param totalDuration
     */
    public static int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

    /**
     * Function to change progress to timer
     *
     * @param progress      -
     * @param totalDuration returns current duration in milliseconds
     */
    public static int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }

    public static String html2text(String html) {
        String regexp = "<p>.*?</p>";
        String replace = "<br />";
        String result = html.replaceAll(regexp, replace);
        return result;
    }

    public static Bitmap retriveVideoFrameFromVideo(String videoPath)
            throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {

            MyLogs.printerror("retriveVideoFrameFromVideo : " + videoPath);

            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
                            + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

    public static String formatFileSize(long size) {
        String hrSize = null;

        double b = size;
        double k = size / 1024.0;
        double m = ((size / 1024.0) / 1024.0);
        double g = (((size / 1024.0) / 1024.0) / 1024.0);
        double t = ((((size / 1024.0) / 1024.0) / 1024.0) / 1024.0);

        DecimalFormat dec = new DecimalFormat("0.00");

        if (t > 1) {
            hrSize = dec.format(t).concat(" TB");
        } else if (g > 1) {
            hrSize = dec.format(g).concat(" GB");
        } else if (m > 1) {
            hrSize = dec.format(m).concat(" MB");
        } else if (k > 1) {
            hrSize = dec.format(k).concat(" KB");
        } else {
            hrSize = dec.format(b).concat(" Bytes");
        }

        return hrSize;
    }

    public static void playVideoInInternalPlayer(Activity activity, String completePath) {
        MyLogs.printerror("Start Playing Video : " + completePath);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(completePath));
        intent.setDataAndType(Uri.parse(completePath), "video/*");
        activity.startActivity(intent);
    }

    public static Boolean checkIsOkText(String text) {
        //Color = "0,0,0,255";
        if (!text.equals("0") && !text.equals("0.0") && !text.equals("0.00")) {
            return true;
        }


        return false;
    }

    // Converts to celcius
    public static float convertFahrenheitToCelcius(float fahrenheit) {
        return ((fahrenheit - 32) * 5 / 9);
    }

    // Converts to fahrenheit
    public static int convertCelciusToFahrenheit(int celsius) {
        return ((celsius * 9) / 5) + 32;
    }

    public static void openDialPad(Activity activity, String number) {
        Intent dial = new Intent();
        dial.setAction("android.intent.action.DIAL");
        dial.setData(Uri.parse("tel:" + number));
        dial.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(dial);
    }

    public static void openBrowser(Activity activity, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        activity.startActivity(i);
    }

    public boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }

    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String convert12HourTo24Hour(String completeTime) {
        MyLogs.printinfo("completeTime " + completeTime);
        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
        Date date = null;
        try {
            date = parseFormat.parse(completeTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return "" + displayFormat.format(date);
    }

    public static void createFacebookHashKey(Activity activity) {
        PackageInfo info;
        try {
            info = activity.getPackageManager().getPackageInfo("com.hblabs.pingemvendor", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }


    }

    public static boolean checkPlayServices(Activity activity) {
        int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {

                UtilityFunctions
                        .showToast_onCenter(
                                "Your Phone is not supported to Receive Notifications. Kindly upgrade Google Play Services",
                                activity);

            }
            return false;
        }
        return true;
    }

    public static ArrayList<String> getPhoneContactList(Activity activity) {
        ArrayList<String> names = new ArrayList<String>();
        ContentResolver cr = activity.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor cur1 = cr.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        new String[]{id}, null);
                while (cur1.moveToNext()) {
                    //to get the contact names
                    String name = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    Log.e("Name :", name);
                    String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    Log.e("Email", email);
                    if (email != null) {
                        names.add(name);
                    }
                }
                cur1.close();
            }
        }
        return names;
    }

//    public static ArrayList<PhoneContact> getPhoneContactListOptimized(Activity activity) {
//        ArrayList<PhoneContact> phoneContacts = new ArrayList<>();
//        PhoneContact phoneContact;
//        ContentResolver cr = activity.getContentResolver();
//        int i = 0;
//        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//        if (cur.getCount() > 0) {
//            while (cur.moveToNext()) {
//                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
//                Cursor cur1 = cr.query(
//                        ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
//                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
//                        new String[]{id}, null);
//                while (cur1.moveToNext()) {
//                    //to get the contact names
//
//                    phoneContact = new PhoneContact();
//                    String name = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                    Log.e("Name :", name);
//                    String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
//                    Log.e("Email", email);
//                    String number = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                    if (email != null) {
//                        phoneContact.setName(name);
//                        phoneContact.setContact(email);
//                        phoneContact.setContact_imported_from("phone");
//                        if (email.contains("@")) {
//                            phoneContact.setType("email");
//                        } else {
//                            phoneContact.setType("phone");
//                        }
//
//                    }
//                    phoneContacts.add(phoneContact);
//                }
//                cur1.close();
//            }
//        }
//        return phoneContacts;
//    }

    public static void putvaluesToJson(String name, String email, String number, String contact_imported_from) {
        JSONObject jObjectData = new JSONObject();
        try {
            jObjectData.put("name", name);
            jObjectData.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jObjectData.toString());
        String prettyJsonString = gson.toJson(je);

        Log.e("syncing friends", prettyJsonString.toString());
    }

    public static ArrayList<PhoneContact> readContacts(Activity activity) {
        ArrayList<PhoneContact> phoneContacts = new ArrayList<>();
        PhoneContact phoneContact = null;

        ContentResolver cr = activity.getContentResolver();
        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
                + ("1") + "'";
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
                + " COLLATE LOCALIZED ASC";
        Cursor cur = cr.query(
                ContactsContract.Contacts.CONTENT_URI, null, selection
                        + " AND " + ContactsContract.Contacts.HAS_PHONE_NUMBER
                        + "=1", null, sortOrder);
        String emailContact = null;
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {

                phoneContact = new PhoneContact();

                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                if (!isEmpty(id)) {
                    phoneContact.setContact_id(Integer.parseInt(id));
                }
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    if (!isEmpty(name)) {

                        if (name.contains(" ")) {
                            phoneContact.setFirst_name(spaceSplit(name)[0]);
                            phoneContact.setLast_name(spaceSplit(name)[1]);

                        } else {
                            phoneContact.setFirst_name(name);
                            phoneContact.setLast_name("");
                        }
                    }
                    try {
                        JSONObject numberJson = new JSONObject();
                        if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                            Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                            while (pCur.moveToNext()) {
                                String phoneType = "";
                                int type = pCur.getInt(pCur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.TYPE));
                                switch (type) {
                                    case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                        phoneType = "secondary_number";
                                        break;
                                    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                        phoneType = "primary_number";
                                        break;
                                    case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                        phoneType = "other_number";
                                        break;
                                    case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME:
                                        phoneType = "other_number";
                                        break;
                                    case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK:
                                        phoneType = "other_number";
                                        break;
                                    default:
                                        phoneType = "other_number";
                                        break;
                                }
                                String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                //  if (phoneNo.length() > 12)
                                numberJson.put(phoneType, getstandarizeNumber(phoneNo, activity));
                            }

                            phoneContact.setNumber(numberJson.toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                    try {
                        int i = 0;
                        while (emailCur.moveToNext()) {
                            JSONObject emailJson = new JSONObject();

                            emailContact = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            if (!isEmpty(emailContact)) {
                                i++;
                                if (i == 1) {
                                    emailJson.put("primary_email", emailContact);
                                } else if (i == 2) {
                                    emailJson.put("secondary_email", emailContact);
                                } else {
                                    emailJson.put("other_email", emailContact);
                                }


                            }

                            phoneContact.setEmail(emailJson.toString());
                        }
                        emailCur.close();
                    } catch (Exception e) {

                    }

                }
                if (!isEmpty(phoneContact.getFirst_name())) {

                    phoneContacts.add(phoneContact);

                }

            }
        }
        return phoneContacts;
    }

    public static String getstandarizeNumber(String number, Activity activity) {
        String countryCode = "";

        TelephonyManager tMgr = (TelephonyManager) activity.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        //  String myCountryCode = isoPhone.getPhone(tMgr.getNetworkCountryIso()).replace("+", "");
        String myCountryCode = AppPreferences.getString(AppPreferences.PREF_USER_COUNTRY_CODE).replace("+", "");
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try {


            if (number.startsWith("+")) {
                Phonenumber.PhoneNumber numberProto = phoneUtil.parse(number, "");
                countryCode = "" + numberProto.getCountryCode();
            } else {
                if (number.startsWith("00")) {
                    number = number.replaceFirst("^0+(?!$)", "");
                    number = "+" + number;
                    Phonenumber.PhoneNumber numberProto = phoneUtil.parse(number, "");
                    countryCode = "" + numberProto.getCountryCode();
                } else {
                    number = number.replaceAll("[\\D]", "");
                    number = number.replaceFirst("^0+(?!$)", "");
                    number = myCountryCode + number;
                    countryCode = myCountryCode;
                    //MyLogs.printinfo("getSimNumber" + countryCode);
                }
            }
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }


        number = number.replaceAll("[\\D]", "");
        number = number.replaceFirst("^0+(?!$)", "");


        MyLogs.printinfo("country_code " + number + " : " + countryCode);
        String prefix = "";
        if (countryCode.length() == 1) {
            prefix = "00";
        } else if (countryCode.length() == 2) {
            prefix = "0";
        } else {
            prefix = "0";
        }


        MyLogs.printinfo("final_number " + prefix + number);
        return prefix + number;


    }

//    public static ArrayList<PhoneContact> readContacts(Activity activity) {
//        ArrayList<PhoneContact> phoneContacts = new ArrayList<>();
//        PhoneContact phoneContact = null;
//        HashMap<String, String> meMap;
//
//        ContentResolver cr = activity.getContentResolver();
//        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
//                + ("1") + "'";
//        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
//                + " COLLATE LOCALIZED ASC";
//        Cursor cur = cr.query(
//                ContactsContract.Contacts.CONTENT_URI, null, selection
//                        + " AND " + ContactsContract.Contacts.HAS_PHONE_NUMBER
//                        + "=1", null, sortOrder);
//        String phone = null;
//        String emailContact = null;
//        if (cur.getCount() > 0) {
//            while (cur.moveToNext()) {
//                meMap = new HashMap<String, String>();
//                phoneContact = new PhoneContact();
//                phoneContact.setContact_imported_from("phone");
//                meMap.put("contact_imported_from", "phone");
//
//                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
//                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
//                    if (!isEmpty(name)) {
//                        phoneContact.setName(name);
//                        meMap.put("name", name);
//                    }
//                    try {
//                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
//                        while (pCur.moveToNext()) {
//                            phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            if (!isEmpty(phone)) {
//                                phoneContact.setContact(phone);
//                                phoneContact.setType("phone");
//                                meMap.put("phone", phone);
//
//                            }
//                        }
//                        pCur.close();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
//                    try {
//                        while (emailCur.moveToNext()) {
//                            emailContact = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
//                            if (!isEmpty(emailContact)) {
//                                phoneContact.setContact(emailContact);
//                                phoneContact.setType("email");
//                                meMap.put("contact", emailContact);
//                                meMap.put("type", "email");
//                            }
//
//
//                        }
//                        emailCur.close();
//                    } catch (Exception e) {
//
//                    }
//
//                }
//                if (!isEmpty(phoneContact.getName())) {
//                    if (phoneContact.getContact().length() > 12) {
//                        if (isFormattedNumber(phoneContact.getContact())) {
//                            phoneContacts.add(phoneContact);
//                            MyLogs.printinfo("new formatted number " + phoneContact.getContact());
//                        }
//
//                    }
//
//
//                }
//
//            }
//        }
//        return phoneContacts;
//    }

    public static JSONArray createSocailMediaContactJson(ArrayList<org.brickred.socialauth.Contact> socialContacts, String importFrom) {
        JSONObject jsonObject;
        JSONArray finaljsonArray = new JSONArray();

        for (int i = 0; i < socialContacts.size(); i++) {
            jsonObject = new JSONObject();
            try {
                jsonObject.put("name", socialContacts.get(i).getDisplayName());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                jsonObject.put("contact", socialContacts.get(i).getEmail());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                jsonObject.put("type", "email");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                jsonObject.put("contact_imported_from", importFrom);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finaljsonArray.put(jsonObject);


        }
        return finaljsonArray;

    }

    public static void openCalendarOnly(Activity activity) {
        //        Intent i = new Intent();
//        ComponentName cn = new ComponentName("com.android.calendar", "com.android.calendar.LaunchActivity");
//        i.setComponent(cn);
//        activity.startActivity(i);

        Intent i = new Intent(Intent.ACTION_VIEW);
        // Android 2.2+
        i.setData(Uri.parse("content://com.android.calendar/time"));
        // Before Android 2.2+
        //i.setData(Uri.parse("content://calendar/time"));


        activity.startActivity(i);
    }

    public static String openDatePickerDialoge(Activity activity) {
        // Get Current Date
        final String[] date = {""};
        int mYear, mMonth, mDay;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        //txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        date[0] = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
        return date[0];
    }

    public static long addEvent(Activity context, String SqlDate,
                                String title, int estimated_hour) {


        MyLogs.printinfo("test_calendar");
        String getSqlDateObject[] = SqlDate.split("\\s+");
        MyLogs.printinfo("" + getSqlDateObject[0]);
        int time_diff = estimated_hour * 60;
        String getDate[] = getSqlDateObject[0].split("-");
        String getTime[] = getSqlDateObject[1].split(":");

        long _eventId = 0;
        GregorianCalendar calDate = new GregorianCalendar(Integer.parseInt(getDate[0]), Integer.parseInt(getDate[1]) - 1,
                Integer.parseInt(getDate[2]), Integer.parseInt(getTime[0]), Integer.parseInt(getTime[1]));

        try {
            ContentResolver cr = context.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, calDate.getTimeInMillis());
            //  values.put(CalendarContract.Events.DTEND, calDate.getTimeInMillis() + 60 * 60 * 1000);
            values.put(CalendarContract.Events.DTEND, calDate.getTimeInMillis() + time_diff * 60 * 1000);
            values.put(CalendarContract.Events.TITLE, title);
            values.put(CalendarContract.Events.CALENDAR_ID, 1);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance()
                    .getTimeZone().getID());
            System.out.println("calendar " + Calendar.getInstance().getTimeZone().getID());
            //Uri uri = null;
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                return 0;
            }
//            Cursor cursor = DBHelper.getInstance(context).getRecordCursor("events",
//                    "appointment_id", appointmentId);
//            if (cursor.moveToFirst()) {
//                MyLogs.printinfo("cursor is not null");
//            } else {
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
            // Save the eventId into the Task object for possible future delete.
            _eventId = Long.parseLong(uri.getLastPathSegment());
            try {


//                    DBHelper.getInstance(context).insertSingleRecord((int) _eventId, appointmentId, last_date,
//                            "events");
//                    MyLogs.printinfo("cursor null" + _eventId);
                setReminder(cr, _eventId, 1440, context);


            } catch (Exception e) {

                e.printStackTrace();
            }

            //}


        } catch (Exception e) {
            e.printStackTrace();
        }
        return _eventId;
    }

    public static void deleteEvent(ContentResolver resolver, Uri eventsUri, int calendarId) {
        int iNumRowsDeleted = 0;

        MyLogs.printinfo("event_id 2 " + calendarId);
        Uri eventUri = ContentUris.withAppendedId(eventsUri, calendarId);
        iNumRowsDeleted = resolver.delete(eventUri, null, null);

        MyLogs.printinfo("Deleted " + iNumRowsDeleted + " calendar entry.");

    }


    public static void deleteEventNew(Activity activity, long id) {
        MyLogs.printinfo("event_id 2 " + id);
        int iNumRowsDeleted = 0;

        Uri eventsUri = Uri.parse("content://com.android.calendar/" + "events");
        Uri eventUri = ContentUris.withAppendedId(eventsUri, id);
        iNumRowsDeleted = activity.getContentResolver().delete(eventUri, null, null);

        Log.i("calendar_deleted ", "Deleted " + iNumRowsDeleted + " calendar entry.");

        //   return iNumRowsDeleted;
//        Uri eventsUri = Uri.parse("content://com.android.calendar/events");
//        ContentResolver cr = activity.getContentResolver();
//        Cursor cursor;
//        cursor = cr.query(eventsUri, new String[]{"_id"}, "calendar_id=" + id, null, null);
//        while (cursor.moveToNext()) {
//            long eventId = cursor.getLong(cursor.getColumnIndex("_id"));
//            cr.delete(ContentUris.withAppendedId(eventsUri, eventId), null, null);
//        }
//        cursor.close();

    }


    public static ArrayList<Integer> getEventsId(Context activity) {


        DBHelper.getInstance(activity).opendb();

        ArrayList<Integer> list = new ArrayList<>();
        Cursor cursor = DBHelper.getInstance(activity)
                .getallRecordsCursor("events");

        try {
            if (cursor != null) {
                if (cursor.moveToFirst())
                    do {

                        list.add(cursor.getInt(cursor
                                .getColumnIndex("id")));
                    } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cursor != null)
            cursor.close();
        DBHelper.getInstance(activity).closedb();

        return list;
    }

    public static void setReminder(ContentResolver cr, long eventID, int timeBefore, Context context) {
        try {
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Reminders.MINUTES, timeBefore);
            values.put(CalendarContract.Reminders.EVENT_ID, eventID);
            values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            MyLogs.printinfo("Calendar ");
            Uri uri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
            Cursor c = CalendarContract.Reminders.query(cr, eventID,
                    new String[]{CalendarContract.Reminders.MINUTES});
            if (c.moveToFirst()) {
                System.out.println("calendar"
                        + c.getInt(c.getColumnIndex(CalendarContract.Reminders.MINUTES)));
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // function to remove an event from the calendar using the eventId stored within the Task object.
    public void removeEvent(Context context) {
//        ContentResolver cr = context.getContentResolver();
//
//        int iNumRowsDeleted = 0;
//
//        Uri eventsUri = Uri.parse(CALENDAR_URI_BASE+"events");
//        Uri eventUri = ContentUris.withAppendedId(eventsUri, this._eventId);
//        iNumRowsDeleted = cr.delete(eventUri, null, null);
//
//        Log.i(" Deleted ", "Deleted " + iNumRowsDeleted + " calendar entry.");

    }


//    public int updateEvent(Context context) {
//        int iNumRowsUpdated = 0;
////        GregorianCalendar calDate = new GregorianCalendar(this._year, this._month, this._day, this._hour, this._minute);
////
////        ContentValues event = new ContentValues();
////
////        event.put(CalendarContract.Events.TITLE, this._title);
////        event.put("hasAlarm", 1); // 0 for false, 1 for true
////        event.put(CalendarContract.Events.DTSTART, calDate.getTimeInMillis());
////        event.put(CalendarContract.Events.DTEND, calDate.getTimeInMillis()+60*60*1000);
////
////        Uri eventsUri = Uri.parse(CALENDAR_URI_BASE+"events");
////        Uri eventUri = ContentUris.withAppendedId(eventsUri, this._eventId);
////
////        iNumRowsUpdated = context.getContentResolver().update(eventUri, event, null,
////                null);
////
////        // TODO put text into strings.xml
////        Log.i(DEBUG_TAG, "Updated " + iNumRowsUpdated + " calendar entry.");
////
////        return iNumRowsUpdated;
//    }

    public static long pushAppointmentsToCalender(Context curActivity,
                                                  String title,
                                                  String addInfo,
                                                  String place,
                                                  int status,
                                                  long startDate,
                                                  boolean needReminder,
                                                  boolean needMailService) {
        /***************** Event: note(without alert) *******************/

        String eventUriString = "content://com.android.calendar/events";
        ContentValues eventValues = new ContentValues();

        eventValues.put("calendar_id", 1); // id, We need to choose from
        // our mobile for primary
        // its 1
        eventValues.put("title", title);
        eventValues.put("description", addInfo);
        eventValues.put("eventLocation", place);

        long endDate = startDate + 1000 * 60 * 60; // For next 1hr

        eventValues.put("dtstart", startDate);
        eventValues.put("dtend", endDate);

        // values.put("allDay", 1); //If it is bithday alarm or such
        // kind (which should remind me for whole day) 0 for false, 1
        // for true
        eventValues.put("eventStatus", status); // This information is
        // sufficient for most
        // entries tentative (0),
        // confirmed (1) or canceled
        // (2):

   /*Comment below visibility and transparency  column to avoid java.lang.IllegalArgumentException column visibility is invalid error */

    /*eventValues.put("visibility", 3); // visibility to default (0),
                                        // confidential (1), private
                                        // (2), or public (3):
    eventValues.put("transparency", 0); // You can control whether
                                        // an event consumes time
                                        // opaque (0) or transparent
                                        // (1).
      */
        eventValues.put("hasAlarm", 1); // 0 for false, 1 for true

        Uri eventUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(eventUriString), eventValues);
        long eventID = Long.parseLong(eventUri.getLastPathSegment());

        if (needReminder) {
            /***************** Event: Reminder(with alert) Adding reminder to event *******************/

            String reminderUriString = "content://com.android.calendar/reminders";

            ContentValues reminderValues = new ContentValues();

            reminderValues.put("event_id", eventID);
            reminderValues.put("minutes", 5); // Default value of the
            // system. Minutes is a
            // integer
            reminderValues.put("method", 1); // Alert Methods: Default(0),
            // Alert(1), Email(2),
            // SMS(3)

            Uri reminderUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
        }

        /***************** Event: Meeting(without alert) Adding Attendies to the meeting *******************/

        if (needMailService) {
            String attendeuesesUriString = "content://com.android.calendar/attendees";

            /********
             * To add multiple attendees need to insert ContentValues multiple
             * times
             ***********/
            ContentValues attendeesValues = new ContentValues();

            attendeesValues.put("event_id", eventID);
            attendeesValues.put("attendeeName", "xxxxx"); // Attendees name
            attendeesValues.put("attendeeEmail", "yyyy@gmail.com");// Attendee
            // E
            // mail
            // id
            attendeesValues.put("attendeeRelationship", 0); // Relationship_Attendee(1),
            // Relationship_None(0),
            // Organizer(2),
            // Performer(3),
            // Speaker(4)
            attendeesValues.put("attendeeType", 0); // None(0), Optional(1),
            // Required(2), Resource(3)
            attendeesValues.put("attendeeStatus", 0); // NOne(0), Accepted(1),
            // Decline(2),
            // Invited(3),
            // Tentative(4)

            Uri attendeuesesUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(attendeuesesUriString), attendeesValues);
        }

        return eventID;

    }

    public static boolean isContainSpaces(String s) {
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(s);
        boolean found = matcher.find();
        return found;
    }

    public static boolean isFormattedNumber(String number) {
        if (number.contains(" "))
            return false;
        else if (number.contains("("))
            return false;
        else
            return true;

    }

    public static String[] spaceSplit(String number) {

        return number.split("\\s+");
    }

    public static String getAbsoluteNumber(String number) {

        //  result = myString.replaceAll("[\\s\\-()]", "");
        String myString = "";
        myString = number.replaceAll("[\\s\\-()]", "");
        myString = number.replaceAll("[\\D]", "");

        return myString;
    }

    public static String FormatteMyNumber(String number) {
        String myString = "";
        if (number.contains("001")) {
            NumberFormat nf = NumberFormat.getInstance(Locale.US);
            myString = nf.format(number);

        } else {
            myString = NumberFormat.getInstance().format(number);

        }


        return myString;
    }

    public static String FormatteInterNationalNumber(String numberStr) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        String number = "";
        MyLogs.printinfo("numberStr " + numberStr);
        try {
            if (number.contains("001")) {
                Phonenumber.PhoneNumber numberProto = phoneUtil.parse(numberStr, "US");
                number = phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            } else {
                Phonenumber.PhoneNumber numberProto = phoneUtil.parse(numberStr, "PK");
                number = phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);

            }

        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }

        return number;


    }

    public static String FormatteSqlDateToDisplay(String date) {
        MyLogs.printinfo(" date " + date);
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        String new_date = "";
        try {
            Date d = df1.parse(date);
            new_date = df2.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new_date;
    }

    public static String HourTo24hour(String time) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
        Date date = null;
        try {
            date = parseFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return displayFormat.format(date);
        //System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));
    }

    // Identifier for the permission request


    // Called when the user is performing an action which requires the app to read the
    // user's contacts
    public static void getPermissionToReadUserContacts(Activity activity) {

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity.shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_CONTACTS)) {
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                        READ_CONTACTS_PERMISSIONS_REQUEST);
            }
        }
    }


    public static String getDisplayDateTime(String datetime) {

        String date_time[] = spaceSplit(datetime);

        String timeonly[] = date_time[1].split(":");

        int hours = Integer.parseInt(timeonly[0]);
        int mints = Integer.parseInt(timeonly[1]);
        String new_hour, new_mint;
        int hour;
        String am_pm;
        if (hours > 12)         //hourofDay =13
        {
            hour = hours - 12;     //hour=1
            am_pm = "PM";                   //PM
        } else {
            hour = hours;
            am_pm = "AM";
        }

        if (hour < 10) {
            new_hour = "0" + hour;
        } else {
            new_hour = "" + hour;
        }
        if (mints < 10) {
            new_mint = "0" + mints;
        } else {
            new_mint = "" + mints;
        }


        String time = new_hour + ":" + new_mint + " " + am_pm;

        String dateTime = date_time[0] + " " + time;

        return dateTime;
    }

    public static String getcurrentDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        return formattedDate;

    }

    public static String formatteSqlDateTime(String sqlDate) {

        String date = spaceSplit(sqlDate)[0];
        int month = Integer.parseInt(date.split("-")[1]);

        String monthString = "";
        monthString = new DateFormatSymbols().getMonths()[month - 1];


        String formattedDate = monthString + " " + ordinal(Integer.parseInt(date.split("-")[2])) + ", " + date.split("-")[0] + " " + convert24HourTo12Hour(spaceSplit(sqlDate)[1]);

        return formattedDate;

    }

    public static String formatteSqlTime(String datetime) {

//        String yy = spaceSplit(String.valueOf(datetime))[5];
//        String mm = "" + monthNumber(spaceSplit(String.valueOf(datetime))[1]);
//        String dd = spaceSplit(String.valueOf(datetime))[2];
//        String time = spaceSplit(String.valueOf(datetime))[3];
//        String sqlDate = yy + "-" + mm + "-" + dd + " " + time;

        MyLogs.printinfo("sqlDate " + datetime);
        String formattedDate = convert24HourTo12Hour(spaceSplit(datetime)[1]);

        return formattedDate;

    }

    public static String mysqlDateTime() {
        SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        Date inputDate = null;
        try {
            inputDate = fmt.parse(getcurrentDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

// Create the MySQL datetime string
        fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = fmt.format(inputDate);
        return dateString;
    }

    public static String convert24HourTo12Hour(String time) {
        MyLogs.printinfo(" time " + time);


        DateFormat f1 = new SimpleDateFormat("HH:mm"); //HH for hour of the day (0 - 23)
        Date d = null;
        try {
            d = f1.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat f2 = new SimpleDateFormat("h:mma");


        return f2.format(d).toLowerCase();
    }

    public static String formatteSqlDate(String datetime) {
//        String yy = spaceSplit(String.valueOf(datetime))[5];
//        String mm = "" + monthNumber(spaceSplit(String.valueOf(datetime))[1]);
//        String dd = spaceSplit(String.valueOf(datetime))[2];
//        String time = spaceSplit(String.valueOf(datetime))[3];
//        String sqlDate = yy + "-" + mm + "-" + dd + " " + time;
//
//        MyLogs.printinfo("sqlDate " + sqlDate);

        String date = spaceSplit(datetime)[0];
        int month = Integer.parseInt(date.split("-")[1]);
        int year = Integer.parseInt(date.split("-")[0]);

        String monthString = "";
        monthString = new DateFormatSymbols().getMonths()[month - 1];
        String formattedDate = dayName(datetime, "yyyy-MM-dd HH:mm:ss") + ", " + monthString + " " + ordinal(Integer.parseInt(date.split("-")[2])) + ", " + year;

        return formattedDate;

    }

    public static String dayName(String inputDate, String format) {
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
    }

    public static String converMillisToDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        return formatter.format(calendar.getTime());
    }

    public static long currentTimeInMillis() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        String myDate = format.format(new Date());
        return convertDateToMillis(myDate);
    }

    public static String currentDateTime() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        String myDate = format.format(new Date());
        return myDate;
    }

    public static long convertDateToMillis(String givenDateString) {
        //  String givenDateString = "Tue Apr 23 16:08:28 GMT+05:30 2013";
        // MyLogs.printinfo("givenDateString " + givenDateString);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeInMilliseconds = 0;
        try {
            Date mDate = sdf.parse(givenDateString);
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }

    public static String formatteSqlDateLikeWhatsApp(String sqlDate) {

        String date = spaceSplit(sqlDate)[0];


        String formattedDate = date.split("-")[1] + "/" + date.split("-")[2] + "/" + date.split("-")[0];

        return formattedDate;

    }

    public static String ordinal(int i) {
        String[] sufixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + sufixes[i % 10];

        }
    }

    public static String formattedCurrency(Double amount) {
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
        //ystem.out.println("US: " + );
        return defaultFormat.format(amount);
    }

    public static String removeRedandencyFromNumber(String inputPhoneNumber) {
        inputPhoneNumber = inputPhoneNumber.replace(" ", "");
        inputPhoneNumber = inputPhoneNumber.replace("+", "00");
        try {
            inputPhoneNumber = inputPhoneNumber.replace("-", "");
        } catch (Exception e) {

        }
        return inputPhoneNumber;
    }

    public static void showSoftKeyboard(Activity activity, View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static String getFormattedNumberToDisplay(Activity activity, String number) {
        PhoneFormat phoneFormat = new PhoneFormat(activity);
        MyLogs.printinfo("number " + number);

        String numberString, text;
        text = number.replace("0011", "001");
        if (text.startsWith("00")) {
            numberString = text.substring(2);
        } else {
            numberString = text.substring(1);
        }
        String formattedString = phoneFormat.format("+" + numberString);
        return formattedString;
    }

    public static Bitmap getBitmap(String _path) {
        Bitmap bitmap = null;

        ExifInterface exif = null;
        try {
            exif = new ExifInterface(_path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);

        int rotate = 0;

        switch (exifOrientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                rotate = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                rotate = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                rotate = 270;
                break;
        }

        if (rotate != 0) {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();

            // Setting pre rotate
            Matrix mtx = new Matrix();
            mtx.preRotate(rotate);

            // Rotating Bitmap & convert to ARGB_8888, required by tess
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
        }
        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        return bitmap;

    }

    public static void directCall(Activity activity, String number) {

        MyLogs.printinfo("number " + UtilityFunctions.getFormattedNumberToDisplay(activity, number));
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + UtilityFunctions.getFormattedNumberToDisplay(activity, number)));

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        activity.startActivity(callIntent);
    }

    public static Date toDate(String dateString) {
        MyLogs.printinfo("dateString " + dateString);

        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            date = formatter.parse(dateString);
            // String d = formatter.format(date);
            // String formattedTime = output.format(date);
            Log.e("Print result: ", String.valueOf(date));

        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        // Sun Sep 25 00:44:59 GMT+05:00 2016
        return date;
//        String yy = spaceSplit(String.valueOf(date))[5];
//        String mm = ""+monthNumber(spaceSplit(String.valueOf(date))[1]);
//        String dd=spaceSplit(String.valueOf(date))[2];
//        String time=spaceSplit(String.valueOf(date))[3];
//        return yy+"-"+mm+"-"+dd +" "+time;
    }

    public static int monthNumber(String monthName) {
        Date date = null;
        try {
            date = new SimpleDateFormat("MMMM").parse(monthName);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);

    }

    public static String converStringToBase64(String url) {

        MyLogs.printinfo("url " + url);
        Bitmap bm = BitmapFactory.decodeFile(url);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();

//        byte[] data = new byte[0];
//        try {
//            data = url.getBytes("UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        String base64 = Base64.encodeToString(b, Base64.DEFAULT);
        return base64;
    }

}
