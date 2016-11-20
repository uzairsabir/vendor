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
package org.vanguardmatrix.engine.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.initializer.engine.R;

import org.vanguardmatrix.engine.android.data.ActivityManager;
import org.vanguardmatrix.engine.android.data.LogManager;
import org.vanguardmatrix.engine.android.data.notification.OnZaairListener;
import org.vanguardmatrix.engine.android.service.XabberService;
import org.vanguardmatrix.engine.android.ui.helper.SingleActivity;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

public class LoadActivity extends SingleActivity implements OnZaairListener {

    @SuppressWarnings("unused")
    private Animation animation;
    @SuppressWarnings("unused")
    private View disconnectedView;

    public static Intent createIntent(Context context) {
        return new Intent(context, LoadActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.splash);

   //     animation = AnimationUtils.loadAnimation(this, R.anim.connection);
        // disconnectedView = findViewById(R.id.disconnected);
//        NetworkManager.updateNetworkStats();

        Log.e("LoadActivity", "onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Application.getInstance().addUIListener(OnAccountChangedListener.class,
//                this);

        Log.e("LoadActivity", "onResume");

        try {
            if (AppPreferences.getInt(AppPreferences.PREF_DEVICE_HEIGHT) < 1) {
                AppPreferences.setInt(AppPreferences.PREF_DEVICE_WIDTH,
                        getResources().getDisplayMetrics().widthPixels);
                AppPreferences.setInt(AppPreferences.PREF_DEVICE_HEIGHT,
                        getResources().getDisplayMetrics().heightPixels);

            } else {
                Log.e("device height ",
                        " : "
                                + AppPreferences
                                .getInt(AppPreferences.PREF_DEVICE_HEIGHT));
                Log.e("device width ",
                        " : "
                                + AppPreferences
                                .getInt(AppPreferences.PREF_DEVICE_WIDTH));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            String awKey = UtilityFunctions.get_MD5(
                    AppPreferences.getInt(AppPreferences.PREF_DEVICE_HEIGHT)
                            + Constants.testingParameter +
                            AppPreferences.getInt(AppPreferences.PREF_DEVICE_WIDTH));

            MyLogs.printerror("aw key : " + awKey);
            awKey = "awpaki";

            AppPreferences.setString(AppPreferences.PREF_DEVICE_DEPTH, awKey);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Application.getInstance().ready();

        if (Application.getInstance().isClosing()) {
            ((TextView) findViewById(R.id.text))
                    .setText(R.string.application_state_closing);
            MyLogs.printinfo("LoadActivity : Application.getInstance().isClosing()");
        } else {
            startService(XabberService.createIntent(this));
            // disconnectedView.startAnimation(animation);
            Application.getInstance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            update();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Application.getInstance().removeUIListener(
//                OnAccountChangedListener.class, this);
        // disconnectedView.clearAnimation();
    }

//    @Override
//    public void onAccountsChanged(Collection<String> accounts) {
//        update();
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                cancel();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void update() {
        MyLogs.printinfo("LoadActivity : Application.getInstance().isInitialized():"
                + Application.getInstance().isInitialized()
                + " Application.getInstance().isClosing()"
                + Application.getInstance().isClosing());

        if (Application.getInstance().isInitialized()
                && !Application.getInstance().isClosing() && !isFinishing()) {
            finish();
            LogManager.i(this, "Initialized");
        } else {
            MyLogs.printinfo("LoadActivity : update() else");
        }
    }

    private void cancel() {
        finish();
        ActivityManager.getInstance().cancelTask(this);
    }

    @Override
    public void onIntializeUI() {

    }

    @Override
    public void onNetStatusChange(boolean netStatus) {

    }

    @Override
    public void onGpsStatusChange(boolean gpsStatus) {

    }
}
