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
package org.vanguardmatrix.engine.android.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.model.LatLng;
import com.initializer.engine.EngineController;
import com.initializer.engine.R;
//import com.vanguardmatrix.zaair.Utils.ZaairController;

import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.android.Application;
import org.vanguardmatrix.engine.android.Constants;
import org.vanguardmatrix.engine.android.LoadActivity;
import org.vanguardmatrix.engine.android.data.SettingsManager.InterfaceTheme;
import org.vanguardmatrix.engine.android.data.connection.NetworkManager;
import org.vanguardmatrix.engine.android.data.notification.OnZaairListener;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.WeakHashMap;

//import com.vanguardmatrix.zaair.HomeScreenOld;

/**
 * Activity stack manager.
 *
 * @author alexander.ivanov
 */
public class ActivityManager implements OnUnloadListener, LocationSource.OnLocationChangedListener,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private static final String EXTRA_TASK_INDEX = "com.xabber.android.data.ActivityManager.EXTRA_TASK_INDEX";

    private static final boolean LOG = true;
    private final static ActivityManager instance;

    public static GoogleApiClient mGoogleApiClient;
    static HashMap<Constants.TrackerName, Tracker> mTrackers = new HashMap<Constants.TrackerName, Tracker>();

    static {
        instance = new ActivityManager();
        Application.getInstance().addManager(instance);

        NetworkManager.getInstance();
    }

    private final Application application;
    /**
     * List of launched activities.
     */
    private final ArrayList<Activity> activities;
    /**
     * Activity with index of it task.
     */
    private final WeakHashMap<Activity, Integer> taskIndexes;
    LocationRequest locationRequest;
    private Activity runningActivity = null;
    private Tracker tracker;
    /**
     * Next index of task.
     */
    private int nextTaskIndex;
    /**
     * Listener for errors.
     */
    private OnErrorListener onErrorListener;
    private Location mLastLocation;

    private ActivityManager() {
        this.application = Application.getInstance();
        activities = new ArrayList<Activity>();
        nextTaskIndex = 0;
        taskIndexes = new WeakHashMap<Activity, Integer>();
    }

    public static ActivityManager getInstance() {
        return instance;
    }

