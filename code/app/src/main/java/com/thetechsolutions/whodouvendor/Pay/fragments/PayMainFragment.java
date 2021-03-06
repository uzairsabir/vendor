package com.thetechsolutions.whodouvendor.Pay.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.PayDT;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.ScheduleDT;
import com.thetechsolutions.whodouvendor.Pay.activities.PayMainActivity;
import com.thetechsolutions.whodouvendor.Pay.adapters.PayListAdapter;
import com.thetechsolutions.whodouvendor.Pay.controller.PayController;
import com.thetechsolutions.whodouvendor.R;
import com.thetechsolutions.whodouvendor.Schedule.adapters.ScheduleListAdapter;

import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.customviews.ProgressActivity;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.NetworkManager;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import io.realm.RealmResults;
import uk.co.ribot.easyadapter.EasyAdapter;

/**
 * Created by Uzair on 7/12/2016.
 */
public class PayMainFragment extends Fragment implements View.OnClickListener {

    static PayMainFragment fragment;
    static Activity activity;
    public static final String ARG_SECTION = "ARG_SECTION";
    public static final String ARG_SECTION_POSITION = "ARG_SECTION_POSITION";
    ProgressActivity progressActivity;
    DynamicListView dynamicListView;
    EasyAdapter easyAdapter;
    public static int tab_pos;
    private EditText paypal_id;
    private TextView save_stripe;


    public static Fragment newInstance(int sectionNumber,
                                       Activity _activity) {
        fragment = new PayMainFragment();
        Bundle args = new Bundle();
        activity = _activity;
        args.putInt(ARG_SECTION, PayController.getIntroLayout(sectionNumber));
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


        } catch (Exception e) {
            e.printStackTrace();
        }

        if (getArguments().getInt(ARG_SECTION_POSITION) < 2) {
            dynamicListView = (DynamicListView) rootView.findViewById(R.id.dynamiclistview);
            loadData();
        } else {
            progressActivity.showContent();
        }

        try {
            paypal_id = (EditText) rootView.findViewById(R.id.paypal_id);
            save_stripe = (TextView) rootView.findViewById(R.id.save_stripe);

            paypal_id.setText(AppPreferences.getString(AppPreferences.PREF_PAYPAL_ID));
            save_stripe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppPreferences.setString(AppPreferences.PREF_PAYPAL_ID, paypal_id.getText().toString());
                    UtilityFunctions.showToast_onCenter("Saved Successfully", activity);
                    PayController.getInstance().createPaypalId(activity, paypal_id.getText().toString());
                }
            });
        } catch (Exception e) {

        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    private void loadData() {
        //  new getDataList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        if (getArguments().getInt(ARG_SECTION_POSITION) == 0) {
            if (RealmDataRetrive.getPayList(0).size() > 0) {
                new getDataList(0).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                progressActivity.showEmpty(activity.getResources().getDrawable(R.drawable.dollar_gray_icon), "",
                        "No payments pending.Click on Setup to experience the simplicity of mobile payments! No more worries about having " +
                                "to track down payment from customers.");
            }

        } else if (getArguments().getInt(ARG_SECTION_POSITION) == 1) {
            if (RealmDataRetrive.getPayList(1).size() > 0) {
                new getDataList(1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                progressActivity.showEmpty(activity.getResources().getDrawable(R.drawable.dollar_gray_icon), "",
                        "No past payments.Click on Setup to experience the simplicity of mobile payments! No more worries about having " +
                                "to track down payment from customers.");

            }
        }
    }

    class getDataList extends AsyncTask<String, Void, Boolean> {


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

                //if (getArguments().getInt(ARG_SECTION_POSITION) == 0) {
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
                setAdapter(RealmDataRetrive.getPayList(getArguments().getInt(ARG_SECTION_POSITION)));

            } else if (!NetworkManager.checkInternet(activity)) {

            } else {
                if (getArguments().getInt(ARG_SECTION_POSITION) == 1) {
                    progressActivity.showEmpty(activity.getResources().getDrawable(R.drawable.dollar_gray_icon), "",
                            "No past payments.Click on Setup to experience the simplicity of mobile payments! No more worries about having " +
                                    "to track down payment from customers.");

                } else if (getArguments().getInt(ARG_SECTION_POSITION) == 0) {
                    progressActivity.showEmpty(activity.getResources().getDrawable(R.drawable.dollar_gray_icon), "",
                            "No payments pending.Click on Setup to experience the simplicity of mobile payments! No more worries about having " +
                                    "to track down payment from customers.");
                }


            }

        }
    }

    private void setAdapter(RealmResults<PayDT> arrayList) {
        MyLogs.printinfo("arrayList " + arrayList.size());
        easyAdapter = new EasyAdapter<>(
                activity,
                PayListAdapter.newInstance(activity, getArguments().getInt(ARG_SECTION_POSITION)),
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
        if (menuVisible)
            tab_pos = ((PayMainActivity) activity).pager.getCurrentItem();
    }
}
