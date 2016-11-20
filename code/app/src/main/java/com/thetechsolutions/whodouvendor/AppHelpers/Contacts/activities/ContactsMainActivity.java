package com.thetechsolutions.whodouvendor.AppHelpers.Contacts.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.thetechsolutions.whodouvendor.AppHelpers.Contacts.adapters.ContactsMainPagerAdapter;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.BottomMenuController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.FragmentActivityController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.MethodGenerator;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.TitleBarController;
import com.thetechsolutions.whodouvendor.R;

import org.vanguardmatrix.engine.utils.PagerSlidingTabStrip;

/**
 * Created by Uzair on 7/12/2016.
 */
public class ContactsMainActivity extends FragmentActivityController implements MethodGenerator {

    public static Activity activity;
    public ViewPager pager;
    ContactsMainPagerAdapter adapter;
    PagerSlidingTabStrip tapStrip;

    String title;
    public static int tab_pos;

    public static Intent createIntent(Activity _activity, int _tab_pos) {
        activity = _activity;
        tab_pos = _tab_pos;
        return new Intent(activity, ContactsMainActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_and_tab_strip);
        activity = this;

        if (tab_pos == 0) {

            title = "Add Providers";
        } else if (tab_pos == 1) {
            title = "Add Friends";

        }

        //MyLogs.printinfo("tab_pos  " + tab_pos);
        TitleBarController.getInstance(activity).setTitleBar(activity, title, true, false, false);
        BottomMenuController.getInstance(activity).setBottomMenu(activity);
        viewInitialize();
        viewUpdate();

    }

    @Override
    public void viewInitialize() {
        pager = (ViewPager) findViewById(R.id.view_pager);
        tapStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs_strip);
        tapStrip.setVisibility(View.GONE);


    }

    @Override
    public void viewUpdate() {

        adapter = new ContactsMainPagerAdapter(
                getSupportFragmentManager(), activity);
        pager.setAdapter(adapter);
        tapStrip.setViewPager(pager);

    }

}
