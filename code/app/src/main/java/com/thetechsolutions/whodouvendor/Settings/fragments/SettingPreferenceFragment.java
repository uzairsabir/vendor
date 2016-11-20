package com.thetechsolutions.whodouvendor.Settings.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.SettingsPreferenceDT;
import com.thetechsolutions.whodouvendor.R;
import com.thetechsolutions.whodouvendor.Settings.adapters.PreferenceAdapter;

import org.vanguardmatrix.engine.customviews.ProgressActivity;
import org.vanguardmatrix.engine.utils.NetworkManager;

import io.realm.RealmResults;

/**
 * Created by Uzair on 12/23/2015.
 */
public class SettingPreferenceFragment extends Fragment {


    static Activity activity;
    static SettingPreferenceFragment fragment;

    ExpandableListView expandablesList;
    PreferenceAdapter expandablesListViewAdapter;
    ProgressActivity progressActivity;


    public static Fragment newInstance(Activity _activity) {
        fragment = new SettingPreferenceFragment();
        activity = _activity;
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.fragment_preference, container, false);
            progressActivity = (ProgressActivity) rootView.findViewById(R.id.progressActivity);

        } catch (Exception e) {
            e.printStackTrace();
        }

        ViewUpdate(rootView);
        return rootView;
    }

    private void ViewUpdate(final View rootView) {

        renderPreference(rootView);


    }


    public void renderPreference(View rootView) {
        try {
            expandablesList = (ExpandableListView) rootView.findViewById(R.id.expandableList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new renderData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


    }


    public class renderData extends AsyncTask<String, Void, Boolean> {


        String type;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {

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
                setPreferenceAdapter(RealmDataRetrive.getSettingsPreferenceList());

            } else if (!NetworkManager.checkInternet(activity)) {

            } else {

                progressActivity.showEmpty(activity.getResources().getDrawable(R.drawable.dollar_gray_icon), "",
                        "Opps! Something went wrong");


            }
        }

        private void setPreferenceAdapter(RealmResults<SettingsPreferenceDT> arrayList) {
            expandablesListViewAdapter = new PreferenceAdapter(activity, arrayList);
            expandablesList.setAdapter(expandablesListViewAdapter);
            expandablesListViewAdapter.notifyDataSetChanged();
            expandablesList.invalidate();

        }


    }
}
