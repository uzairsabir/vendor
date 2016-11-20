package com.initializer.engine;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class EngineController extends Activity {

    private static EngineController engineController;

    public EngineController(Activity activity) {
    }

    public static EngineController getInstance(Activity activity) {
        if (engineController == null)
            engineController = new EngineController(activity);

        return engineController;
    }

}
