package com.thetechsolutions.whodouvendor.Settings.adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.thetechsolutions.whodouvendor.AppHelpers.Config.AppConstants;
import com.thetechsolutions.whodouvendor.Settings.fragments.SettingPreferenceFragment;
import com.thetechsolutions.whodouvendor.Settings.fragments.SettingProfileFragment;

import java.util.ArrayList;

/**
 * Created by Uzair on 12/23/2015.
 */
public class SettingsMainPagerAdapter extends FragmentStatePagerAdapter {
    Activity activity;

    private ArrayList<String> titlesList = new ArrayList<String>();

    public SettingsMainPagerAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        ArrayList<String> temp_titles = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                temp_titles.add(AppConstants.PREFERENCE);
            } else if (i == 1) {
                temp_titles.add(AppConstants.PROFILE);
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

        if(position==0){
            return SettingPreferenceFragment.newInstance(activity);
        }else{
            return SettingProfileFragment.newInstance(activity);
        }


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);


    }


}
