package com.thetechsolutions.whodouvendor.Schedule.fragments;

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
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ScheduleDT;
import com.thetechsolutions.whodouvendor.R;
import com.thetechsolutions.whodouvendor.Schedule.activities.ScheduleMainActivity;
import com.thetechsolutions.whodouvendor.Schedule.adapters.ScheduleListAdapter;
import com.thetechsolutions.whodouvendor.Schedule.controller.ScheduleController;

import org.vanguardmatrix.engine.customviews.ProgressActivity;
import org.vanguardmatrix.engine.utils.NetworkManager;

import io.realm.RealmResults;
import uk.co.ribot.easyadapter.EasyAdapter;

/**
 * Created by Uzair on 7/12/2016.
 */
public class ScheduleMainFragment extends Fragment implements View.OnClickListener {

    static ScheduleMainFragment fragment;
    static Activity activity;
    public static final String ARG_SECTION = "ARG_SECTION";
    public static final String ARG_SECTION_POSITION = "ARG_SECTION_POSITION";

    ProgressActivity progressActivity;

    DynamicListView dynamicListView;
    EasyAdapter easyAdapter;
    public static int tab_pos;


    public static Fragment newInstance(int sectionNumber,
                                       Activity _activity) {
        fragment = new ScheduleMainFragment();
        Bundle args = new Bundle();
        activity = _activity;
        args.putInt(ARG_SECTION, ScheduleController.getIntroLayout(sectionNumber));
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
            //tab_position = getArguments().getInt(ARG_SECTION_POSITION);

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

    private void loadData() {
        if (getArguments().getInt(ARG_SECTION_POSITION) == 0) {
            if (RealmDataRetrive.getScheduleList(0).size() > 0) {
                new getDataList(0).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                progressActivity.showEmpty(activity.getResources().getDrawable(R.drawable.calendar_gray_icon), "",
                        "Need to schedule an appointment? It's easy and integrates with the calendar you and your customers already use.  " +
                                "Just click on the + button above.");
            }

        } else if (getArguments().getInt(ARG_SECTION_POSITION) == 1) {
            if (RealmDataRetrive.getScheduleList(1).size() > 0) {
                new getDataList(1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                progressActivity.showEmpty(activity.getResources().getDrawable(R.drawable.calendar_gray_icon), "",
                        "Need to schedule an appointment? It's easy and integrates with the calendar you and your customers already use.  " +
                                "Just click on the + button above.");
            }
        }

    }

    class getDataList extends AsyncTask<String, Void, Boolean> {

        //  RealmResults<ScheduleDT> item;


        int _tab_id;

        getDataList(int tab_id) {
            _tab_id = tab_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressActivity.showLoading();

        }


        @Override
        protected Boolean doInBackground(String... params) {
            try {

                //if (RealmDataRetrive.getScheduleList(_tab_id).size() > 0) {
                return true;
                //}


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
                setAdapter(RealmDataRetrive.getScheduleList(_tab_id));


            } else if (!NetworkManager.checkInternet(activity)) {

            } else {

                progressActivity.showEmpty(activity.getResources().getDrawable(R.drawable.calendar_gray_icon), "",
                        "Need to schedule an appointment? It's easy and integrates with the calendar you and your customers already use.  " +
                                "Just click on the + button above.");

            }

        }
    }


    private void setAdapter(RealmResults<ScheduleDT> arrayList) {

        easyAdapter = new EasyAdapter<>(
                activity,
                ScheduleListAdapter.newInstance(activity),
                arrayList, mListener);
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(easyAdapter);
        animationAdapter.setAbsListView(dynamicListView);
        dynamicListView.setAdapter(animationAdapter);

    }


    public ScheduleListAdapter.Listener mListener = new ScheduleListAdapter.Listener() {

        @Override
        public void onButtonClicked(ScheduleDT person) {
            easyAdapter.notifyDataSetChanged();
            dynamicListView.invalidate();
        }
    };

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        try {
            tab_pos = ((ScheduleMainActivity) activity).pager.getCurrentItem();
        } catch (Exception e) {

        }


    }
}