//    public synchronized static Tracker getTracker(Constants.TrackerName trackerId) {
//        if (!mTrackers.containsKey(trackerId)) {
//
//            GoogleAnalytics analytics = GoogleAnalytics.getInstance(getInstance().getRunningActivity());
//            analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
//            Tracker t = (trackerId == Constants.TrackerName.APP_TRACKER) ? analytics
//                    .newTracker(Constants.GOOGLE_ANALYTICS_TRACKER_ID)
//                    : (trackerId == Constants.TrackerName.GLOBAL_TRACKER) ? analytics
//                    .newTracker(R.xml.global_tracker) : analytics
//                    .newTracker(R.xml.ecommerce_tracker);
//            t.enableAdvertisingIdCollection(true);
//            mTrackers.put(trackerId, t);
//        }
//        return mTrackers.get(trackerId);
//    }

    public Activity getRunningActivity() {
        return this.runningActivity;
    }

    /**
     * Removes finished activities from stask.
     */
    private void rebuildStack() {
        Iterator<Activity> iterator = activities.iterator();
        while (iterator.hasNext())
            if (iterator.next().isFinishing())
                iterator.remove();
    }

    /**
     * Finish all activities in stack till the root HomeScreen.
     *
     * @param finishRoot also finish root HomeScreen.
     */
    public void clearStack(boolean finishRoot) {
        EngineController root = null;
        rebuildStack();
        for (Activity activity : activities) {
            if (!finishRoot && root == null && activity instanceof EngineController)
                root = (EngineController) activity;
            else
                activity.finish();
        }
        rebuildStack();
    }

    /**
     * @return Whether contact list is in the activity stack.
     */
    public boolean hasContactList(Context context) {
        rebuildStack();
        for (Activity activity : activities)
            if (activity instanceof EngineController)
                return true;
        return false;
    }

    /**
     * Apply theme settings.
     *
     * @param activity
     */
    private void applyTheme(Activity activity) {
        if (LOG)
            return;
//        TypedArray title = activity.getTheme().obtainStyledAttributes(
//                new int[]{android.R.attr.windowNoTitle,
//                        android.R.attr.windowIsFloating});
//        boolean noTitle = title.getBoolean(0, false);
//        boolean isFloating = title.getBoolean(1, false);
//        title.recycle();
//        if (isFloating)
//            return;
//        InterfaceTheme theme = SettingsManager.interfaceTheme();
//        if (theme == SettingsManager.InterfaceTheme.light)
//            activity.setTheme(noTitle ? R.style.Theme_Light_NoTitleBar
//                    : R.style.Theme_Light);
//        else if (theme == SettingsManager.InterfaceTheme.dark)
//            activity.setTheme(noTitle ? R.style.Theme_Dark_NoTitleBar
//                    : R.style.Theme_Dark);
    }

    /**
     * Push activity to stack.
     * <p/>
     * Must be called from {@link Activity#onCreate(Bundle)}.
     *
     * @param activity
     */
    public void onCreate(Activity activity) {
        if (LOG)
            LogManager.i(activity, "onCreate: " + activity.getIntent());
        applyTheme(activity);
        if (application.isClosing() && !(activity instanceof LoadActivity)) {
            activity.startActivity(LoadActivity.createIntent(activity));
            activity.finish();
        }
        runningActivity = activity;
       // ZaairController.onCreate(runningActivity);

//        try {
//            GoogleAnalytics.getInstance(activity).newTracker(
//                    Constants.GOOGLE_ANALYTICS_TRACKER_ID);
//            GoogleAnalytics.getInstance(activity).getLogger()
//                    .setLogLevel(Logger.LogLevel.VERBOSE);
//            tracker = getTracker(Constants.TrackerName.APP_TRACKER);
//            tracker.setScreenName(activity.getClass().getName());
//            tracker.send(new HitBuilders.AppViewBuilder().build());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        activities.add(activity);

//        if(activities.size()==1){
//            IntentFilter filter = new IntentFilter(ACTION);
//            activity.getApplication().registerReceiver(mVgmBaseReceiver, filter);
//        }

        rebuildStack();
        fetchTaskIndex(activity, activity.getIntent());

        if (activity instanceof EngineController) {
            try {

                locationRequest = createLocationRequest();
                buildGoogleApiClient(activity);
                mGoogleApiClient.connect();

//                getLocation();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    protected LocationRequest createLocationRequest() {
//        LocationRequest mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(120000);
//        mLocationRequest.setFastestInterval(30000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//        return mLocationRequest;

        return LocationRequest.create()
                .setFastestInterval(60000)   // in milliseconds
                .setInterval(180000)         // in milliseconds
                .setPriority(LocationRequest.PRIORITY_LOW_POWER);

    }

    public LatLng getLastKnownLatLng() {
        if (mLastLocation == null) {
            return new LatLng(0, 0);
        }
        return new LatLng(getLastKnownLocation().getLatitude(), getLastKnownLocation().getLongitude());
    }

    public Location getLastKnownLocation() {
        if (mLastLocation == null) {
            mLastLocation = AppPreferences.getCurrentBestLocation();
        }
        return mLastLocation;
    }

    protected synchronized void buildGoogleApiClient(Activity activity) {
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            AppPreferences.setCurrentBestLocation(mLastLocation);
            MyLogs.printinfo("ActivityMananger LocationServices.FusedLocationApi.getLastLocation " +
                    "\nlatitude: " + mLastLocation.getLatitude() + " longitude: " + mLastLocation.getLongitude());
        } else {
            MyLogs.printinfo("ActivityMananger LocationServices.FusedLocationApi.getLastLocation mLastLocation == null");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

//    String ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
//    VgmBaseReceiver mVgmBaseReceiver = new VgmBaseReceiver();

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            mLastLocation = location;
            MyLogs.printinfo("ActivityMananger onLocationChanged lati: " + location.getLatitude() + " longi : " + location.getLongitude());
            AppPreferences.setCurrentBestLocation(location);
        } else {
            MyLogs.printinfo("ActivityMananger onLocationChanged location==null");
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    /**
     * Pop activity from stack.
     * <p/>
     * Must be called from {@link Activity#onDestroy()}.
     *
     * @param activity
     */
    public void onDestroy(Activity activity) {
        if (LOG)
            LogManager.i(activity, "onDestroy");
        activities.remove(activity);

//        if(activities.size()==0){
//            activity.getApplication().unregisterReceiver(mVgmBaseReceiver);
//        }

        try {
            System.gc();
//            UtilityFunctions.unbindDrawables(activity
//                    .findViewById(R.id.root_view));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Pause activity.
     * <p/>
     * Must be called from {@link Activity#onPause()}.
     *
     * @param activity
     */
    public void onPause(Activity activity) {
        if (LOG)
            LogManager.i(activity, "onPause");
        if (onErrorListener != null)
            application
                    .removeUIListener(OnErrorListener.class, onErrorListener);
        onErrorListener = null;

        try {
//            application.removeUIListener(OnZaairListener.class, (OnZaairListener) activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Resume activity.
     * <p/>
     * Must be called from {@link Activity#onResume()}.
     *
     * @param activity
     */
    public void onResume(final Activity activity) {

        runningActivity = activity;
       // ZaairController.onResume(runningActivity);

        if (LOG)
            LogManager.i(activity, "onResume");
        if (!application.isInitialized() && !(activity instanceof LoadActivity)) {
            if (LOG)
                LogManager.i(this, "Wait for loading");
            activity.startActivity(LoadActivity.createIntent(activity));
        }
        if (onErrorListener != null)
            application
                    .removeUIListener(OnErrorListener.class, onErrorListener);
        onErrorListener = new OnErrorListener() {
            @Override
            public void onError(final int resourceId) {
                UtilityFunctions.showToast_onCenter(
                        activity.getString(resourceId), activity);
            }
        };
        application.addUIListener(OnErrorListener.class, onErrorListener);

        try {
            application.addUIListener(OnZaairListener.class, (OnZaairListener) activity);
        } catch (Exception e) {
//            e.printStackTrace();
        }

        try {
          //  ZaairController.handleZaairOfflineBar(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * New intent received.
     * <p/>
     * Must be called from {@link Activity#onNewIntent(Intent)}.
     *
     * @param activity
     * @param intent
     */
    public void onNewIntent(Activity activity, Intent intent) {
        if (LOG)
            LogManager.i(activity, "onNewIntent: " + intent);
    }

    /**
     * Result has been received.
     * <p/>
     * Must be called from {@link Activity#onActivityResult(int, int, Intent)}.
     *
     * @param activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(Activity activity, int requestCode,
                                 int resultCode, Intent data) {
        if (LOG)
            LogManager.i(activity, "onActivityResult: " + requestCode + ", "
                    + resultCode + ", " + data);
    }

    /**
     * Adds task index to the intent if specified for the source activity.
     * <p/>
     * Must be used when source activity starts new own activity from
     * {@link Activity#startActivity(Intent)} and
     * {@link Activity#startActivityForResult(Intent, int)}.
     *
     * @param source
     * @param intent
     */
    public void updateIntent(Activity source, Intent intent) {
        Integer index = taskIndexes.get(source);
        if (index == null)
            return;
        intent.putExtra(EXTRA_TASK_INDEX, index);
    }

    /**
     * Mark activity to be in separate activity stack.
     *
     * @param activity
     */
    public void startNewTask(Activity activity) {
        taskIndexes.put(activity, nextTaskIndex);
        LogManager.i(activity, "Start new task " + nextTaskIndex);
        nextTaskIndex += 1;
    }

    /**
     * Either move main task to back, either close all activities in subtask.
     *
     * @param activity
     */
    public void cancelTask(Activity activity) {
        Integer index = taskIndexes.get(activity);
        LogManager.i(activity, "Cancel task " + index);
        if (index == null) {
            activity.moveTaskToBack(true);
        } else {
            for (Entry<Activity, Integer> entry : taskIndexes.entrySet())
                if (entry.getValue() == index)
                    entry.getKey().finish();
        }
    }

    /**
     * Fetch task index from the intent and mark specified activity.
     *
     * @param activity
     * @param intent
     */
    private void fetchTaskIndex(Activity activity, Intent intent) {
        int index = intent.getIntExtra(EXTRA_TASK_INDEX, -1);
        if (index == -1)
            return;
        LogManager.i(activity, "Fetch task index " + index);
        taskIndexes.put(activity, index);
    }

    @Override
    public void onUnload() {
        clearStack(true);
    }

}
