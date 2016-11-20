package com.thetechsolutions.whodouvendor;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONArray;
import org.json.JSONObject;
import org.vanguardmatrix.engine.utils.MyLogs;

import java.util.ArrayList;

public class GcmIntentService extends IntentService {


    public static final String TYPE_SYNC_DATA = "sync_data";
    public static final String TYPE_TICKER = "type_nearby_loc";
    // public static final String TYPE_MESSAGE = "type_message";

    public static final String TYPE_CONSUMER_APPOINTMENT = "consumer_appoinment";
    public static final String TYPE_PAYMENT = "payment";
    public static final String TYPE_CONSUMER = "type_message";
    public static final String TYPE_MESSAGE = "p_message";

    public static int new_location_count = 0;
    String finalMessage = "";
    String targetId = "";
    String currTitle = "";
    String extra = "";
    String gcmMsg;
    JSONObject gcmMsgJson;
    PendingIntent contentIntent = null;
    NotificationCompat.Builder builder;
    String TAG = "GcmIntentService";
    private int currNotificationId = 1;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {

                Log.i(TAG, "Received: " + extras.toString());
                sendNotification(extras);

            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {

                Log.i(TAG, "Received: " + extras.toString());
                sendNotification(extras);

            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {

                for (int i = 0; i < 5; i++) {
                    Log.i(TAG,
                            "Working... " + (i + 1) + "/5 @ "
                                    + SystemClock.elapsedRealtime());
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                    }
                }
                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());

                sendNotification(extras);

                Log.i(TAG, "Received: " + extras.toString());
            }
        }

    }

    private void sendNotification(Bundle msg) {

        try {

            gcmMsg = msg.getString("message");
            gcmMsgJson = new JSONObject(gcmMsg);

            try {
               // gcmMsgJson.put(GcmNotificationsModel.N_TIME, System.currentTimeMillis());

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        updateGcmIntent(gcmMsgJson);

    }


    public void updateGcmIntent(JSONObject _gcmMsgJson) {
        try {


            MyLogs.printerror("GCMIntentService updateGcmIntent() : " + _gcmMsgJson.toString());
            ArrayList<String> data_list = new ArrayList<>();
            JSONArray jsonArray = new JSONArray();
            try {
                jsonArray = _gcmMsgJson.getJSONArray(TYPE_CONSUMER_APPOINTMENT);
                for (int i = 0; i < jsonArray.length(); i++) {


//                    Intent notificationIntent = CalendarCenterActivity.createIntent(
//                            Application.getInstance().getApplicationContext(), true, false);

//                    Intent notificationIntent = AppointmentMainActivity.createIntent(
//                            Application.getInstance().getApplicationContext());
//                    // DatabaseController.addAllRecords(Application.getInstance().getApplicationContext(), jsonArray.getJSONObject(i), AccountObject.ID, AppConstants.NOTIFICATION_TABLE_NAME);
//                    NotificationManager.notifyTestingSyncData(
//                            Application.getInstance().getApplicationContext(),
//                            data_list, notificationIntent, R.mipmap.ic_launcher, jsonArray, 1);
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
            try {
                jsonArray = _gcmMsgJson.getJSONArray(TYPE_PAYMENT);
                for (int i = 0; i < jsonArray.length(); i++) {

//
//                    Intent notificationIntent = PaymentMainActivity.createIntent(
//                            Application.getInstance().getApplicationContext());
//                    // DatabaseController.addAllRecords(Application.getInstance().getApplicationContext(), jsonArray.getJSONObject(i), AccountObject.ID, AppConstants.NOTIFICATION_TABLE_NAME);
//                    NotificationManager.notifyTestingSyncData(
//                            Application.getInstance().getApplicationContext(),
//                            data_list, notificationIntent, R.mipmap.ic_launcher, jsonArray, 2);
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
            try {
//                jsonArray = _gcmMsgJson.getJSONArray(TYPE_MESSAGE);
//                Intent notificationIntent = MessageCenterActivity.createIntent(
//                        Application.getInstance().getApplicationContext(), false, true);
//                NotificationManager.notifyTestingSyncData(
//                        Application.getInstance().getApplicationContext(),
//                        data_list, notificationIntent, R.mipmap.ic_launcher, jsonArray, 3);
//
//                try {
//                    RequestAppointmentFragment.refereshMessages();
//                } catch (Exception e) {
//
//                }

            } catch (Exception e) {
                e.printStackTrace();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
