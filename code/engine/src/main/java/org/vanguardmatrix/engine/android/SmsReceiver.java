package org.vanguardmatrix.engine.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = "Demo App";
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;

        Bundle pudsBundle = intent.getExtras();
        Object[] pdus = (Object[]) pudsBundle.get("pdus");
        SmsMessage msg = SmsMessage.createFromPdu((byte[]) pdus[0]);

        Log.e(TAG, "Message Recieved : " + msg.getMessageBody());
        Log.e("Sender Number", msg.getOriginatingAddress());
        // Log.e(TAG, "matching saved : ^" + Login.receivingMessage
        // + "^ with incoming : ^" + msg.getMessageBody() + "^");
        // if
        // (msg.getMessageBody().equalsIgnoreCase(Login.receivingMessage))
        // {
        // try {
        // Thread.sleep(3000);
        // SmsManager smsManager = SmsManager.getDefault();
        //
        // // if (settings.getReplyText().equalsIgnoreCase(""))
        //
        // // Log.e(TAG, "Sending : " + "http://maps.google.com/?q="
        // // + settings.getSendingMessage());
        // Log.e("app",
        // "Required Message Recieved : " + msg.getMessageBody());
        // // smsManager.sendTextMessage(
        // // msg.getOriginatingAddress(),
        // // null,
        // // "http://maps.google.com/?q="
        // // + settings.getSendingMessage(), null, null);
        //
        // Toast.makeText(context,
        // "Verifying almost dialog_done. Pleast Check inbox for code.",
        // Toast.LENGTH_SHORT).show();
        //
        // // else
        // // smsManager.sendTextMessage(
        // // messages.getOriginatingAddress(), null,
        // // settings.getReplyText(), null, null);
        // //
        //
        // // Login.registerNow();
        //
        // Log.e(TAG, "sms sent");
        //
        // // Toast.makeText(context, "SMS Sent!",
        // // Toast.LENGTH_LONG).show();
        //
        // } catch (Exception e) {
        // // Toast.makeText(context,"SMS faild, please try again later!",
        // // Toast.LENGTH_LONG).show();
        // Log.e(TAG, "sms sent fail");
        // e.printStackTrace();
        //
        // }
        // } else {
        // ;// do nothing with other msgs
        // Log.e(TAG, "Msg not matched : " + msg.getMessageBody());
        // }
    }
}