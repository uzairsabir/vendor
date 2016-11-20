package org.vanguardmatrix.engine.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.WifiManager;
import android.telephony.CellInfoGsm;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public abstract class NetworkManager {

    public static Boolean proceedToSettings = false;
    private static long Rx1 = 0, Rx2 = 0, Tx1 = 0, Tx2 = 0, time1 = 0,
            time2 = 0;
    private static double uploadingSpeed = 0.0, downloadingSpeed = 0.0;

    public static boolean checkInternet(Activity activity) {
        try {
            if (isConnected(activity)) {
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

    public static boolean isConnectedToNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static void redirectToNetworkSettings(Context context) {
        context.startActivity(new Intent(
                android.provider.Settings.ACTION_SETTINGS));
    }

    public static void askPermissionForSettingsMenu(final Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Add the buttons
        builder.setPositiveButton("Go To Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        redirectToNetworkSettings(context);
                    }
                });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder.setTitle("No Internet Connectivity!");
        builder.setMessage("The device is not connected to a network or the connection is dead. Please make sure the internet is available!");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Get the network info
     *
     * @param context
     * @return
     */
    public static NetworkInfo getNetworkInfo(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo();
        } catch (Exception e) {

        }
        return null;

    }

    /**
     * Check if there is any connectivity
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = NetworkManager.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    /**
     * Check if there is any connectivity to a Wifi network
     *
     * @param context
     * @return
     */
    public static boolean isConnectedWifi(Context context) {
        NetworkInfo info = NetworkManager.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Get WIFI Signal Strength
     *
     * @param context
     * @return
     */
    public static String getWifiSignalStrength(Context context) {
        return ""
                + ((WifiManager) context.getSystemService(Context.WIFI_SERVICE))
                .getConnectionInfo().getRssi();
    }

    /**
     * Check if there is any connectivity to a mobile network
     *
     * @param context
     * @return
     */
    public static boolean isConnectedMobile(Context context) {
        NetworkInfo info = NetworkManager.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * Get GSM Signal Strength
     *
     * @param context
     * @return
     */
    @SuppressLint("NewApi")
    public static String getGSMSignalStrength(Context context) {
        if (UtilityFunctions.getDeviceOSVersion() > 16) {
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            CellInfoGsm cellinfogsm = (CellInfoGsm) telephonyManager
                    .getAllCellInfo().get(0);
            CellSignalStrengthGsm cellSignalStrengthGsm = cellinfogsm
                    .getCellSignalStrength();
            return "" + cellSignalStrengthGsm.getDbm();
        } else {
            return getMobileNetworkType(context);
        }
    }

    /**
     * Get Mobile Network subtype, is case if no gsmSignal found
     *
     * @param context
     * @return
     */

    public static int getNetworkInfoSubType(Context context) {
        return (NetworkManager.getNetworkInfo(context)).getSubtype();
    }

    public static String getMobileNetworkType(Context context) {
        int subType = getNetworkInfoSubType(context);

        if (UtilityFunctions.getDeviceOSVersion() < 8) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return "NETWORK_TYPE_1xRTT"; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return "NETWORK_TYPE_CDMA"; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return "NETWORK_TYPE_EDGE"; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return "NETWORK_TYPE_EVDO_0"; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return "NETWORK_TYPE_EVDO_A"; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return "NETWORK_TYPE_GPRS"; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return "NETWORK_TYPE_HSDPA"; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return "NETWORK_TYPE_HSPA"; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return "NETWORK_TYPE_HSUPA"; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return "NETWORK_TYPE_UMTS"; // ~ 400-7000 kbps
                /*
                 * Above API level 7, make sure to set android:targetSdkVersion
				 * to appropriate level to use these
				 */
                default:
                    return "No Mobile Network Connection Found";
            }
        } else {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    return "NETWORK_TYPE_IDEN"; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    return "NETWORK_TYPE_EVDO_B"; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    return "NETWORK_TYPE_EHRPD"; // ~ 1-2 Mbps

                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    return "NETWORK_TYPE_HSPAP"; // ~ 10-20 Mbps

                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    return "NETWORK_TYPE_LTE"; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    return "NETWORK_TYPE_UNKNOWN";
                default:
                    return "No Mobile Network Connection Found";
            }
        }
    }

    public static String getConnectionSpeed(Context context) {
        String speed = "-1";
        if (isConnectedWifi(context))
            return getWifiSignalStrength(context);
        else if (isConnectedMobile(context))
            return getGSMSignalStrength(context);
        else
            return speed;
    }

    /**
     * Check if there is fast connectivity
     *
     * @param context
     * @return
     */

    public static boolean isConnectedFast(Context context) {
        NetworkInfo info = NetworkManager.getNetworkInfo(context);
        return (info != null && info.isConnected() && NetworkManager
                .isConnectionFast(info.getType(), info.getSubtype()));
    }

    /**
     * Check if the connection is fast
     *
     * @param type
     * @param subType
     * @return
     */
    public static boolean isConnectionFast(int type, int subType) {
        if (type == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            if (UtilityFunctions.getDeviceOSVersion() < 8) {
                switch (subType) {
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                        return false; // ~ 50-100 kbps
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                        return false; // ~ 14-64 kbps
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                        return false; // ~ 50-100 kbps
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        return true; // ~ 400-1000 kbps
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        return true; // ~ 600-1400 kbps
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                        return false; // ~ 100 kbps
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                        return true; // ~ 2-14 Mbps
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                        return true; // ~ 700-1700 kbps
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                        return true; // ~ 1-23 Mbps
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                        return true; // ~ 400-7000 kbps
                    /*
                     * Above API level 7, make sure to set
					 * android:targetSdkVersion to appropriate level to use
					 * these
					 */
                    default:
                        return false;
                }
            } else {
                switch (subType) {
                    case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                        return true; // ~ 1-2 Mbps
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                        return true; // ~ 5 Mbps
                    case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                        return true; // ~ 10-20 Mbps
                    case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                        return false; // ~25 kbps
                    case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                        return true; // ~ 10+ Mbps
                    // Unknown
                    case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    default:
                        return false;
                }
            }
        } else {
            return false;
        }
    }

    public static String getUploadingSpeedMbps() {
        return "" + (uploadingSpeed / (1024)) + "Mbps";
    }

    public static String getDownloadingSpeedMbps() {
        return "" + (downloadingSpeed / (1024)) + "Mbps";
    }

    public static String getUploadingSpeedKbps() {
        return String.format("%.2f", uploadingSpeed) + " Kbps";
    }

    public static String getDownloadingSpeedKbps() {
        return String.format("%.2f", downloadingSpeed) + " Kbps";
    }

    public static double getUploadingSpeedKbpsUnit() {
        return uploadingSpeed;
    }

    public static double getDownloadingSpeedKbpsUnit() {
        return downloadingSpeed;
    }

    public static double getDownloadingSpeedForCall() {
        return downloadingSpeed;
    }

    public static void updateNetworkStats() {

        time2 = System.currentTimeMillis();

        Log.e("aw", "NW_Stats started");

        try {
            Rx2 = TrafficStats.getTotalRxBytes();
            Tx2 = TrafficStats.getTotalTxBytes();

            long secDiff = TimeUnit.MILLISECONDS.toSeconds(time2 - time1);

            double Tbps = (Tx2 - Tx1) / secDiff;
            double Rbps = (Rx2 - Rx1) / secDiff;

            uploadingSpeed = Tbps;
            downloadingSpeed = Rbps;

            Log.e("aw", " secs diff : " + secDiff + " trs K-Bytes/sec : "
                    + Tbps + " rec K-Bytes/sec : " + Rbps
                    + " uploading Speed : " + getUploadingSpeedKbps()
                    + " downloading speed : " + getDownloadingSpeedKbps());

            if (time1 == 0) {
                Rx1 = Rx2;
                Tx1 = Tx2;
                time1 = time2;
            }
            // Rx1 = Rx2;
            // Tx1 = Tx2;
            // time1 = time2;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
