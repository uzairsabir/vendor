package com.thetechsolutions.whodouvendor.AppHelpers.Contacts.adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.thetechsolutions.whodouvendor.AppHelpers.Contacts.fragments.ContactsMainFragment;
import com.thetechsolutions.whodouvendor.AppHelpers.Config.AppConstants;

import java.util.ArrayList;

/**
 * Created by Uzair on 12/23/2015.
 */
public class ContactsMainPagerAdapter extends FragmentStatePagerAdapter {
    Activity activity;

    private ArrayList<String> titlesList = new ArrayList<String>();

    public ContactsMainPagerAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        ArrayList<String> temp_titles = new ArrayList<>();

        for (int i = 0; i < 1; i++) {
            if (i == 0) {
                temp_titles.add(AppConstants.LIST);
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

        return ContactsMainFragment.newInstance(position, activity);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);


    }


}
