package org.vanguardmatrix.engine.socials.FacebookManager;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.plus.PlusShare;
import com.initializer.engine.R;

import org.vanguardmatrix.engine.android.Constants;
import org.vanguardmatrix.engine.socials.FacebookManager.FacebookShareActivity;
import org.vanguardmatrix.engine.utils.ImageLoadingHandler;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.util.ArrayList;
import java.util.List;

//import com.facebook.CallbackManager;
//import com.facebook.share.widget.ShareDialog;

//import com.facebook.

public class GeneralShare {


    public static String iamkhi_text = "\nHi, I am sharing this news using #IAmKarachiApp. Check out the application @ "
            + Constants.getApplicationGooglePlayLinkShortened();
//    CallbackManager callbackManager;
//    ShareDialog shareDialog;

    public static String getAppNotExistMessage(String appName) {
        // return "Check " + appName + " application is Active";
        return "Couldn't shared with " + appName;
    }

    private static boolean appInstalledOrNot(Activity _actiivty, String uri) {
        PackageManager pm = _actiivty.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static boolean shareWithGooglePlus(Activity _activity, String contentURI, String title,
                                              String description, String imageURI) {
        try {
            Intent shareIntent = null;
            if (imageURI.length() > 0) {
                shareIntent = new PlusShare.Builder(_activity)
                        .setType("image/*")
                        .setText(title + "\n" + description + iamkhi_text)
//                        .setContentUrl( Uri.fromFile(ImageLoadingHandler.getCacheFile(imageURI))).getIntent();
                        .setContentUrl(Uri.parse(contentURI)).getIntent();
//                        .setContentUrl(Uri.parse(imageURI)).getIntent();
//                        .setContentUrl(Uri.fromFile(ImageLoadingHandler.getCacheFile(imageURI))).getIntent();
            } else {
                shareIntent = new PlusShare.Builder(_activity)
                        .setType("text/plain")
                        .setText(title + "\n\n" + description + iamkhi_text)
                        .getIntent();
            }

            if (shareIntent == null) {
                UtilityFunctions.showToast_onCenter(
                        getAppNotExistMessage("Google+"), _activity);
                return true;
            }

            _activity.startActivity(shareIntent);

            return true;

        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean shareWtihFacebookPublishing(Activity _activity, String contentURI, String title,
                                                      String description, String imageURI) {
        return false;
    }

    public static boolean shareWithFacebookNew(Activity _activity, String contentURI, String title,
                                               String description, String imageURI) {
        // uri to the image you want to share
        Uri path = Uri.fromFile(ImageLoadingHandler.getCacheFile(imageURI));
        Resources resources = _activity.getResources();

        // create email intent first to remove bluetooth + others options
        Intent emailIntent = new Intent();
        emailIntent.setAction(Intent.ACTION_SEND);
        // Native email client doesn't currently support HTML, but it doesn't hurt to try in case they fix it
        emailIntent.putExtra(Intent.EXTRA_TEXT, "blah");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "blah");
        emailIntent.setType("image/jpeg");
        // Create the chooser based on the email intent
        Intent openInChooser = Intent.createChooser(emailIntent, "blah");

        // Check for other packages that open our mime type
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("image/jpeg");

        PackageManager pm = _activity.getPackageManager();
        List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
        for (int i = 0; i < resInfo.size(); i++) {
            // Extract the label, append it, and repackage it in a LabeledIntent
            ResolveInfo ri = resInfo.get(i);
            String packageName = ri.activityInfo.packageName;
            if (packageName.contains("android.email")) {
                emailIntent.setPackage(packageName);
                // select packages to share here V
            } else if (packageName.contains("com.facebook.katana")) {

                // Check for other packages that open our mime type
                Intent fbShareIntent = new Intent(_activity, FacebookShareActivity.class);
                fbShareIntent.setType("image/jpeg");
                fbShareIntent.putExtra(Intent.EXTRA_STREAM, path);
                fbShareIntent.putExtra(Intent.EXTRA_TEXT, contentURI + description);
                fbShareIntent.putExtra(Intent.EXTRA_SUBJECT, title);

                _activity.startActivity(fbShareIntent);

                return true;
            } else if (packageName.contains("com.instagram.android") || packageName.contains("com.twitter.android") || packageName.contains("com.whatsapp") || packageName.contains("mms") || packageName.contains("android.gm"))
                  /* Removed facebook Intent here can add it back in if needed */
            //|| packageName.contains("com.facebook.katana"))
            {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, path);
                intent.setType("image/jpeg");

                if (packageName.contains("twitter")) {
                    intent.putExtra(Intent.EXTRA_TEXT, "blah");
                } else if (packageName.contains("mms")) {
                    intent.putExtra(Intent.EXTRA_TEXT, "blah");
                }
                    /* can add back in the original facebook intent here
                    else if(packageName.contains("facebook"))
                    {
                        // Facebook IGNORES our text. They say "These fields are intended for users to express themselves.
                        // Pre-filling these fields erodes the authenticity of the user voice."
                        // Putting it here anyway as they might change their minds
                        intent.putExtra(Intent.EXTRA_TEXT, "picture caption #test");
                    }*/
                else if (packageName.contains("android.gm")) {
                    intent.putExtra(Intent.EXTRA_TEXT, "blah");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "blah");
                }

                intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
            }

        }


        // Get our custom intent put here as we only want your app to get it not others
        Intent customIntent = new Intent("com.vanguardmatrix.iamkarachi.intent.action.SEND");
        customIntent.setType("image/jpeg");
        customIntent.setAction("com.vanguardmatrix.iamkarachi.intent.action.SEND");

        resInfo = pm.queryIntentActivities(customIntent, 0);
        for (int i = 0; i < resInfo.size(); i++) {
            // Extract the label, append it, and repackage it in a LabeledIntent
            ResolveInfo ri = resInfo.get(i);
            String packageName = ri.activityInfo.packageName;
            if (packageName.contains("com.weeworld.slapsticker.app")) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, path);
                intent.setType("image/jpeg");
                if (packageName.contains("com.weeworld.slapsticker.app")) {
                    // My custom facebook intent to do something very simple!
                    intent.putExtra(Intent.EXTRA_TEXT, "caption #testhashtag");
                }
                intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
            }
        }

        // convert the list of intents(intentList) to array and add as extra intents
        LabeledIntent[] extraIntents = intentList.toArray(new LabeledIntent[intentList.size()]);
        openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);

        _activity.startActivity(openInChooser);

        return true;
    }

    public static boolean shareWithFacebook(Activity _activity, String contentURI, String title,
                                            String description, String imageURI) {

        try {

            // Intent sendIntent = new Intent();
            // sendIntent.setAction(Intent.ACTION_SEND);
            // sendIntent.putExtra(Intent.EXTRA_TEXT,
            // "<---YOUR TEXT HERE--->.");
            // sendIntent.setType("text/plain");
            // sendIntent.setPackage("com.facebook.orca");
            // try {
            // startActivity(sendIntent);
            // } catch (android.content.ActivityNotFoundException ex) {
            // ToastHelper.MakeShortText("Please Install Facebook Messenger");
            // }

            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            if (imageURI.length() > 0)
                shareIntent.setType("image/jpeg");
            else
                shareIntent.setType("text/plain");
            shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
            shareIntent
                    .putExtra(android.content.Intent.EXTRA_TEXT, description);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(ImageLoadingHandler.getCacheFile(imageURI)));

            PackageManager pm = _activity.getPackageManager();
            List<ResolveInfo> activityList = pm.queryIntentActivities(
                    shareIntent, 0);
            for (final ResolveInfo app : activityList) {
                if ((app.activityInfo.name).contains("com.facebook")) {
                    final ActivityInfo activity = app.activityInfo;
                    final ComponentName name = new ComponentName(
                            activity.applicationInfo.packageName, activity.name);
                    shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    shareIntent.setComponent(name);
                    _activity.startActivity(shareIntent);
                    return true;
                }
            }

            UtilityFunctions.showToast_onCenter(
                    getAppNotExistMessage("Facebook"), _activity);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean shareWithTwitterPublishing(Activity _activity, String contentURI, String title,
                                                     String description, String imageURI) {
        List<Intent> targetShareIntents = new ArrayList<Intent>();
        if (appInstalledOrNot(_activity, "com.twitter.android")) {
            System.out.println("App installed");
            PackageManager packageManager = _activity.getPackageManager();
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("image/*");
            List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(sendIntent, 0);
            for (int j = 0; j < resolveInfoList.size(); j++) {
                ResolveInfo resInfo = resolveInfoList.get(j);
                String packageName = resInfo.activityInfo.packageName;
                Log.i("Package Name", packageName);
                if (packageName.contains("com.twitter.android")) {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("image/*");//Set MIME Type
                    intent.putExtra(Intent.EXTRA_TITLE, title + "\n\n" + contentURI);
                    intent.putExtra(Intent.EXTRA_TEXT, description);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Post Image");
                    Uri screenshotUri = Uri.parse(ImageLoadingHandler.getCacheFilePath(imageURI));
//                    Uri screenshotUri = Uri.parse(imageUri);
                    intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);// Pur Image to intent

                    intent.setPackage(packageName);
                    targetShareIntents.add(intent);
                }

                if (!targetShareIntents.isEmpty()) {
                    System.out.println("Have Intent");
                    Intent chooserIntent = Intent.createChooser(targetShareIntents.remove(0), "Choose app to share");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{
                    }));
                    _activity.startActivity(chooserIntent);
                } else {
                    System.out.println("Do not Have Intent");
                    return false;
                }
            }
        } else {
//            UtilityFunctions.showToast_onCenter(
//                    getAppNotExistMessage("Twitter"), _activity);
            return false;
        }

        return true;
    }

    public static boolean shareWithTwitterNew(Activity _activity, String contentURI, String title,
                                              String description, String imageURI) {
        try {

//            File file =  ImageLoadingHandler.getCacheFile(imageURL);
//            Uri uri = Uri.fromFile(file);
            //Uri uri = Uri.parse("android.resource://com.gobaby.app/drawable/back");
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("/*");
            intent.setClassName("com.twitter.android", "com.twitter.android.PostActivity");
            intent.putExtra(Intent.EXTRA_SUBJECT, title);
            intent.putExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME, _activity.getResources().getString(R.string.application_name));
            intent.putExtra(Intent.EXTRA_TEXT, description);
            intent.putExtra(Intent.EXTRA_ORIGINATING_URI, contentURI);
            intent.putExtra(Intent.EXTRA_TEXT, description);
            if (!imageURI.equals(""))
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(ImageLoadingHandler.getCacheFile(imageURI)));
            _activity.startActivity(intent);

        } catch (final ActivityNotFoundException e) {
            Toast.makeText(_activity, "You don't seem to have twitter installed on this device", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean shareWithTwitter(Activity _activity, String contentURI, String title,
                                           String description, String imageURI) {
        try {

            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            if (imageURI.length() > 0)
                shareIntent.setType("image/*");
            else
                shareIntent.setType("text/plain");
            shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, description
                    + "\n" + imageURI + iamkhi_text);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(ImageLoadingHandler.getCacheFile(imageURI)));

            PackageManager pm = _activity.getPackageManager();
            List<ResolveInfo> activityList = pm.queryIntentActivities(
                    shareIntent, 0);
            for (final ResolveInfo app : activityList) {
                if ("com.twitter.android.PostActivity"
                        .equals(app.activityInfo.name)) {
                    final ActivityInfo activity = app.activityInfo;
                    final ComponentName name = new ComponentName(
                            activity.applicationInfo.packageName, activity.name);
                    shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    shareIntent.setComponent(name);
                    _activity.startActivity(shareIntent);
                    return true;
                }
            }

            UtilityFunctions.showToast_onCenter(
                    getAppNotExistMessage("Twitter"), _activity);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean shareWithWhatsApp(Activity _activity, String contentURI, String title,
                                            String description, String imageURI) {
        try {
            // image/*
            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            if (imageURI.length() > 0)
                shareIntent.setType("image/*");
            else
                shareIntent.setType("text/plain");

            shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, title
                    + "\n\n" + description + "\n" + contentURI + iamkhi_text);

            if (imageURI.length() > 0)
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(ImageLoadingHandler.getCacheFile(imageURI)));

            PackageManager pm = _activity.getPackageManager();
            List<ResolveInfo> activityList = pm.queryIntentActivities(
                    shareIntent, 0);

            for (final ResolveInfo app : activityList) {
                if ((app.activityInfo.name).contains("com.whatsapp")) {
                    final ActivityInfo activity = app.activityInfo;
                    final ComponentName name = new ComponentName(
                            activity.applicationInfo.packageName, activity.name);
                    shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    shareIntent.setComponent(name);
                    _activity.startActivity(shareIntent);
                    return true;
                }
            }

            UtilityFunctions.showToast_onCenter(
                    getAppNotExistMessage("WhatsApp"), _activity);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean shareWithGmail(Activity _activity, String contentURI, String title,
                                         String description, String imageURI) {
        try {
            List<Intent> targetShareIntents = new ArrayList<Intent>();
            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            if (imageURI.length() > 0) {
                shareIntent.setType("image/*");
            } else {
                shareIntent.setType("text/plain");
            }
            PackageManager packageManager = _activity.getPackageManager();
            List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(shareIntent, 0);
            for (int j = 0; j < resolveInfoList.size(); j++) {
                ResolveInfo resInfo = resolveInfoList.get(j);
                String packageName = resInfo.activityInfo.packageName;
                Log.i("Package Name", packageName);
                if (packageName.contains("android.gm")) {
                    try {
                        Intent targetShareIntent = new Intent();
                        targetShareIntent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                        targetShareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                        if (imageURI.length() > 0) {
                            targetShareIntent.setType("image/*");
                            targetShareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                                    title);
                            targetShareIntent
                                    .putExtra(
                                            android.content.Intent.EXTRA_TEXT,
                                            description
                                                    + "\n"
                                                    + contentURI
                                                    + iamkhi_text);
                            targetShareIntent.putExtra(Intent.EXTRA_STREAM,
                                    Uri.fromFile(ImageLoadingHandler.getCacheFile(imageURI)));
//                        new URI(imageURI));
//                        Uri.fromFile(ImageLoadingHandler.getCacheFile(imageURI)));
                        } else {
                            targetShareIntent.setType("text/plain");
                            targetShareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                                    title);
                            targetShareIntent
                                    .putExtra(
                                            android.content.Intent.EXTRA_TEXT,
                                            description
                                                    + "\n"
                                                    + contentURI
                                                    + iamkhi_text);
                        }
//                    final ActivityInfo activity = app.activityInfo;
//                    final ComponentName name = new ComponentName(
//                            activity.applicationInfo.packageName, activity.name);
                        targetShareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                        targetShareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                    targetShareIntent.setComponent(name);
                        targetShareIntent.setPackage(packageName);
                        targetShareIntents.add(targetShareIntent);

                        if (!targetShareIntents.isEmpty()) {
                            System.out.println("Have Intent");
                            Intent chooserIntent = Intent.createChooser(targetShareIntents.remove(0), "Choose app to share");
                            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]
                                    {
                                    }));
                            _activity.startActivity(chooserIntent);
                            return true;
                        } else {
                            System.out.println("Do not Have Intent");
                        }
                    } catch (ActivityNotFoundException e) {
                        return false;
                    }
                    return true;
                }
            }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
