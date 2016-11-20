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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.vanguardmatrix.engine.android.data.LogManager;
import org.vanguardmatrix.engine.android.data.notification.NotificationManager;


/**
 * Activity to clear all notifications.
 *
 * @author alexander.ivanov
 */
public class ClearNotifications extends Activity {

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ClearNotifications.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION
                | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogManager.i(this, "onCreate");
        if (Application.getInstance().isInitialized())
            NotificationManager.getInstance().onClearNotifications();
        finish();
    }

}