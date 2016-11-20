package com.thetechsolutions.whodouvendor.Home.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.thetechsolutions.whodouvendor.AppHelpers.Config.AppConstants;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.BottomMenuController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.FragmentActivityController;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.MethodGenerator;
import com.thetechsolutions.whodouvendor.AppHelpers.Controllers.TitleBarController;
import com.thetechsolutions.whodouvendor.Home.adapters.HomeMainPagerAdapter;
import com.thetechsolutions.whodouvendor.R;

import org.vanguardmatrix.engine.utils.PagerSlidingTabStrip;

/**
 * Created by Uzair on 7/12/2016.
 */
public class HomeMainSearchActivity extends FragmentActivityController implements MethodGenerator {

    public static Activity activity;
    public ViewPager pager;
    HomeMainPagerAdapter adapter;
    PagerSlidingTabStrip tapStrip;
    public static boolean isReferesh = false;
    public static int tab_pos, cat_id, sub_cat_id;
    public static String keyword;
    static String title,sub_title;
   // public static boolean isOpenChat=false;

    public static Intent createIntent(Activity _activity, String _keyword, int _tab_pos, int _cat_id, int _sub_cat_id,String _title) {
        activity = _activity;
        tab_pos = _tab_pos;
        cat_id = _cat_id;
        sub_cat_id = _sub_cat_id;
        keyword = _keyword;
        sub_title=_title;


        return new Intent(activity, HomeMainSearchActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_and_tab_strip);
        activity = this;
        viewInitialize();
        viewUpdate();

        if (tab_pos == 0) {
            title = AppConstants.CUSTOMERS;
        } else if (tab_pos == 1) {
            title = AppConstants.COLLEAGUES;
        } else {
            title = AppConstants.FRIENDS_PROVIDERS;
        }
        TitleBarController.getInstance(activity).setTitleBar(activity, sub_title+ " "+ title, true, false, false);


    }

    @Override
    public void viewInitialize() {
        pager = (ViewPager) findViewById(R.id.view_pager);
        tapStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs_strip);
        tapStrip.setVisibility(View.GONE);


    }


    @Override
    protected void onResume() {
        super.onResume();
        BottomMenuController.getInstance(activity).setBottomMenu(activity);


    }

    @Override
    public void viewUpdate() {

        adapter = new HomeMainPagerAdapter(
                getSupportFragmentManager(), activity, tab_pos);
        pager.setAdapter(adapter);
        pager.setCurrentItem(tab_pos);
        tapStrip.setViewPager(pager);


    }


}
