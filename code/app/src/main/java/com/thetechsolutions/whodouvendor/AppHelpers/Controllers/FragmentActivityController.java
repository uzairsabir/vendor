package com.thetechsolutions.whodouvendor.AppHelpers.Controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.thetechsolutions.whodouvendor.HomeScreenActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * Created by Uzair on 7/16/2016.
 */
public class FragmentActivityController extends FragmentActivity {


    private final String className;

    protected FragmentActivityController() {
        StackTraceElement[] trace = new Throwable().getStackTrace();
        this.className = trace[1].getClassName();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);
        EasyImage.configuration(this)
                .setImagesFolderName("tessdata") //images folder name, default is "EasyImage"
                //.saveInAppExternalFilesDir() //if you want to use root internal memory for storying images
                .saveInRootPicturesDirectory();
        AppController.insertDummpyContent();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            HomeScreenActivity.activity.finish();
        } catch (Exception e) {


        }


    }


}
