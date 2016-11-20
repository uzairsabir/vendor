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
package org.vanguardmatrix.engine.android.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;

import org.vanguardmatrix.engine.android.Application;
import org.vanguardmatrix.engine.android.data.LogManager;
import org.vanguardmatrix.engine.android.data.SettingsManager;
import org.vanguardmatrix.engine.android.data.notification.NotificationManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Basic service to work in background.
 *
 * @author alexander.ivanov
 */
public class XabberService extends Service {

    public static Application appTempInstance;
    private static XabberService instance;
    private Method startForeground;
    private Method stopForeground;
    private int phoneContactsCount;
    private long lastTimeSynced;
    private ContentObserver mObserver = new ContentObserver(new Handler()) {

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            final int currentCount = getContactCount();
            final long currentTime = System.currentTimeMillis();

            // if (getTimeDifference_fromLastChange(lastTimeSynced, currentTime)
            // > 1) {
            // Log.e("XabberService", "onChange : timeDiffere>1");
            // } else {
            // Log.e("XabberService", "onChange : timeDiffere<1");
            // }

            if (currentCount < phoneContactsCount) {
                Log.e("XabberService", "contact deleted ,,, phoneContactCount:"
                        + currentCount);

            } else if (currentCount == phoneContactsCount) {
                // Log.e("XabberService",
                // "contact updated ,,, phoneContactCount:"
                // + currentCount);
            } else {
                Log.e("XabberService", "contact added,,,,,, phoneContactCount:"
                        + currentCount);
                if (getTimeDifference_fromLastChange(lastTimeSynced,
                        currentTime) > 1) {
                    // if (!HomeScreenOld.pcs)
//                    new PhoneContactsManager.AddNewlyAddedPhoneContact_inDBTask()
//                            .execute();
                    lastTimeSynced = currentTime;
                }
            }
            phoneContactsCount = currentCount;
        }

    };

    public static XabberService getInstance() {
        return instance;
    }

    // public void changeForeground() {
    // if (Application.getInstance().isInitialized()) {
    // // startForegroundWrapper(NotificationManager.getInstance()
    // // .getPersistentNotification());
    // } else
    // stopForegroundWrapper();
    // }

    public static Intent createIntent(Context context) {
        return new Intent(context, XabberService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LogManager.i(this, "onCreate");

        // Try to get methods supported in API Level 5+
        try {
            startForeground = getClass().getMethod("startForeground",
                    new Class[]{int.class, Notification.class});
            stopForeground = getClass().getMethod("stopForeground",
                    new Class[]{boolean.class});
        } catch (NoSuchMethodException e) {
            startForeground = stopForeground = null;
        }

        try {
            phoneContactsCount = getContactCount();
            lastTimeSynced = System.currentTimeMillis();
            this.getContentResolver().registerContentObserver(
                    ContactsContract.Contacts.CONTENT_URI, true, mObserver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        changeForeground();
    }

    public void changeForeground() {
        Log.e("aw", "changingForeground");
        if (SettingsManager.eventsPersistent()
                && Application.getInstance().isInitialized()) {
            Log.e("aw",
                    "changingForeground event persistant or app initialized");
            startForegroundWrapper(NotificationManager.getInstance()
                    .getPersistentNotification());
        } else {
            Log.e("aw",
                    "changingForeground event not persisant or app not initialized");
            if (!Application.isAppStarted())
                stopForegroundWrapper();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Application.getInstance().onServiceStarted();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogManager.i(this, "onDestroy");
        Log.e("awXabber", "onDestroy");
        stopForegroundWrapper();
        this.getContentResolver().unregisterContentObserver(mObserver);
        Application.getInstance().onServiceDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * This is a wrapper around the new startForeground method, using the older
     * APIs if it is not available.
     */
    void startForegroundWrapper(Notification notification) {
        if (startForeground != null) {
            Object[] startForegroundArgs = new Object[]{
                    Integer.valueOf(NotificationManager.PERSISTENT_NOTIFICATION_ID),
                    notification};
            // Object[] startForegroundArgs = new Object[] { 1, null };
            try {
                startForeground.invoke(this, startForegroundArgs);
            } catch (InvocationTargetException e) {
                // Should not happen.
                LogManager.w(this, "Unable to invoke startForeground" + e);
            } catch (IllegalAccessException e) {
                // Should not happen.
                LogManager.w(this, "Unable to invoke startForeground" + e);
            }
        } else {
            stopForeground(true);
            try {
                Log.e("NNNNNNNN", " persistant notification from XabberService");
                ((android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
                        .notify(NotificationManager.PERSISTENT_NOTIFICATION_ID,
                                notification);
            } catch (SecurityException e) {
            }
        }
    }

    /**
     * This is a wrapper around the new stopForeground method, using the older
     * APIs if it is not available.
     */
    void stopForegroundWrapper() {

        if (stopForeground != null) {
            try {
                stopForeground.invoke(this, new Object[]{Boolean.TRUE});
                // We don't want to clear notification bar.
            } catch (InvocationTargetException e) {
                // Should not happen.
                LogManager.w(this, "Unable to invoke stopForeground" + e);
            } catch (IllegalAccessException e) {
                // Should not happen.
                LogManager.w(this, "Unable to invoke stopForeground" + e);
            }
        } else {
            stopForeground(false);
        }
    }

    private int getContactCount() {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(
                    ContactsContract.Contacts.CONTENT_URI, null, null, null,
                    null);
            if (cursor != null) {
                return cursor.getCount();
            } else {
                return 0;
            }
        } catch (Exception ignore) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return 0;
    }

    private int getTimeDifference_fromLastChange(long time1, long time2) {

        long diffInMs = time2 - time1;
        long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);

        Log.e("XabberService", " timeDifference in sec/60 =" + (diffInSec / 60));

        return (int) (diffInSec / 60);

    }

}
