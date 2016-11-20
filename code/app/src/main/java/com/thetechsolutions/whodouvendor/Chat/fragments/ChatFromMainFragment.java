package com.thetechsolutions.whodouvendor.Chat.fragments;

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
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.CustomersDT;
import com.thetechsolutions.whodouvendor.Chat.activities.ChatFromMainActivity;
import com.thetechsolutions.whodouvendor.Chat.adapters.ChatFromListAdapter;
import com.thetechsolutions.whodouvendor.Home.activities.HomeMainSearchActivity;
import com.thetechsolutions.whodouvendor.Home.adapters.HomeFriendsProviderListAdapter;
import com.thetechsolutions.whodouvendor.Home.controllers.HomeMainController;
import com.thetechsolutions.whodouvendor.R;

import org.vanguardmatrix.engine.customviews.ProgressActivity;
import org.vanguardmatrix.engine.utils.MyLogs;

import java.util.ArrayList;

import uk.co.ribot.easyadapter.EasyAdapter;

/**
 * Created by Uzair on 7/12/2016.
 */
public class ChatFromMainFragment extends Fragment implements View.OnClickListener {

    public static ChatFromMainFragment fragment;
    static Activity activity;
    public static final String ARG_SECTION = "ARG_SECTION";
    public static final String ARG_SECTION_POSITION = "ARG_SECTION_POSITION";

    ProgressActivity progressActivity;
    DynamicListView dynamicListView;
    EasyAdapter easyAdapter;

    ArrayList<CustomersDT> providerDTs;
    public static int pos;

    public static Fragment newInstance(int sectionNumber,
                                       Activity _activity) {
        fragment = new ChatFromMainFragment();
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



          //  HomeMainFragment.pos=HomeMainSearchActivity.tab_pos;
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
        if (ChatFromMainActivity.tab_pos == 0) {

            providerDTs = new ArrayList<>();
            providerDTs.addAll(RealmDataRetrive.getHomeSearchList(HomeMainSearchActivity.keyword, 0, HomeMainSearchActivity.cat_id, HomeMainSearchActivity.sub_cat_id));
            new getProviderList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } else if (ChatFromMainActivity.tab_pos == 1) {
            providerDTs = new ArrayList<>();
            providerDTs.addAll(RealmDataRetrive.getHomeSearchList(HomeMainSearchActivity.keyword, 1, HomeMainSearchActivity.cat_id, HomeMainSearchActivity.sub_cat_id));

            new getFriendsList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        } else if (ChatFromMainActivity.tab_pos == 2) {


            providerDTs = new ArrayList<>();
            providerDTs.addAll(RealmDataRetrive.getHomeSearchList(HomeMainSearchActivity.keyword, 2, HomeMainSearchActivity.cat_id, HomeMainSearchActivity.sub_cat_id));

            new getFriendsProviderList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }


    }


    private class getProviderList extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressActivity.showLoading();

        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {
                if (providerDTs.size() > 0)
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
                        ChatFromListAdapter.newInstance(activity, 0),
                        providerDTs, mListener);
                AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(easyAdapter);
                animationAdapter.setAbsListView(dynamicListView);
                dynamicListView.setAdapter(animationAdapter);



            } else {

                progressActivity.showEmpty(activity.getResources().getDrawable(R.drawable.home_vendor_icon), "", activity.getResources().getString(R.string.no_search_found)
                );


            }

        }
    }

    private class getFriendsList extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressActivity.showLoading();

        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {
                if (providerDTs.size() > 0) {
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
                        ChatFromListAdapter.newInstance(activity, 0),
                        providerDTs, mListener);
                AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(easyAdapter);
                animationAdapter.setAbsListView(dynamicListView);
                dynamicListView.setAdapter(animationAdapter);


            } else {
                progressActivity.showEmpty(activity.getResources().getDrawable(R.drawable.home_vendor_icon), "",
                        activity.getResources().getString(R.string.no_search_found));


            }

        }
    }

    private class getFriendsProviderList extends AsyncTask<String, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressActivity.showLoading();

        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {

                if (providerDTs.size() > 0) {
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
                        HomeFriendsProviderListAdapter.newInstance(activity),
                        providerDTs, mListener);
                AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(easyAdapter);
                animationAdapter.setAbsListView(dynamicListView);
                dynamicListView.setAdapter(animationAdapter);


            } else {
                progressActivity.showEmpty(activity.getResources().getDrawable(R.drawable.home_vendor_icon), "",
                        activity.getResources().getString(R.string.no_search_found));


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
            MyLogs.printinfo("is_visible" + getArguments().getInt(ARG_SECTION_POSITION));
            pos = getArguments().getInt(ARG_SECTION_POSITION);
        }
    }
}
