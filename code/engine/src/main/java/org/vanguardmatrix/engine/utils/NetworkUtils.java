package org.vanguardmatrix.engine.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils extends BroadcastReceiver {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public static int getInternetConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }


    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtils.getInternetConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtils.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == NetworkUtils.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == NetworkUtils.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }

    @Override
    public void onReceive(Context arg0, Intent arg1) {


    }

}
