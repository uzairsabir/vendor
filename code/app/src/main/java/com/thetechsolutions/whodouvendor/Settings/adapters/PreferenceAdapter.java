package com.thetechsolutions.whodouvendor.Settings.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thetechsolutions.whodouvendor.AppHelpers.DataBase.RealmDataRetrive;
import com.thetechsolutions.whodouvendor.AppHelpers.DataTypes.SettingsPreferenceDT;
import com.thetechsolutions.whodouvendor.R;
import com.kyleduo.switchbutton.SwitchButton;
import com.thetechsolutions.whodouvendor.Settings.controller.SettingsController;

import org.vanguardmatrix.engine.android.AppPreferences;
import org.vanguardmatrix.engine.utils.MyLogs;
import org.vanguardmatrix.engine.utils.NetworkManager;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import io.realm.RealmResults;

/**
 * Created by Uzair on 10/14/2015.
 */
public class PreferenceAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private RealmResults<SettingsPreferenceDT> PreferenceGroupList;


    public PreferenceAdapter(Activity _activity, RealmResults<SettingsPreferenceDT> header_list) {
        this.activity = _activity;
        PreferenceGroupList = header_list;

    }

    @Override
    public int getGroupCount() {

        return PreferenceGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        RealmResults<SettingsPreferenceDT> childList = RealmDataRetrive.getSettingsPreferenceItemList(PreferenceGroupList.get(groupPosition).getGroup());
        return childList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return PreferenceGroupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        RealmResults<SettingsPreferenceDT> childList = RealmDataRetrive.getSettingsPreferenceItemList(PreferenceGroupList.get(groupPosition).getGroup());
        return childList.get(childPosition);

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        SettingsPreferenceDT headerItem = (SettingsPreferenceDT) getGroup(groupPosition);
        final ViewHolder holder;
        if (view == null) {
            try {
                holder = new ViewHolder();
                LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.item_account_expandable_listview_header, null);
                view.setTag(holder);
                ExpandableListView mExpandableListView = (ExpandableListView) viewGroup;
                mExpandableListView.expandGroup(groupPosition);
                renderHeaderView(holder, view, headerItem);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                holder = new ViewHolder();
                LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.item_account_expandable_listview_header, null);
                view.setTag(holder);
                ExpandableListView mExpandableListView = (ExpandableListView) viewGroup;
                mExpandableListView.expandGroup(groupPosition);
                renderHeaderView(holder, view, headerItem);

            } catch (Exception e) {
                e.printStackTrace();

            }


        }

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        SettingsPreferenceDT childItem = (SettingsPreferenceDT) getChild(groupPosition, childPosition);
        final ViewHolder holder;

        if (view == null) {
            try {
                holder = new ViewHolder();
                LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                view = layoutInflater.inflate(R.layout.item_account_expandable_listview_child, null);
                view.setTag(holder);
                renderChildView(holder, view, childItem);


            } catch (Exception e) {
                e.printStackTrace();

            }

        } else {
            try {
                holder = new ViewHolder();
                LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                view = layoutInflater.inflate(R.layout.item_account_expandable_listview_child, null);
                view.setTag(holder);
                renderChildView(holder, view, childItem);


            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        return view;

    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public void renderHeaderView(ViewHolder holder, View v, SettingsPreferenceDT headerItems) {
        try {
            holder.type_name = (TextView) v
                    .findViewById(R.id.header_title);
        } catch (Exception e) {
            e.printStackTrace();

        }
        try {

            holder.type_name
                    .setText(headerItems.getGroup());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void renderChildView(final ViewHolder holder, View v, final SettingsPreferenceDT Items) {
        try {
            holder.homescreen_title_textview = (TextView) v
                    .findViewById(R.id.contact_name);
            holder.homescreen_location_textview = (TextView) v
                    .findViewById(R.id.status_check);
            holder.progress_loader = (ProgressBar) v
                    .findViewById(R.id.progress_loader);
            holder.container_layout = (RelativeLayout) v.findViewById(R.id.container_layout);

            holder.switch_button = (SwitchButton) v
                    .findViewById(R.id.switch_button);

        } catch (Exception e) {

        }


        try {
            holder.homescreen_title_textview
                    .setText(Items.getPreferences_type());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //   if (AppPreferences.getBoolean(AppPreferences.PREF_IS_SETTING_ITEM_IS_CLICKED + Items.getPrefernece_id())) {
            if (Items.getPreferences_value().equals("1")) {
                holder.homescreen_location_textview.setText("ON");
                holder.homescreen_location_textview.setTextColor(activity.getResources().getColor(R.color.app_title_bar_bg));
                holder.switch_button.setChecked(true);
            } else {

                holder.homescreen_location_textview.setText("OFF");
                holder.homescreen_location_textview.setTextColor(activity.getResources().getColor(R.color.imkhi_red));
                holder.switch_button.setChecked(false);
            }

            //  } else {
//                if (Items.getValue().equalsIgnoreCase("1")) {
//                    holder.homescreen_location_textview.setTextColor(activity.getResources().getColor(R.color.app_title_bar_bg));
//                    holder.homescreen_location_textview.setText("ON");
//                    holder.switch_button.setChecked(true);
//                } else if (Items.getValue().equalsIgnoreCase("0")) {
//                    holder.homescreen_location_textview.setText("OFF");
//                    holder.switch_button.setChecked(false);
//                    holder.homescreen_location_textview.setTextColor(activity.getResources().getColor(R.color.imkhi_red));
//                }
            //}
        } catch (Exception e) {

        }
        if (holder.switch_button.isChecked()) {
            holder.switch_button.setBackColor(ColorStateList.valueOf(activity.getResources().getColor(R.color.who_do_u_green)));
        } else {
            holder.switch_button.setBackColor(ColorStateList.valueOf(activity.getResources().getColor(R.color.who_do_u_red)));
        }
        holder.switch_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.switch_button.setBackColor(ColorStateList.valueOf(activity.getResources().getColor(R.color.who_do_u_green)));
                } else {
                    holder.switch_button.setBackColor(ColorStateList.valueOf(activity.getResources().getColor(R.color.who_do_u_red)));
                }

                if (NetworkManager.isConnected(activity)) {

                    if (holder.switch_button.isChecked()) {
                        SettingsController.updatePreference(activity, Items.getPrefernece_id(), "1");
                    } else {
                        SettingsController.updatePreference(activity, Items.getPrefernece_id(), "0");
                    }

                    //       SettingsController.
//                    if (AppPreferences.getBoolean(AppPreferences.PREF_IS_SETTING_ITEM_IS_CLICKED + Items.getPrefernece_id())) {
//                        if (AppPreferences.getInt(AppPreferences.PREF_ACCOUNT_SETTING_YES + Items.getPrefernece_id()) == 1) {
//                            MyLogs.printinfo("is clicked 0");
//                            // new callUpdateService(holder, Items, 0).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                        } else {
//                            // new callUpdateService(holder, Items, 1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                            MyLogs.printinfo("is clicked 1");
//                        }


//                    } else {
//
////                        if (Items.getValue().equalsIgnoreCase("1")) {
////                            //new callUpdateService(holder, Items, 0).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
////                            MyLogs.printinfo("is clicked service 0");
////                        } else {
////                            //new callUpdateService(holder, Items, 1).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
////                            MyLogs.printinfo("is clicked service 1");
////                        }
//                    }
                } else {
                    UtilityFunctions.showToast_onCenter(activity.getString(R.string.internetoffstatus), activity);
                }
            }
        });

        holder.container_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }


    class ViewHolder {


        protected TextView homescreen_location_textview, homescreen_title_textview, type_name, description;

        protected ProgressBar progress_loader;


        RelativeLayout container_layout;

        SwitchButton switch_button;

    }


    public class callUpdateService extends AsyncTask<String, Void, Boolean> {


        int preference_id, preference_value;

        public callUpdateService(int _preference_id, int _preference_value) {
            preference_id = _preference_id;
            preference_value = _preference_value;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                if (NetworkManager.isConnected(activity)) {
                    //    SettingsController.updatePreference(activity, preference_id+"", preference_value + "")
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

            } else {
                UtilityFunctions.showToast_onCenter(activity.getString(R.string.internetoffstatus), activity);
            }

        }
    }

}
