package com.thetechsolutions.whodouvendor.Settings.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.BottomMenuController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.FragmentActivityController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.MethodGenerator;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.TitleBarController;
import com.thetechsolutions.whodouvendor.R;
import com.thetechsolutions.whodouvendor.Settings.adapters.SettingsMainPagerAdapter;
import com.thetechsolutions.whodouvendor.Settings.fragments.SettingProfileFragment;

import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.PagerSlidingTabStrip;

import java.io.File;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * Created by Uzair on 7/12/2016.
 */
public class SettingsMainActivity extends FragmentActivityController implements MethodGenerator {

    static Activity activity;
    public ViewPager pager;
    SettingsMainPagerAdapter adapter;
    PagerSlidingTabStrip tapStrip;

    public static Intent createIntent(Activity _activity) {
        activity = _activity;
        return new Intent(activity, SettingsMainActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_and_tab_strip);
        activity = this;

        BottomMenuController.getInstance(activity).setBottomMenu(activity);
        viewInitialize();
        viewUpdate();
        TitleBarController.getInstance(activity).setTitleBar(activity, "Settings", false, false, false);

    }

    @Override
    public void viewInitialize() {
        pager = (ViewPager) findViewById(R.id.view_pager);
        tapStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs_strip);


    }

    @Override
    public void viewUpdate() {

        adapter = new SettingsMainPagerAdapter(
                getSupportFragmentManager(), activity);
        pager.setAdapter(adapter);
        tapStrip.setViewPager(pager);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, activity, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                //Handle the image
                try {
                    MyLogs.printinfo("imageFile " + imageFile.getPath());
                    SettingProfileFragment.fresco_view.setImageURI(Uri.fromFile(new File(imageFile.getPath())));
                } catch (Exception e) {

                }

                SettingProfileFragment.imageUrl = imageFile.getAbsolutePath();
            }
        });
    }


}
