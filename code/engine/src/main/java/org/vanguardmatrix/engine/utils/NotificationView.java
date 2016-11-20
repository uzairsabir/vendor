package org.vanguardmatrix.engine.utils;

import android.app.Activity;
import android.os.Bundle;

import com.initializer.engine.R;

;

/**
 * Created by Uzair on 12/8/2015.
 */
public class NotificationView extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_notification);
    }
}
