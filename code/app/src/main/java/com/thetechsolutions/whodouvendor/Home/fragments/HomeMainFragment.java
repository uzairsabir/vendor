package com.thetechsolutions.whodouvendor.Home.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ColleagesDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.CustomersDT;
import com.thetechsolutions.whodouvendor.Home.adapters.ColleagesListAdapter;
import com.thetechsolutions.whodouvendor.Home.adapters.HomeFriendsProviderListAdapter;
import com.thetechsolutions.whodouvendor.Home.adapters.CustomerListAdapter;
import com.thetechsolutions.whodouvendor.Home.controllers.HomeMainController;
import com.thetechsolutions.whodouvendor.R;

import org.vanguardmatrix.engine.customviews.ProgressActivity;
import org.vanguardmatrix.engine.utils.MyLogs;

import java.util.ArrayList;

import uk.co.ribot.easyadapter.EasyAdapter;

/**
 * Created by Uzair on 7/12/2016.
 */
public class HomeMainFragment extends Fragment implements View.OnClickListener {

    public static HomeMainFragment fragment;
    static Activity activity;
    public static final String ARG_SECTION = "ARG_SECTION";
    public static final String ARG_SECTION_POSITION = "ARG_SECTION_POSITION";

    ProgressActivity progressActivity;
    DynamicListView dynamicListView;
    EasyAdapter easyAdapter;

    ArrayList<CustomersDT> customersDTs;
    ArrayList<ColleagesDT> colleagesDTs;
    public static int pos;

    public static Fragment newInstance(int sectionNumber,
                                       Activity _activity) {
        fragment = new HomeMainFragment();
        Bundle args = new Bundle();
        activity = _activity;
        args.putInt(ARG_SECTION, HomeMainController.getIntroLayout(sectionNumber));
        args.putInt(ARG_SECTION_POSITION, sectionNumber);
        fragment.setArguments(args);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        try {
            rootView = inflater.inflate(getArguments().getInt(ARG_SECTION), container, false);
            progressActivity = (ProgressActivity) rootView.findViewById(R.id.progressActivity);
            dynamicListView = (DynamicListView) rootView.findViewById(R.id.dynamiclistview);

        } catch (Exception e) {
            e.printStackTrace();
        }


        loadData();
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    public void loadData() {
        if (getArguments().getInt(ARG_SECTION_POSITION) == 0) {

            customersDTs = new ArrayList<>();
            customersDTs.addAll(RealmDataRetrive.getCustomerList());
            new getCustomers().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } else if (getArguments().getInt(ARG_SECTION_POSITION) == 1) {
            colleagesDTs = new ArrayList<>();
            colleagesDTs.addAll(RealmDataRetrive.getColleagesList());

            new getColleagues().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        }
//        else if (getArguments().getInt(ARG_SECTION_POSITION) == 2) {
//
//
//            providerDTs = new ArrayList<>();
//            providerDTs.addAll(RealmDataRetrive.getCustomerList(2));
//
//            new getFriendsProviderList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        }


    }


    private class getCustomers extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressActivity.showLoading();

        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {
                if (customersDTs.size() > 0)
                    return true;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                progressActivity.showContent();
                easyAdapter = new EasyAdapter<>(
                        activity,
                        CustomerListAdapter.newInstance(activity, 0),
                        customersDTs, mListener);
                AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(easyAdapter);
                animationAdapter.setAbsListView(dynamicListView);
                dynamicListView.setAdapter(animationAdapter);


            } else {

                progressActivity.showEmpty(activity.getResources().getDrawable(R.drawable.home_vendor_icon), "",
                        "Get Started by adding customers! Just click on the + button above to begin tailoring whodou to meet your needs.");

            }

        }
    }

    private class getColleagues extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressActivity.showLoading();

        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {
                if (colleagesDTs.size() > 0) {
                    return true;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                progressActivity.showContent();
                easyAdapter = new EasyAdapter<>(
                        activity,
                        ColleagesListAdapter.newInstance(activity, 0),
                        colleagesDTs, mListener);
                AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(easyAdapter);
                animationAdapter.setAbsListView(dynamicListView);
                dynamicListView.setAdapter(animationAdapter);


            } else {
                progressActivity.showEmpty(activity.getResources().getDrawable(R.drawable.home_vendor_icon), "",
                        "Get Started by adding colleagues and all the other people and providers you rely on to get the job done! " +
                                "Just click on the + button above.");

            }

        }
    }



    public HomeFriendsProviderListAdapter.Listener mListener = new HomeFriendsProviderListAdapter.Listener() {

        @Override
        public void onButtonClicked(CustomersDT person) {
            refreshAdapters();
        }
    };

    public void refreshAdapters() {
        easyAdapter.notifyDataSetChanged();
        dynamicListView.invalidate();
    }


    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            pos = getArguments().getInt(ARG_SECTION_POSITION);
            MyLogs.printinfo("is_visible"+pos);
        }
    }
}
