package com.thetechsolutions.whodouvendor.Schedule.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.AppController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.BottomMenuController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.FragmentActivityController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.MethodGenerator;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.TitleBarController;
import com.thetechsolutions.whodouvendor.AppHelpers.WebService.AsynGetDataController;
import com.thetechsolutions.whodouvendor.R;
import com.thetechsolutions.whodouvendor.Schedule.adapters.ScheduleMainPagerAdapter;

import org.vanguardmatrix.engine.utils.PagerSlidingTabStrip;

/**
 * Created by Uzair on 7/12/2016.
 */
public class ScheduleMainActivity extends FragmentActivityController implements MethodGenerator {

    static Activity activity;
    public ViewPager pager;
    ScheduleMainPagerAdapter adapter;
    PagerSlidingTabStrip tapStrip;

    public static Intent createIntent(Activity _activity) {
        activity = _activity;
        return new Intent(activity, ScheduleMainActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_and_tab_strip);
        activity = this;


        BottomMenuController.getInstance(activity).setBottomMenu(activity);
        viewInitialize();

        TitleBarController.getInstance(activity).setTitleBar(activity, "Appointments", false, false, false);
        viewUpdate();
    }

    @Override
    public void viewInitialize() {
        pager = (ViewPager) findViewById(R.id.view_pager);
        tapStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs_strip);
        //pager.setOffscreenPageLimit(1);


    }

    @Override
    public void viewUpdate() {

        adapter = new ScheduleMainPagerAdapter(
                getSupportFragmentManager(), activity);
        pager.setAdapter(adapter);
        tapStrip.setViewPager(pager);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (AppController.getActivityResult(requestCode, resultCode, data)) {
            viewUpdate();
        }

    }
}
