package com.thetechsolutions.whodouvendor.Signup.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.FragmentActivityController;
import com.thetechsolutions.whodouvendor.R;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.ListenerController;

import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.utils.GCMController;

/**
 * Created by Uzair on 12/6/2015.
 */
public class SplashActivity extends FragmentActivityController {
    Activity activity;

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, SplashActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = this;
        try {
            GCMController.CreateGCMID(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(3 * 1000);

                    if (!AppPreferences.getBoolean(AppPreferences.PREF_IS_INTRO_COMPLETED)) {
                        ListenerController.openIntroActivity(activity);
                    } else if (!AppPreferences.getBoolean(AppPreferences.PREF_IS_SIGNUP_DONE)) {
                        ListenerController.openSignupWithNumberActivity(activity);
                    } else if (!AppPreferences.getBoolean(AppPreferences.PREF_CONTACT_SYNC_DONE)) {
                        ListenerController.openInitProgressActivity(activity);
                    }
                    finish();

                } catch (Exception e) {

                }
            }
        };
        background.start();

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

    }
}
