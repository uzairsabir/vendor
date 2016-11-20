package org.vanguardmatrix.engine.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

public class DeviceTools {
    public DeviceTools() {

    }

    ;

    public static NetworkClass getNetworkClass(Context context) {

		/* Check if the device is connected to a Network */
        if (!isUsingWiFi(context) && !isUsingMobileData(context))
            return NetworkClass.NETWORK_CLASS_NOT_CONNECTED;

		/* If WiFi Conntection */
        if (isUsingWiFi(context))
            return NetworkClass.NETWORK_CLASS_WIFI;

		/* Check for other Data Services */
        TelephonyManager mTelephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        switch (mTelephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:

                return NetworkClass.NETWORK_CLASS_2G;

            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:

                return NetworkClass.NETWORK_CLASS_3G;

            case TelephonyManager.NETWORK_TYPE_LTE:
                return NetworkClass.NETWORK_CLASS_4G;

            default:
                return NetworkClass.NETWORK_CLASS_UNKNOWN;
        }

    }

    public static boolean isUsingWiFi(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiInfo = connectivity
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifiInfo != null
                && wifiInfo.getState() == NetworkInfo.State.CONNECTED
                || wifiInfo.getState() == NetworkInfo.State.CONNECTING) {
            return true;
        }

        return false;
    }

    public static boolean isUsingMobileData(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo mobileInfo = connectivity
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mobileInfo != null
                && mobileInfo.getState() == NetworkInfo.State.CONNECTED
                || mobileInfo.getState() == NetworkInfo.State.CONNECTING) {
            return true;
        }

        return false;
    }

    public static double getLinkSpeed(Context context) {
        if (isUsingWiFi(context)) {
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            // WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//            if (wifiInfo != null) {
//                return wifiInfo.getLinkSpeed(); // measured using
//                // WifiInfo.LINK_SPEED_UNITS
//            }

        }
        return 0.0;
    }

    public static int getNumCores() {
        // Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                // Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]+", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            // Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            // Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            // Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            // Default to return 1 core
            return 1;
        }
    }

    public static long getMemoryTotal() {
        String commandResult;

        commandResult = shellGetMemoryInfo().replaceAll("\\s+", ",");

        String[] memoryInfo = commandResult.split(",");

        return Long.parseLong(memoryInfo[1]);
    }

    public static long getMemoryFree() {
        String commandResult;

        commandResult = shellGetMemoryInfo().replaceAll("\\s+", ",");

        String[] memoryInfo = commandResult.split(",");

        return Long.parseLong(memoryInfo[3]);
    }

    public static long getMemoryUsed() {
        String commandResult;

        commandResult = shellGetMemoryInfo().replaceAll("\\s+", ",");

        String[] memoryInfo = commandResult.split(",");

        return Long.parseLong(memoryInfo[2]);
    }

    public static long getMemoryShared() {
        String commandResult;

        commandResult = shellGetMemoryInfo().replaceAll("\\s+", ",");

        String[] memoryInfo = commandResult.split(",");

        return Long.parseLong(memoryInfo[4]);
    }

    public static long getMemoryBuffers() {
        String commandResult;

        commandResult = shellGetMemoryInfo().replaceAll("\\s+", ",");

        String[] memoryInfo = commandResult.split(",");

        return Long.parseLong(memoryInfo[5]);
    }

    public static long getMemoryCached() {
        String commandResult;

        commandResult = shellGetMemoryInfo().replaceAll("\\s+", ",");

        String[] memoryInfo = commandResult.split(",");

        return Long.parseLong(memoryInfo[6]);
    }

    protected static String shellGetMemoryInfo() {
        return ShellExecuter.Executer("free | grep Mem:");
    }

    public static String getNetworkTypeValue(NetworkClass networktype) {

        if (networktype.equals(NetworkClass.NETWORK_CLASS_WIFI))
            return "WiFi";
        else if (networktype.equals(NetworkClass.NETWORK_CLASS_2G))
            return "2G";
        else if (networktype.equals(NetworkClass.NETWORK_CLASS_3G))
            return "3G";
        else if (networktype.equals(NetworkClass.NETWORK_CLASS_4G))
            return "4G";
        else if (networktype.equals(NetworkClass.NETWORK_CLASS_NOT_CONNECTED))
            return "Offline";

        return null;

    }

    public enum NetworkClass {
        NETWORK_CLASS_2G, NETWORK_CLASS_3G, NETWORK_CLASS_4G, NETWORK_CLASS_WIFI, NETWORK_CLASS_GPRS, NETWORK_CLASS_UNKNOWN, NETWORK_CLASS_NOT_CONNECTED
    }

    // public static long getAvailableMemory(Context context) {
    // MemoryInfo mi = new MemoryInfo();
    // ActivityManager activityManager = (ActivityManager)
    // context.getSystemService(Context.ACTIVITY_SERVICE);
    // activityManager.getMemoryInfo(mi);
    // long availableMegs = mi.availMem / 1048576L;
    //
    // //Percentage can be calculated for API 16+
    // long percentAvail = mi.availMem / mi.totalMem;
    //
    // return percentAvail;
    // }
}