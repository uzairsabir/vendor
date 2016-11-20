package com.thetechsolutions.whodouvendor.Signup.adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.thetechsolutions.whodouvendor.Signup.fragments.IntroFragment;

import java.util.ArrayList;

/**
 * Created by Uzair on 12/23/2015.
 */
public class IntroPagerAdapter extends FragmentStatePagerAdapter {
    Activity activity;
    FragmentManager fragmentManager;
    private ArrayList<String> titlesList = new ArrayList<String>();

    public IntroPagerAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        ArrayList<String> temp_titles = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            temp_titles.add("a" + i);

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
        return 4;
    }

    @Override
    public Fragment getItem(int position) {

        return IntroFragment.newInstance(position, activity);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);


    }


}
