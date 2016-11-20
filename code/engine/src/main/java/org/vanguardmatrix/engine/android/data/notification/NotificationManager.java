/**
 * Copyright (c) 2013, Redsolution LTD. All rights reserved.
 * <p/>
 * This file is part of Xabber project; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License, Version 3.
 * <p/>
 * Xabber is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License,
 * along with this program. If not, see http://www.gnu.org/licenses/.
 */
package org.vanguardmatrix.engine.android.data.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.initializer.engine.EngineController;
import com.initializer.engine.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.vanguardmatrix.engine.android.Application;
import org.vanguardmatrix.engine.android.ClearNotifications;
import org.vanguardmatrix.engine.android.data.LogManager;
import org.vanguardmatrix.engine.android.data.OnCloseListener;
import org.vanguardmatrix.engine.android.data.OnInitializedListener;
import org.vanguardmatrix.engine.android.data.OnLoadListener;
import org.vanguardmatrix.engine.android.data.SettingsManager;
import org.vanguardmatrix.engine.android.ui.AppFunctions;
import org.vanguardmatrix.engine.utils.MyLogs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manage notifications about message, subscription and authentication.
 *
 * @author alexander.ivanov
 */
public class NotificationManager implements OnInitializedListener, OnCloseListener, OnLoadListener, Runnable {

    public static final int PERSISTENT_NOTIFICATION_ID = 1;
    private static final int CHAT_NOTIFICATION_ID = 2;
    private static final int BASE_NOTIFICATION_PROVIDER_ID = 0x10;

    private static final long VIBRATION_DURATION = 500;
    private static final int MAX_NOTIFICATION_TEXT = 80;
    private final static NotificationManager instance;
    public static final int TYPE_APPOINTMENT = 1;

    static {
        instance = new NotificationManager();
        Application.getInstance().addManager(instance);
    }

    private final long startTime;
    private final Application application;
    private final android.app.NotificationManager notificationManager;
    private final Notification persistentNotification;
    private final PendingIntent clearNotifications;
    private final Handler handler;
    /**
     * Runnable to start vibration.
     */
    private final Runnable startVibro;
    /**
     * Runnable to force stop vibration.
     */
    private final Runnable stopVibro;
    /**
     * List of providers for notifications.
     */
    private final List<NotificationProvider<? extends NotificationItem>> providers;

    private NotificationManager() {
        this.application = Application.getInstance();
        notificationManager = (android.app.NotificationManager) application
                .getSystemService(Context.NOTIFICATION_SERVICE);
        persistentNotification = new Notification();
        if (getDeviceOSVersion() > 15)
            persistentNotification.priority = Notification.PRIORITY_MIN;
        persistentNotification.flags = Notification.FLAG_ONGOING_EVENT;
        handler = new Handler();
        providers = new ArrayList<NotificationProvider<? extends NotificationItem>>();
        startTime = System.currentTimeMillis();
        clearNotifications = PendingIntent.getActivity(application, 0,
                ClearNotifications.createIntent(application), 0);
        stopVibro = new Runnable() {
            @Override
            public void run() {
                handler.removeCallbacks(startVibro);
                handler.removeCallbacks(stopVibro);
                ((Vibrator) NotificationManager.this.application
                        .getSystemService(Context.VIBRATOR_SERVICE)).cancel();
            }
        };
        startVibro = new Runnable() {
            @Override
            public void run() {
                handler.removeCallbacks(startVibro);
                handler.removeCallbacks(stopVibro);
                ((Vibrator) NotificationManager.this.application
                        .getSystemService(Context.VIBRATOR_SERVICE)).cancel();
                ((Vibrator) NotificationManager.this.application
                        .getSystemService(Context.VIBRATOR_SERVICE))
                        .vibrate(VIBRATION_DURATION);
                handler.postDelayed(stopVibro, VIBRATION_DURATION);
            }
        };
    }

    public static NotificationManager getInstance() {
        return instance;
    }

