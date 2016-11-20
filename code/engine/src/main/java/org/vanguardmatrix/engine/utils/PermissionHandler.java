package org.vanguardmatrix.engine.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uzair on 5/11/2016.
 */
public class PermissionHandler {

    final public static int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    public static boolean isStoragePermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                MyLogs.printinfo("Permission is granted");
                return true;
            } else {
                MyLogs.printinfo("Permission is revoked");

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 12);
                return false;
            }
        }
        MyLogs.printinfo("Permission is granted");

        return true;
    }

    public static boolean isAudioPermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(Manifest.permission.MODIFY_AUDIO_SETTINGS)
                    == PackageManager.PERMISSION_GRANTED) {

                MyLogs.printinfo("Permission is granted");
                return true;
            } else {
                MyLogs.printinfo("Permission is revoked");

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.MODIFY_AUDIO_SETTINGS}, 2);
                return false;
            }
        }
        MyLogs.printinfo("Permission is granted");

        return true;
    }

    public static boolean allPermissionsHandler(final Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            List<String> permissionsNeeded = new ArrayList<String>();

            final List<String> permissionsList = new ArrayList<String>();
            if (!addPermission(permissionsList, Manifest.permission.READ_CONTACTS, activity))
                permissionsNeeded.add("Read Contacts");
            if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE, activity))
                permissionsNeeded.add("Write External Storage");
            if (!addPermission(permissionsList, Manifest.permission.CALL_PHONE, activity))
                permissionsNeeded.add("Call Phone");
            if (!addPermission(permissionsList, Manifest.permission.WRITE_CALENDAR, activity))
                permissionsNeeded.add("Write Calendar");
            if (!addPermission(permissionsList, Manifest.permission.READ_CALENDAR, activity))
                permissionsNeeded.add("Read Calendar");

            if (permissionsList.size() > 0) {
                if (permissionsNeeded.size() > 0) {
                    // Need Rationale
                    String message = "You need to grant access to " + permissionsNeeded.get(0);
                    for (int i = 1; i < permissionsNeeded.size(); i++)
                        message = message + ", " + permissionsNeeded.get(i);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        activity.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                    }

                    return false;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activity.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                }
                return false;
            }


        }
        return true;


        //insertDummyContact();
    }

    private static boolean addPermission(List<String> permissionsList, String permission, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!activity.shouldShowRequestPermissionRationale(permission))
                    return false;
            }
        }
        return true;
    }

    private static void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener, Activity activity) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


}
