package com.initializer.engine;

import android.app.Activity;

/**
 * Created by Uzair on 12/4/2015.
 */
public class Engine {

    public static void initialize(Activity activity) {
        EngineController.getInstance(activity);

    }
}
