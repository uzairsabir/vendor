package com.thetechsolutions.whodouvendor;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.vanguardmatrix.engine.utils.MyLogs;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

    // @Override
    // protected String getGCMIntentServiceClassName(Context context) {
    // return "com.vanguardmatrix.iamkarachi.GCMIntentService";
    // }
    @Override
    public void onReceive(Context context, Intent intent) {

        // Explicitly specify that GcmIntentService will handle the intent.
        ComponentName comp = new ComponentName(context.getPackageName(),
                GcmIntentService.class.getName());
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        String messageType = gcm.getMessageType(intent);
        // Filter messages based on message type. It is likely that GCM will be
        // extended in the future
        // with new message types, so just ignore message types you're not
        // interested in, or that you
        // don't recognize.

        if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
            // It's an error.
        } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                .equals(messageType)) {
            // Deleted messages on the server.
        } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                .equals(messageType)) {
//             It's a regular GCM message, do some work.
            try {

//                MyLogs.printerror("GCMBroadcastReceiver intent.getDataString() : " + intent.getDataString());
//                MyLogs.printerror("GCMBroadcastReceiver intent.getData() : " + intent.getData());
                MyLogs.printerror("GCMBroadcastReceiver intent.getStringExtra(\"message\") : " + intent.getStringExtra("message"));

//                UtilityFunctions.showToast_onCenter(intent.getDataString(), context);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
