package org.vanguardmatrix.engine.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;

import com.initializer.engine.R;

import org.vanguardmatrix.engine.android.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdul Wahab on 6/3/2015.
 */


public class Fb_Twitter {
    private static Context context;
    private static Fb_Twitter instance;

    public Fb_Twitter() {
        this.instance = this;
    }

    public static Fb_Twitter instance() {
        if (instance == null) {
            instance = new Fb_Twitter();
        }
        return instance;
    }

    private static boolean appInstalledOrNot(String uri) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static void showMessage(String msg) {
//        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        UtilityFunctions.showToast_onCenter(msg, context);
    }

    public static boolean PostImageOnTwitter(Activity _activity, String contentURI, String title,
                                             String description, String imageURI) {
        context = _activity;
        System.out.println("Cick on Twitter");
        List<Intent> targetShareIntents = new ArrayList<Intent>();
        if (appInstalledOrNot("com.twitter.android")) {
            System.out.println("App installed");
            PackageManager packageManager = context.getPackageManager();
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            if (!imageURI.equals(""))
                sendIntent.setType("image/*");//Set MIME Type
            else
                sendIntent.setType("text/plain");//Set MIME Type
            List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(sendIntent, 0);
            for (int j = 0; j < resolveInfoList.size(); j++) {
                ResolveInfo resInfo = resolveInfoList.get(j);
                String packageName = resInfo.activityInfo.packageName;
                Log.i("Package Name", packageName);
                if (packageName.contains("com.twitter.android")) {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                    intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                    if (!imageURI.equals(""))
                        intent.setType("image/*");//Set MIME Type
                    else
                        intent.setType("text/plain");//Set MIME Type

                    String fullText = "#IAmKhiApp\n" + Constants.getApplicationGooglePlayLinkShortened()
                            + "\n" + contentURI + "\n" + title;
                    intent.putExtra(Intent.EXTRA_TITLE, title);
                    intent.putExtra(Intent.EXTRA_SUBJECT, title);
                    intent.putExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME, _activity.getResources().getString(R.string.application_name));
//                    intent.putExtra(Intent.EXTRA_TEXT, description);
                    if (fullText.length() > 139)
                        intent.putExtra(Intent.EXTRA_TEXT, fullText.substring(0, 139));
                    else
                        intent.putExtra(Intent.EXTRA_TEXT, fullText);
                    intent.putExtra(Intent.EXTRA_ORIGINATING_URI, contentURI);
                    if (!imageURI.equals("")) {
                        try {
                            Uri screenshotUri = Uri.parse(ImageLoadingHandler.getCacheFilePath(imageURI));
                            intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);// Pur Image to intent
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    intent.setPackage(packageName);
                    targetShareIntents.add(intent);
                }

                if (!targetShareIntents.isEmpty()) {
                    System.out.println("Have Intent");
                    Intent chooserIntent = Intent.createChooser(targetShareIntents.remove(0), "Choose app to share");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]
                            {
                            }));
                    context.startActivity(chooserIntent);
                    return true;
                } else {
                    System.out.println("Do not Have Intent");
                }
            }
            return false;
        } else
            return false;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void ShareTextOnTwitter(String msg) {
        List<Intent> targetShareIntents = new ArrayList<Intent>();
        if (appInstalledOrNot("com.twitter.android"))// Check android app is installed or not
        {
            PackageManager packageManager = context.getPackageManager();// Create instance of PackageManager
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
//Get List of all activities
            List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(sendIntent, 0);
            for (int j = 0; j < resolveInfoList.size(); j++) {
                ResolveInfo resInfo = resolveInfoList.get(j);
                String packageName = resInfo.activityInfo.packageName;
//Find twitter app from list
                if (packageName.contains("com.twitter.android")) {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));//Create Intent with twitter app package
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, msg);// Set message
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Text Sharing");//set subject
                    intent.setPackage(packageName);
                    targetShareIntents.add(intent);
                }

                if (!targetShareIntents.isEmpty()) {
                    Intent chooserIntent = Intent.createChooser(targetShareIntents.remove(0), "Choose app to share");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]
                            {
                            }));
                    context.startActivity(chooserIntent);
                } else {
                    System.out.println("Do not Have Intent");
                }
            }
        } else
            showMessage("App Not Installed");
    }

}



