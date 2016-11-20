package com.thetechsolutions.whodouvendor.Home.adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.thetechsolutions.whodouvendor.AppHelpers.Config.AppConstants;
import com.thetechsolutions.whodouvendor.Home.fragments.HomeMainFragment;
import com.thetechsolutions.whodouvendor.Home.fragments.HomeSearchMainFragment;

import java.util.ArrayList;

/**
 * Created by Uzair on 12/23/2015.
 */
public class HomeMainPagerAdapter extends FragmentStatePagerAdapter {
    Activity activity;

    private ArrayList<String> titlesList = new ArrayList<String>();

    public HomeMainPagerAdapter(FragmentManager fm, Activity activity, int pos_id) {
        super(fm);
        ArrayList<String> temp_titles = new ArrayList<>();

        if (pos_id == 0) {
            temp_titles.add(AppConstants.CUSTOMERS);
        } else if (pos_id == 1) {
            temp_titles.add(AppConstants.COLLEAGUES);
        }  else {
            for (int i = 0; i < 2; i++) {
                if (i == 0) {
                    temp_titles.add(AppConstants.CUSTOMERS);
                } else if (i == 1) {
                    temp_titles.add(AppConstants.COLLEAGUES);
                }


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

        if (titlesList.size() == 1) {
            return HomeSearchMainFragment.newInstance(position, activity);
        } else {
            return HomeMainFragment.newInstance(position, activity);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);


    }


}
