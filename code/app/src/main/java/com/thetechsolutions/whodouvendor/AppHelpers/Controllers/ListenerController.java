package com.thetechsolutions.whodouvendor.AppHelpers.Controllers;

import android.app.Activity;

import com.thetechsolutions.whodouvendor.AppHelpers.Contacts.activities.ContactsMainActivity;
import com.thetechsolutions.whodouvendor.Chat.activities.ChatFromMainActivity;
import com.thetechsolutions.whodouvendor.Chat.activities.ChatMainActivity;
import com.thetechsolutions.whodouvendor.Home.activities.HomeCreateNewContactActivity;
import com.thetechsolutions.whodouvendor.Home.activities.HomeFriendProfileActivity;
import com.thetechsolutions.whodouvendor.Home.activities.HomeMainActivity;
import com.thetechsolutions.whodouvendor.Home.activities.HomeMainSearchActivity;
import com.thetechsolutions.whodouvendor.Pay.activities.PayDetailActivity;
import com.thetechsolutions.whodouvendor.Pay.activities.PayMainActivity;
import com.thetechsolutions.whodouvendor.R;
import com.thetechsolutions.whodouvendor.Schedule.activities.ScheduleDetailActivity;
import com.thetechsolutions.whodouvendor.Schedule.activities.ScheduleMainActivity;
import com.thetechsolutions.whodouvendor.Settings.activities.SettingsMainActivity;
import com.thetechsolutions.whodouvendor.Signup.activities.ContactSyncActivity;
import com.thetechsolutions.whodouvendor.Signup.activities.IntroMainActivity;
import com.thetechsolutions.whodouvendor.Signup.activities.SignupActivity;
import com.thetechsolutions.whodouvendor.Signup.activities.SplashActivity;

//import edu.sfsu.cs.orange.ocr.CaptureActivity;

/**
 * Created by Uzair on 12/8/2015.
 */
public class ListenerController {
    public static void openSplashActivity(Activity activity) {
        activity.startActivity(SplashActivity.createIntent(
                activity));
        activity.overridePendingTransition(R.anim.pull_in_from_left,
                R.anim.hold);
    }

    public static void openInitProgressActivity(Activity activity) {
        activity.startActivity(ContactSyncActivity.createIntent(
                activity));
        activity.overridePendingTransition(R.anim.pull_in_from_left,
                R.anim.hold);
    }

    public static void openHomeActivity(Activity activity, int pos) {
        activity.startActivity(HomeMainActivity.createIntent(
                activity, pos));

    }
    public static void openHomeSearchActivity(Activity activity,String keyword, int pos,int cat_id,int sub_cat_id,String sub_title) {
        activity.startActivity(HomeMainSearchActivity.createIntent(
                activity,keyword, pos,cat_id,sub_cat_id,sub_title));

    }

    public static void openChatActivity(Activity activity) {
        activity.startActivityForResult(ChatMainActivity.createIntent(
                activity), 1);


    }

    public static void openScheduleActivity(Activity activity) {
        activity.startActivity(ScheduleMainActivity.createIntent(
                activity));

    }

    public static void openPaymentActivity(Activity activity) {
        activity.startActivity(PayMainActivity.createIntent(
                activity));

    }

    public static void openPaymentDetail(Activity activity, int id, int tab_pos, String title,String providerName) {
        activity.startActivity(PayDetailActivity.createIntent(
                activity, id, tab_pos, title,providerName));

    }

    public static void openScheduleDetail(Activity activity, int id, int tab_pos, String title,String providerName) {
        activity.startActivityForResult(ScheduleDetailActivity.createIntent(
                activity, id, tab_pos, title,providerName),1);

    }

    public static void openContacts(Activity activity, int tab_pos) {
        activity.startActivityForResult(ContactsMainActivity.createIntent(
                activity, tab_pos), 1);

    }

    public static void openCreateNewContacts(Activity activity, int tab_pos, String provider_name) {
        activity.startActivityForResult(HomeCreateNewContactActivity.createIntent(
                activity, tab_pos, provider_name), 1);

    }

    public static void openSettingActivity(Activity activity) {
        activity.startActivity(SettingsMainActivity.createIntent(activity));

    }

    public static void openFriendProfileActivity(Activity activity, int tab_pos, String providerName) {
        activity.startActivityForResult(HomeFriendProfileActivity.createIntent(
                activity, tab_pos, providerName), 1);

    }

    public static void openSignupWithNumberActivity(Activity activity) {
        activity.startActivity(SignupActivity.createIntent(
                activity));
        activity.overridePendingTransition(R.anim.pull_in_from_left,
                R.anim.hold);
    }

    public static void openIntroActivity(Activity activity) {
        activity.startActivity(IntroMainActivity.createIntent(
                activity));
        activity.overridePendingTransition(R.anim.pull_in_from_left,
                R.anim.hold);
    }
    public static void openChatFromListActivity(Activity activity,String keyword, int pos,int cat_id,int sub_cat_id,String sub_title) {
        activity.startActivity(ChatFromMainActivity.createIntent(
                activity,keyword, pos,cat_id,sub_cat_id,sub_title));

    }

    public static void openOCRActivity(Activity activity) {
    //    Intent myIntent = new Intent(activity, CaptureActivity.class);
     //   activity.startActivity(myIntent);
//        activity.startActivity(CaptureActivity.createIntent(
//                activity));

    }


    public static void closeActivity(Activity activity) {
        activity.finish();
    }
}