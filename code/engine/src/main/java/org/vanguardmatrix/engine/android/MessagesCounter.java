package org.vanguardmatrix.engine.android;

import android.util.Log;

import java.util.ArrayList;


public class MessagesCounter {

    public static String smsCount = "sms-count";

    public static void addMessageCounter(String user) {
        AppPreferences.setInt(user + smsCount, getMessageCount(user) + 1);
    }

    public static int getMessageCount(String user) {

        int x = AppPreferences.getInt(user + smsCount);

        // if (x < 10)
        // x = Integer.parseInt("0" + x);

        // Log.e("MessagesCounter", "getting count for user : " + user
        // + " count : " + x);
        if (x > 99)
            return 99;
        return x;
        // return AppPreferences.getInt(user + smsCount);
    }

    public static void setMessageCounter2zero(String user) {
        Log.e("MessagesCounter", "setting count 0 for user : " + user);
        // AppPreferences.setInt(user + smsCount, 0);
        AppPreferences.unset(user + smsCount);
    }

    public static int getTotalCount(ArrayList<String> activeChatUsers) {
        int totalCount = 0;

        for (String user : activeChatUsers) {
            totalCount = totalCount + getMessageCount(user);
        }

        // Log.e("MessagesCounter", "returning total count : " + totalCount
        // + " for users : " + activeChatUsers.toString());

        return totalCount;
    }

}