    /**
     * @param text
     * @return Trimmed text.
     */
    private static String trimText(String text) {
        if (text.length() > MAX_NOTIFICATION_TEXT)
            return text.substring(0, MAX_NOTIFICATION_TEXT - 3) + "...";
        else
            return text;
    }

    public static int getDeviceOSVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    @Override
    public void onLoad() {
        //
    }

//    private void onLoaded(Collection<MessageNotification> messageNotifications) {
//        this.messageNotifications.addAll(messageNotifications);
//    }

    @Override
    public void onInitialized() {
//        application.addUIListener(OnAccountChangedListener.class, this);
//        updateMessageNotification(null);
    }

    /**
     * Register new provider for notifications.
     *
     * @param provider
     */
    public void registerNotificationProvider(
            NotificationProvider<? extends NotificationItem> provider) {
        providers.add(provider);
    }

    /**
     * Update notifications for specified provider.
     *
     * @param <T>
     * @param provider
     * @param notify   Ticker to be shown. Can be <code>null</code>.
     */
    public <T extends NotificationItem> void updateNotifications(
            NotificationProvider<T> provider, T notify) {
        int id = providers.indexOf(provider);
        if (id == -1)
            throw new IllegalStateException(
                    "registerNotificationProvider() must be called from onLoaded() method.");
        else
            id += BASE_NOTIFICATION_PROVIDER_ID;
        Iterator<? extends NotificationItem> iterator = provider
                .getNotifications().iterator();
        if (!iterator.hasNext()) {
            notificationManager.cancel(id);
        } else {
            NotificationItem top;
            String ticker;
            if (notify == null) {
                top = iterator.next();
                ticker = null;
            } else {
                top = notify;
                ticker = top.getTitle();
            }
            Intent intent = top.getIntent();
            Notification notification = new Notification(provider.getIcon(),
                    ticker, System.currentTimeMillis());
            if (!provider.canClearNotifications())
                notification.flags |= Notification.FLAG_NO_CLEAR;
//            notification.setLatestEventInfo(application, top.getTitle(), top
//                    .getText(), PendingIntent.getActivity(application, 0,
//                    intent, PendingIntent.FLAG_UPDATE_CURRENT));
            // if (ticker != null)
            // setNotificationDefaults(notification,
            // SettingsManager.eventsVibro(), provider.getSound(),
            // provider.getStreamType());
            if (ticker != null)
                setNotificationDefaults(notification,
                        SettingsManager.eventsVibro(),
                        AppFunctions.getDefaultNotificationSound(),
                        provider.getStreamType());
            notification.deleteIntent = clearNotifications;
            notify(id, notification);
        }
    }

    public void cancelNotificaiton(int id) {
        notificationManager.cancel(id);
    }

    /**
     * Sound, vibration and lightning flags.
     *
     * @param notification
     * @param streamType
     */
    private void setNotificationDefaults(Notification notification,
                                         boolean vibro, Uri sound, int streamType) {
        notification.audioStreamType = streamType;
        notification.defaults = 0;
        notification.sound = sound;
        if (vibro) {
            if (SettingsManager.eventsIgnoreSystemVibro())
                handler.post(startVibro);
            else
                notification.defaults |= Notification.DEFAULT_VIBRATE;
        }
        if (SettingsManager.eventsLightning()) {
            notification.defaults |= Notification.DEFAULT_LIGHTS;
            notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        }
    }

