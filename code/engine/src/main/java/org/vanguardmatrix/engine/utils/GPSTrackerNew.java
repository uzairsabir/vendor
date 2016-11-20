package org.vanguardmatrix.engine.utils;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.android.Application;
import org.vanguardmatrix.engine.android.data.ActivityManager;
import org.vanguardmatrix.engine.android.data.notification.OnZaairListener;

public class GPSTrackerNew extends Service implements LocationListener {

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    public static double latitude = 25.2; // latitude
    public static double longitude = 55.3; // longitude
    private final Context mContext;
    // public static double latitude = 0.0; // latitude
    // public static double longitude = 0.0; // longitude
    // Declaring a Location Manager
    protected LocationManager locationManager;
    // flag for GPS status
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;
    // flag for GPS status
    boolean canGetLocation = false;
    Location location; // location

//    boolean isGPSProviderEnable = false;

//    public staic isGPS_ProviderEnable()

    public GPSTrackerNew(Context context) {
        this.mContext = context;
        try {
            getLocation();
            Log.e("GPSTracker", " initialized");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isGPS_Enabled(Context context) {
        return ((LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE))
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public Location getLocation() {

        try {

            location = new Location("");
            location.setLatitude(org.vanguardmatrix.engine.utils.LocationManager.default_latitude);
            location.setLongitude(org.vanguardmatrix.engine.utils.LocationManager.default_longitude);

            // Acquire a reference to the system Location Manager
            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            if (location == null) {
                                // request location update!!
                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                            }
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER, 0, 0, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Stop using GPS listener Calling this function will stop using GPS in your
     * app
     */
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GPSTrackerNew.this);
        }
    }

    /**
     * Function to get latitude
     */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog On pressing Settings button will
     * lauch Settings Options
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog
                .setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);

                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getLocation();
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {

        try {

            latitude = location.getLatitude();
            longitude = location.getLongitude();

            Log.e("GPSTracker",
                    "onLocationChanged lati: " + location.getLatitude() + " , longi" + location.getLongitude());

            if (location != null) {
                AppPreferences.setCurrentBestLocation(location);
                org.vanguardmatrix.engine.utils.LocationManager.setLocation(location);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        for (OnZaairListener onZaairListener : Application.getInstance().getUIListeners(OnZaairListener.class))
            onZaairListener.onGpsStatusChange(false);
//        isGPSProviderEnable = false;
    }

    @Override
    public void onProviderEnabled(String provider) {
        for (OnZaairListener onZaairListener : Application.getInstance().getUIListeners(OnZaairListener.class))
            onZaairListener.onGpsStatusChange(true);

        ActivityManager.getInstance().onResume(ActivityManager.getInstance().getRunningActivity());
//        isGPSProviderEnable = true;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}
