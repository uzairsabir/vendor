package com.thetechsolutions.whodouvendor.Schedule.adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.thetechsolutions.whodouvendor.AppHelpers.Config.AppConstants;
import com.thetechsolutions.whodouvendor.Schedule.fragments.ScheduleMainFragment;

import java.util.ArrayList;

/**
 * Created by Uzair on 12/23/2015.
 */
public class ScheduleMainPagerAdapter extends FragmentStatePagerAdapter {
    Activity activity;

    private ArrayList<String> titlesList = new ArrayList<String>();

    public ScheduleMainPagerAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        ArrayList<String> temp_titles = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                temp_titles.add(AppConstants.UPCOMING);
            } else if (i == 1) {
                temp_titles.add(AppConstants.RECENT);
            }


        }

        this.titlesList.addAll(temp_titles);
        this.activity = activity;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlesList.get(position);
    }

    @Override
    public int getCount() {
        return this.titlesList.size();
    }

    @Override
    public Fragment getItem(int position) {

        return ScheduleMainFragment.newInstance(position, activity);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);


    }


}