    public void notify(int id, Notification notification) {
        LogManager.i(this, "Notification: " + id + ", ticker: "
                + notification.tickerText + ", sound: " + notification.sound
                + ", vibro: "
                + (notification.defaults & Notification.DEFAULT_VIBRATE)
                + ", light: "
                + (notification.defaults & Notification.DEFAULT_LIGHTS));
        //------------------------Engine-------------///
//        try {
//            if (id == CHAT_NOTIFICATION_ID
//                    || id == GcmIntentService.NOTIFY_MUSIC_PLAYER
//                    || id == GcmIntentService.NOTIFY_DATA_SYNCING
//                    || id == GcmIntentService.NOTIFY_MESSAGE
//                    || id == GcmIntentService.NOTIFY_NEAR_LOCATION)
//                notificationManager.notify(id, notification);
//            else
//                Log.e("NNNNNN",
//                        "not notifying other notification except defined... event, rescue, voice, invite, goup, frined, pani");
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }
    }


    /**
     * Update notification according to ticker.
     *
     * @param notification
     */
    public void updateNotificationSound(Notification notification) {
        try {

            Log.e("NotificaitonManager", "should sound");
            setNotificationDefaults(notification, true,
                    AppFunctions.getDefaultNotificationSound(),
                    AudioManager.STREAM_NOTIFICATION);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Called when notifications was cleared by user.
     */
    public void onClearNotifications() {
        for (NotificationProvider<? extends NotificationItem> provider : providers)
            if (provider.canClearNotifications())
                provider.clearNotifications();
//        messageNotifications.clear();
    }

    @Override
    public void run() {
        handler.removeCallbacks(this);
    }

    public Notification getPersistentNotification() {
//        return persistentNotification;
        return null;
    }

    @Override
    public void onClose() {
        notificationManager.cancelAll();
    }


    public static void notifyTesting(Activity activity) {
        try {
            int icon = R.drawable.ic_launcher;
            long when = System.currentTimeMillis();
            Notification notification = new Notification(icon, "Custom Notification", when);

            android.app.NotificationManager mNotificationManager =
                    (android.app.NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);

//            RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_custom_music);
//            contentView.setImageViewResource(R.id.image, R.drawable.ic_launcher);
//            contentView.setTextViewText(R.id.title, "Custom notification");
//            contentView.setTextViewText(R.id.text, "This is a custom layout");
//            notification.contentView = contentView;

            Intent notificationIntent = new Intent(activity, EngineController.class);
            PendingIntent contentIntent = PendingIntent.getActivity(activity, 0, notificationIntent, 0);
            notification.contentIntent = contentIntent;

            notification.flags |= Notification.FLAG_INSISTENT; //Do not clear the notification
            notification.defaults |= Notification.DEFAULT_LIGHTS; // LED
            notification.defaults |= Notification.DEFAULT_VIBRATE; //Vibration
            notification.defaults |= Notification.DEFAULT_SOUND; // Sound

            mNotificationManager.notify(1, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void notifyTestingSyncData(Context appContext, ArrayList<String> data_list,
                                             Intent intent, int icon, JSONArray jsonArray, int code) {

        try {

            MyLogs.printinfo("intent notify");
            addSyncNewDataNotification(appContext, data_list, intent, icon, jsonArray, code);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearMusicPlayerNotification(Activity _activity) {
        android.app.NotificationManager mNotificationManager =
                (android.app.NotificationManager) _activity.getSystemService(Context.NOTIFICATION_SERVICE);
        // mNotificationManager.cancel(com.vanguardmatrix.zaair.GcmIntentService.NOTIFY_MUSIC_PLAYER);
    }


    public static void addSyncNewDataNotification(Context _activity, ArrayList<String> list,
                                                  Intent intent, int icon, JSONArray jsonArray, int code) {

        android.app.NotificationManager mNotificationManager =
                (android.app.NotificationManager) _activity.getSystemService(Context.NOTIFICATION_SERVICE);
//-----------------Engine---------------//
//        Intent notificationIntent = new Intent();// = AccountMainActivity.createIntent(_activity);//NotificationSyncData.createIntent(_activity, list);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        MyLogs.printinfo("intent clicked");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntents = PendingIntent.getActivity(_activity, code, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//        RemoteViews contentView = new RemoteViews(_activity.getPackageName(), R.layout.notification_custom_default);
//        contentView.setImageViewResource(R.id.image, R.drawable.ic_launcher);
//        contentView.setTextViewText(R.id.title, "New Ziaraat Data Available");
//        contentView.setTextViewText(R.id.text, "Browse more Ziaraat in Iran, Iraq, Syria \nUsing Zaair Guide App");
        String name = "", ticker = "", contenttitle = "", message = "";
        try {
            if (code == 1) {
                name = jsonArray.getJSONObject(0).getString("consumer_firstName") + " " +
                        jsonArray.getJSONObject(0).getString("consumer_lastName");
                ticker = "New Appointment received";
                contenttitle = "New Appointment";
                message = "\"You have an Appointment with " + name + "\n Tap to confirm";
            } else if (code == 3) {
                name = "";
                ticker = "New Message received";
                contenttitle = "New Message received";
                message = "New Message";
            } else {
                name = jsonArray.getJSONObject(0).getString("firstName") + " " +
                        jsonArray.getJSONObject(0).getString("lastName");
                ticker = "Payment received";
                contenttitle = "Payment received";
                message = "\"Payment received from " + name + "\n Tap to check detail";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(_activity.getApplicationContext());
        notifyBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(icon)
                .setTicker(ticker)
                .setContentTitle(contenttitle)
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntents)
                .setContentInfo("");

        mNotificationManager.notify(code, notifyBuilder.build());

        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) _activity.getApplicationContext().getSystemService(ns);
        nMgr.cancelNotificaiton(code);

    }

    public static void addMusicPlayerNotification(Activity _activity, String audioTitle, String audioDescrip, String audioMediaUrl, int audioTabPosition) {

        try {

//            MyLogs.printinfo("Adding custom Music Player notification -> imageURL : " + _lankmark.getImage_url() +
//                    "\nAudioTabPosition:" + audioTabPosition + "\nDescription:" + audioDescrip);
//            Uri temp = ImageLoadingHandler.getCacheFileUri(_lankmark.getImage_url());
//            MyLogs.printinfo("Media file uri from ImageLoader : " + temp.toString());
//
//            android.app.NotificationManager mNotificationManager =
//                    (android.app.NotificationManager) _activity.getSystemService(Context.NOTIFICATION_SERVICE);
//
//            Intent notificationIntent = RecitalsMain.createIntent(_activity, audioTabPosition, _lankmark, false, "", "", false);
            //          PendingIntent contentIntent = PendingIntent.getActivity(_activity, 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            try {
//                RemoteViews contentView = new RemoteViews(_activity.getPackageName(), R.layout.notification_custom_music);
//                contentView.setImageViewUri(R.id.image, temp);
//                contentView.setTextViewText(R.id.title, audioTitle);
//                contentView.setTextViewText(R.id.text, "Audio Playing... \n" + audioDescrip);
//
//                NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(_activity.getApplicationContext());
//                notifyBuilder.setDefaults(Notification.DEFAULT_ALL)
//                        .setWhen(System.currentTimeMillis())
//                        .setSmallIcon(R.drawable.ic_launcher)
//                        .setAutoCancel(false)
//                        .setTicker(_activity.getResources().getString(R.string.app_name))
//                        .setContentTitle(audioTitle)
//                        .setContentText("Ziarat Audio Playing : " + audioTitle + "\n" + audioDescrip)
//                        .setOnlyAlertOnce(true)
//                        .setDefaults(Notification.DEFAULT_LIGHTS)
//                        .setContentIntent(contentIntent)
//                        .setContentInfo("You are listening Audio Ziarat for " + audioTitle)
//                        .setContent(contentView)
//                        .setContentIntent(contentIntent);
//
//                mNotificationManager.notify(com.vanguardmatrix.zaair.GcmIntentService.NOTIFY_MUSIC_PLAYER, notifyBuilder.build());

            } catch (Exception e) {
                e.printStackTrace();
            }
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void addNotification(Activity _activity, int _currNotificationId, PendingIntent _contentIntent, int _resIcon, String _currTitle, String _message) {

        try {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    _activity)
                    .setSmallIcon(_resIcon)
                    .setContentTitle(_currTitle)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(_message))
                    .setContentText(_message).setAutoCancel(true);

            mBuilder.setContentIntent(_contentIntent);

            getInstance().updateNotificationSound(mBuilder.build());

            getInstance().notify(_currNotificationId, mBuilder.build());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

//
//    public static void notifyTesting(Activity context) {
//        try {
//            int icon = R.drawable.ic_launcher;
//            long when = System.currentTimeMillis();
//            Notification notification = new Notification(icon, "Custom Notification", when);
//
//            android.app.NotificationManager mNotificationManager =
//                    (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//            RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_custom_music);
//            contentView.setImageViewResource(R.id.image, R.drawable.ic_launcher);
//            contentView.setTextViewText(R.id.title, "Custom notification");
//            contentView.setTextViewText(R.id.text, "This is a custom layout");
//            notification.contentView = contentView;
//
//            Intent notificationIntent = new Intent(context, HomeScreen.class);
//            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
//            notification.contentIntent = contentIntent;
//
//            notification.flags |= Notification.FLAG_INSISTENT; //Do not clear the notification
//            notification.defaults |= Notification.DEFAULT_LIGHTS; // LED
//            notification.defaults |= Notification.DEFAULT_VIBRATE; //Vibration
//            notification.defaults |= Notification.DEFAULT_SOUND; // Sound
//
//            mNotificationManager.notify(1, notification);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void notifyTestingSyncData(Activity context, String jsonPush) {
//        try {
//
//            String serverPushNotiJson = jsonPush;
////            String serverPushNotiJson = "{\"message\":{\"sync_data\":{\"1\":{\"type\":\"landmarks\",\"extra\":\"\",\"updated_at\":\"2015-11-26 13:53:10\"},\"2\":{\"type\":\"personality\",\"extra\":\"\",\"updated_at\":\"2015-11-26 13:53:10\"},\"3\":{\"type\":\"imp_countries\",\"extra\":\"\",\"updated_at\":\"2015-11-26 13:53:10\"},\"4\":{\"type\":\"imp_cities\",\"extra\":\"\",\"updated_at\":\"2015-11-26 13:53:10\"},\"5\":{\"type\":\"personality_characteristics\",\"extra\":\"\",\"updated_at\":\"2015-11-26 13:53:10\"},\"6\":{\"type\":\"landmarks_personality_detail\",\"extra\":\"\",\"updated_at\":\"2015-11-26 13:53:10\"},\"7\":{\"type\":\"tags\",\"extra\":\"\",\"updated_at\":\"2015-11-26 13:53:10\"},\"8\":{\"type\":\"assets\",\"extra\":\"\",\"updated_at\":\"2015-11-26 13:53:10\"},\"9\":{\"type\":\"assets_detail\",\"extra\":\"\",\"updated_at\":\"2015-11-26 13:53:10\"},\"10\":{\"type\":\"day_detail\",\"extra\":\"\",\"updated_at\":\"2015-11-26 13:53:10\"},\"11\":{\"type\":\"landmarks_type\",\"extra\":\"\",\"updated_at\":\"2015-11-26 13:53:10\"}}},\"time\":1448545254870}";
//
//            JSONObject jsonObj = new JSONObject(serverPushNotiJson);
//            JSONObject data2Sync = jsonObj.getJSONObject("sync_data");
//
//            HashMap<String, JSONObject> data_list = new HashMap<>();
//
//            Iterator<String> iter = data2Sync.keys();
//            while (iter.hasNext()) {
//                String key = iter.next();
//                try {
//                    JSONObject value = data2Sync.getJSONObject(key);
//                    MyLogs.printerror("json parsing : " + key + " -> " + value.toString());
//
//                    data_list.put(key, value);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            addSyncNewDataNotification(context, data_list);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void clearMusicPlayerNotification(Activity _activity) {
//        android.app.NotificationManager mNotificationManager =
//                (android.app.NotificationManager) _activity.getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.cancel(com.vanguardmatrix.zaair.GcmIntentService.NOTIFY_MUSIC_PLAYER);
//    }
//
//    public static void addSyncNewDataNotification(Activity _activity, HashMap<String, JSONObject> list) {
//
//        android.app.NotificationManager mNotificationManager =
//                (android.app.NotificationManager) _activity.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Intent notificationIntent = NotificationSyncData.createIntent(_activity, list);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//        PendingIntent contentIntent = PendingIntent.getActivity(_activity, 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        RemoteViews contentView = new RemoteViews(_activity.getPackageName(), R.layout.notification_custom_default);
//        contentView.setImageViewResource(R.id.image, R.drawable.ic_launcher);
//        contentView.setTextViewText(R.id.title, "New Ziaraat Data Available");
//        contentView.setTextViewText(R.id.text, "Browse more Ziaraat in Iran, Iraq, Syria \nUsing Zaair Guide App");
//
//        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(_activity.getApplicationContext());
//        notifyBuilder.setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.drawable.ic_launcher)
//                .setTicker("Zaair Guide Updates")
//                .setContentTitle("New Ziaraat Data Available")
//                .setContentText("Browse more Ziaraat in Iran, Iraq, Syria \n" + "Using Zaair Guide App.")
//                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
//                .setContentIntent(contentIntent)
//                .setContent(contentView)
//                .setContentInfo("Browse more Ziaraat in Iran, Iraq, Syria \n" + "Using Zaair Guide App.");
//
////        Notification notification = new Notification(icon, "Zaair Guide Updates", when);
////        RemoteViews contentView = new RemoteViews(_activity.getPackageName(), R.layout.notification_custom_default);
////        contentView.setImageViewResource(R.id.image, R.drawable.ic_launcher);
////        contentView.setTextViewText(R.id.title, "New Ziaraat Data Available");
////        contentView.setTextViewText(R.id.text, "Browse more Ziaraat in Iran, Iraq, Syria \nUsing Zaair Guide App");
////        notification.contentView = contentView;
////        notification.contentIntent = contentIntent;
////
////        notification.flags |= Notification.FLAG_NO_CLEAR;
////        notification.flags |= Notification.FLAG_AUTO_CANCEL;
////        notification.defaults |= Notification.DEFAULT_LIGHTS; // LED
////        notification.defaults |= Notification.DEFAULT_VIBRATE; //Vibration
////        notification.defaults |= Notification.DEFAULT_SOUND; // Sound
//
////        if (Build.VERSION.SDK_INT < 16) {
////            mNotificationManager.notify(com.vanguardmatrix.zaair.GcmIntentService.NOTIFY_DATA_SYNCING, notification);
////            else
//        mNotificationManager.notify(com.vanguardmatrix.zaair.GcmIntentService.NOTIFY_DATA_SYNCING, notifyBuilder.build());
//
//    }
//
//    public static void addMusicPlayerNotification(Activity _activity, String audioTitle, String audioDescrip, String audioMediaUrl, LandMarks _lankmark, int audioTabPosition) {
//
//        try {
//
//            MyLogs.printinfo("Adding custom Music Player notification -> imageURL : " + _lankmark.getImage_url() +
//                    "\nAudioTabPosition:" + audioTabPosition + "\nDescription:" + audioDescrip);
//            Uri temp = ImageLoadingHandler.getCacheFileUri(_lankmark.getImage_url());
//            MyLogs.printinfo("Media file uri from ImageLoader : " + temp.toString());
//
//            int icon = R.drawable.ic_launcher;
//            long when = System.currentTimeMillis();
//
//            android.app.NotificationManager mNotificationManager =
//                    (android.app.NotificationManager) _activity.getSystemService(Context.NOTIFICATION_SERVICE);
//
//            Intent notificationIntent = RecitalsMain.createIntent(_activity, audioTabPosition, _lankmark, false, "", "", false);
//            PendingIntent contentIntent = PendingIntent.getActivity(_activity, 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//
////            if (Build.VERSION.SDK_INT < 16) {
////
////                RemoteViews contentView = new RemoteViews(_activity.getPackageName(), R.layout.notification_custom_music_api14);
////                contentView.setImageViewUri(R.id.image, temp);
////                contentView.setTextViewText(R.id.title, audioTitle);
////                contentView.setTextViewText(R.id.text, "Audio Playing... \n" + audioDescrip);
////
////                Notification notification = new Notification(icon, audioTitle, when);
////                notification.contentView = contentView;
////                notification.contentIntent = contentIntent;
////                notification.flags |= Notification.FLAG_NO_CLEAR;
//////                notification.flags |= Notification.FLAG_AUTO_CANCEL;//Do not clear the notification
////                notification.defaults |= Notification.DEFAULT_LIGHTS; // LED
////                notification.defaults |= Notification.DEFAULT_VIBRATE; //Vibration
////                notification.defaults |= Notification.DEFAULT_SOUND; // Sound
////
//////                Notification.Builder builder = new Notification.Builder(_activity)
//////                        .setSmallIcon(icon)
//////                        .setWhen(when)
//////                        .setAutoCancel(false)
//////                        .setTicker(_activity.getResources().getString(R.string.app_name))
//////                        .setContentTitle(audioTitle)
//////                        .setContentIntent(contentIntent)
//////                        .setContentText("Audio Playing... \n" + audioDescrip)
//////                        .setOnlyAlertOnce(true)
//////                        .setDefaults(Notification.DEFAULT_LIGHTS)
//////                        .setContent(contentView);
////
////
//////                Notification notification = builder.getNotification();
////                mNotificationManager.notify(com.vanguardmatrix.zaair.GcmIntentService.NOTIFY_MUSIC_PLAYER, notification);
////
////            } else {
//
//            try {
//                RemoteViews contentView = new RemoteViews(_activity.getPackageName(), R.layout.notification_custom_music);
//                contentView.setImageViewUri(R.id.image, temp);
//                contentView.setTextViewText(R.id.title, audioTitle);
//                contentView.setTextViewText(R.id.text, "Audio Playing... \n" + audioDescrip);
//
////                    Notification notification = new Notification(icon, audioTitle, when);
////
////                    notification.contentView = contentView;
////                    notification.contentIntent = contentIntent;
////                    notification.flags |= Notification.FLAG_NO_CLEAR;
//////                notification.flags |= Notification.FLAG_AUTO_CANCEL;//Do not clear the notification
////                    notification.defaults |= Notification.DEFAULT_LIGHTS; // LED
//////              notification.defaults |= Notification.DEFAULT_VIBRATE; //Vibration
//////              notification.defaults |= Notification.DEFAULT_SOUND; // Sound
//
//                NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(_activity.getApplicationContext());
//                notifyBuilder.setDefaults(Notification.DEFAULT_ALL)
//                        .setWhen(System.currentTimeMillis())
//                        .setSmallIcon(R.drawable.ic_launcher)
//                        .setAutoCancel(false)
//                        .setTicker(_activity.getResources().getString(R.string.app_name))
//                        .setContentTitle(audioTitle)
//                        .setContentIntent(contentIntent)
//                        .setContentText("Ziarat Audio Playing : " + audioTitle + "\n" + audioDescrip)
//                        .setOnlyAlertOnce(true)
//                        .setDefaults(Notification.DEFAULT_LIGHTS)
//                        .setContentIntent(contentIntent)
//                        .setContentInfo("You are listening Audio Ziarat for " + audioTitle)
//                        .setContent(contentView);
//
//                mNotificationManager.notify(com.vanguardmatrix.zaair.GcmIntentService.NOTIFY_MUSIC_PLAYER, notifyBuilder.build());
////                mNotificationManager.notify(com.vanguardmatrix.zaair.GcmIntentService.NOTIFY_MUSIC_PLAYER, notification);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
////            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public static void addNotification(Activity _activity, int _currNotificationId, PendingIntent _contentIntent, int _resIcon, String _currTitle, String _message) {
//
//        try {
//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
//                    _activity)
//                    .setSmallIcon(_resIcon)
//                    .setContentTitle(_currTitle)
//                    .setStyle(new NotificationCompat.BigTextStyle().bigText(_message))
//                    .setContentText(_message).setAutoCancel(true);
//
//            mBuilder.setContentIntent(_contentIntent);
//
//            getInstance().updateNotificationSound(mBuilder.build());
//
//            getInstance().notify(_currNotificationId, mBuilder.build());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
